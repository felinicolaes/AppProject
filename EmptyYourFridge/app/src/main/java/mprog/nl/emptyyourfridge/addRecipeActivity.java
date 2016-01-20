package mprog.nl.emptyyourfridge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class addRecipeActivity extends AppCompatActivity {

    ArrayList<String> ingredients;
    ListView listView;
    EditText nameText;
    String prevActivity;
    DatabaseHandler db;
    String recipeName;
    String recipeText;
    String notes;

    File file;
    File dir;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        db = new DatabaseHandler(this);
        Bundle extra = getIntent().getExtras();
        nameText = (EditText) findViewById(R.id.name);
        listView = (ListView) findViewById(R.id.ingredients);

        prevActivity = extra.getString("ActivityName");
        recipeName = extra.getString("RecipeName");
        nameText.setText(recipeName, TextView.BufferType.EDITABLE);

        if (extra.containsKey("IngredientName")) {
            addItem(extra.getString("IngredientName"), extra.getString("AmountName"), extra.getString("Status"));
        } else if (extra.containsKey("notes")) {
            notes = extra.getString("notes");
        } else if (extra.containsKey("recipe")) {
            recipeText = extra.getString("recipe");
        }

        makeList();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                deleteItem(position);
                return false;
            }
        });
    }

    public void makeList() {
        listView = (ListView) findViewById(R.id.ingredients);
        if (!prevActivity.equals("MainActivity")) {
            Recipe recipe = db.getRecipe(recipeName);
            ArrayAdapter<String> adapter = new IngredientList(addRecipeActivity.this, recipe.getNecAmountList(),
                    recipe.getNecIngredientList(), recipe.getPosAmountList(), recipe.getPosIngredientList(),
                    recipe.getAllIngredientPrint());
            System.out.println("adapter gemaakt");
            listView.setAdapter(adapter);
            System.out.println("adapter geset");
        }
    }

    public void addItem(String itemName, String itemAmount, String optionality) {
        Recipe recipe = db.getRecipe(recipeName);
        if (optionality.equals("necessary")) {
            recipe.addNecIngredient(itemName, itemAmount);
        } else {
            recipe.addPosIngredient(itemName, itemAmount);
        }
        db.updateRecipe(recipeName, recipe);
    }

    public void deleteItem(int i) {
        Recipe recipe = db.getRecipe(recipeName);
        recipe.removeIngredient(recipe.getAllIngredient().get(i));
        db.updateRecipe(recipeName, recipe);
        makeList();
        Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_LONG).show();
    }

    public ArrayList<String> getAllNames() {
        ArrayList<String> allNames = new ArrayList<String>();
        for (Recipe recipe : db.getAllRecipes()) {
            allNames.add(recipe.getName());
        }
        return allNames;
    }

    public void addIngredientButton(View view) {
        if (nameText.getText().toString().equals("")) {
            Toast.makeText(addRecipeActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
        } else if (getAllNames().contains(nameText.getText().toString()) && prevActivity.equals("MainActivity")) {
            Toast.makeText(addRecipeActivity.this, "This name is taken, please change it", Toast.LENGTH_SHORT).show();
        } else {
            if (prevActivity.equals("MainActivity")) {
                db.addRecipe(new Recipe(nameText.getText().toString(), "", ""));
            }
            Intent addIngredientIntent = new Intent(this, addIngredientActivity.class);
            addIngredientIntent.putExtra("ActivityName", "addRecipeActivity");
            addIngredientIntent.putExtra("RecipeName", nameText.getText().toString());
            startActivity(addIngredientIntent);
        }
    }

    // Tutorial used: http://www.c-sharpcorner.com/UploadFile/e14021/capture-image-from-camera-and-selecting-image-from-gallery-o/
    public void addRecipePictButton(View view) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(addRecipeActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void addRecipeTextButton(View view) {
        if (nameText.getText().toString().equals("")) {
            Toast.makeText(addRecipeActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
        } else if (getAllNames().contains(nameText.getText().toString()) && prevActivity.equals("MainActivity")) {
            Toast.makeText(addRecipeActivity.this, "This name is taken, please change it", Toast.LENGTH_SHORT).show();
        } else {
            if (prevActivity.equals("MainActivity")) {
                db.addRecipe(new Recipe(nameText.getText().toString(), "", ""));
            }
            Intent typeRecipeIntent = new Intent(this, typeRecipeActivity.class);
            typeRecipeIntent.putExtra("TextKind", "recipe");
            typeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
            startActivity(typeRecipeIntent);
        }
    }

    public void addExtraPictButton(View view) {

    }

    public void addExtraTextButton(View view) {
        if (nameText.getText().toString().equals("")) {
            Toast.makeText(addRecipeActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
        } else if (getAllNames().contains(nameText.getText().toString()) && prevActivity.equals("MainActivity")) {
            Toast.makeText(addRecipeActivity.this, "This name is taken, please change it", Toast.LENGTH_SHORT).show();
        } else {
            if (prevActivity.equals("MainActivity")) {
                db.addRecipe(new Recipe(nameText.getText().toString(), "", ""));
            }
            Intent typeRecipeIntent = new Intent(this, typeRecipeActivity.class);
            typeRecipeIntent.putExtra("TextKind", "extra");
            typeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
            startActivity(typeRecipeIntent);
        }
    }

    public void saveRecipeButton(View view) {
        System.out.println("hoi1");
        if (nameText.getText().toString().equals("")) {
            Toast.makeText(addRecipeActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
        } else if (getAllNames().contains(nameText.getText().toString()) && prevActivity.equals("MainActivity")) {
            Toast.makeText(addRecipeActivity.this, "This name is taken, please change it", Toast.LENGTH_SHORT).show();
        } else {
            if (prevActivity.equals("MainActivity")) {
                recipeName = nameText.getText().toString();
                db.addRecipe(new Recipe(recipeName, "", ""));
            }
            if (!nameText.getText().toString().equals(recipeName)) {
                Recipe newRecipe = db.getRecipe(recipeName);
                newRecipe.setName(nameText.getText().toString());
                db.updateRecipe(recipeName, newRecipe);
            }

            Intent seeRecipeIntent = new Intent(this, seeRecipeActivity.class);
            seeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
            System.out.println("hoihoi6");
            startActivity(seeRecipeIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

                    String path = android.os.Environment.getExternalStorageDirectory()
                            + File.separator + "EmptyYourFridge";
                    File directory = new File(path);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    Recipe recipe = db.getRecipe(recipeName);
                    recipe.setRecipe(file.toString());
                    db.updateRecipe(recipeName, recipe);

                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                Recipe recipe = db.getRecipe(recipeName);
                recipe.setRecipe(picturePath.toString());
                db.updateRecipe(recipeName, recipe);
            }
        }
    }
}
