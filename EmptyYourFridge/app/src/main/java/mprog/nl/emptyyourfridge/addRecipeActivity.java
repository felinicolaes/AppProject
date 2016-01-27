package mprog.nl.emptyyourfridge;

import android.app.AlertDialog;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Empty Your Fridge App - Feli Nicolaes, feli.nicolaes@uva.student.nl
 *
 * addRecipeActivity is responsible for adding and editing recipes
 */

public class addRecipeActivity extends AppCompatActivity {
    ListView listView;
    EditText nameText;
    String prevActivity;
    DatabaseHandler db;
    String recipeName;
    String recipeText;
    String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        db = new DatabaseHandler(this);

        nameText = (EditText) findViewById(R.id.name);
        listView = (ListView) findViewById(R.id.ingredients);

        getBundleExtras();
        makeList();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                deleteItem(position);
                return false;
            }
        });
    }

    /* Gets all information given from previous activities
     */
    public void getBundleExtras() {
        Bundle extra = getIntent().getExtras();
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
    }

    /* Show a list containing all ingredients from this recipe
     */
    public void makeList() {
        listView = (ListView) findViewById(R.id.ingredients);
        if (!prevActivity.equals("MainActivity")) {
            Recipe recipe = db.getRecipe(recipeName);
            ArrayAdapter<String> adapter = new IngredientList(addRecipeActivity.this, recipe.getNecAmountList(),
                    recipe.getNecIngredientList(), recipe.getPosAmountList(), recipe.getPosIngredientList(),
                    recipe.getAllIngredientPrint());
            listView.setAdapter(adapter);
        }
    }

    /* Add an ingredient to the recipe
     */
    public void addItem(String itemName, String itemAmount, String optionality) {
        Recipe recipe = db.getRecipe(recipeName);
        if (optionality.equals("necessary")) {
            recipe.addNecIngredient(itemName, itemAmount);
        } else {
            recipe.addPosIngredient(itemName, itemAmount);
        }
        db.updateRecipe(recipeName, recipe);
    }

    /* Display an alert asking the user whether he really wants to delete the old recipe
     * text/picture and start over
     */
    public void sureChangeRecipe(final String recipeKind) {
        new AlertDialog.Builder(this)
                .setTitle("Delete item")
                .setMessage("Are you sure you want to delete the old recipe and start a new one?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(recipeKind.equals("picture")) {
                            addRecipePict("recipe");
                        } else {
                            addRecipeText();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    /* Delete an ingredient from the recipe
     */
    public void deleteItem(int i) {
        Recipe recipe = db.getRecipe(recipeName);
        recipe.removeIngredient(recipe.getAllIngredient().get(i));
        db.updateRecipe(recipeName, recipe);
        makeList();
        Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
    }

    /* Get all the names from the recipes in the database
     */
    public ArrayList<String> getAllNames() {
        ArrayList<String> allNames = new ArrayList<String>();
        for (Recipe recipe : db.getAllRecipes()) {
            allNames.add(recipe.getName());
        }
        return allNames;
    }

    /* If addIngredientButton clicked, go to the addIngredientActivity to add a new ingredient to
     * this recipe
     */
    public void addIngredientButton(View view) {
        if (checkIfLegalName()) {
            Intent addIngredientIntent = new Intent(this, addIngredientActivity.class);
            addIngredientIntent.putExtra("ActivityName", "addRecipeActivity");
            addIngredientIntent.putExtra("RecipeName", nameText.getText().toString());
            startActivity(addIngredientIntent);
        }
    }

    /* If addRecipePictButton clicked, ask whether the user really wants to change this and act
     * accordingly
     */
    public void addRecipePictButton(View view) {
        if(checkIfLegalName()) {
            sureChangeRecipe("picture");
        }
    }

    /* Show a dialog to add a picture to the recipe from the gallery or camera app
     */
    public void addRecipePict(final String textKind) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(addRecipeActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // Take a new picture with the camera app
                if (options[item].equals("Take Photo")) {
                    //Go to camera and take the picture
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    //Go to seperate function to edit, save etc. made picture
                    if (textKind.equals("recipe")) {
                        startActivityForResult(intent, 1);
                    } else {
                        startActivityForResult(intent, 3);
                    }
                    // Choose a picture from the gallery to add
                } else if (options[item].equals("Choose from Gallery")) {
                    //Go to gallery and choose picture
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //Go to seperate function to edit, save etc. chosen picture
                    if (textKind.equals("recipe")) {
                        startActivityForResult(intent, 2);
                    } else {
                        startActivityForResult(intent, 4);
                    }
                    // Don't add any pictures
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /* If addRecipeTextButton clicked, add a recipe using text
     */
    public void addRecipeTextButton(View view) {
        if(checkIfLegalName()) {
            addRecipeText();
        }
    }

    /* Go to the typeRecipeIntent to add text as a recipe
     */
    public void addRecipeText() {
        Intent typeRecipeIntent = new Intent(this, typeRecipeActivity.class);
        typeRecipeIntent.putExtra("TextKind", "recipe");
        typeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
        startActivity(typeRecipeIntent);
    }

    /* Checks if the entered name is not already chosen or empty
     */
    public boolean checkIfLegalName() {
        if (nameText.getText().toString().equals("")) {
            Toast.makeText(addRecipeActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (getAllNames().contains(nameText.getText().toString()) && !nameText.getText().toString().equals(recipeName)) {
            Toast.makeText(addRecipeActivity.this, "This name is taken, please change it", Toast.LENGTH_SHORT).show();
            return false;
        } else if (nameText.getText().toString().equals(recipeName)) {
            System.out.println("nothing changed");
            return true;
        } else if (prevActivity.equals("MainActivity")){
            db.addRecipe(new Recipe(nameText.getText().toString(), "", ""));
            return true;
        } else {
            System.out.println("recipe name changed");
            Recipe recipe = db.getRecipe(recipeName);
            recipe.setName(nameText.getText().toString());
            db.updateRecipe(recipeName, recipe);
            recipeName = nameText.getText().toString();
            return true;
        }
    }

    // Tutorial used: http://www.c-sharpcorner.com/UploadFile/e14021/capture-image-from-camera-and-selecting-image-from-gallery-o/
    /* Get image from gallery or camera-app and save it
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Add a picture from the camera
            if (requestCode == 1 || requestCode == 3) {
                addCameraPicture(requestCode);
            } else if (requestCode == 2 || requestCode == 4) {
                addGalleryPicture(requestCode, data);
            }
        }
    }

    /* Get taken picture and save it
     */
    public void addCameraPicture(int requestCode) {
        File f = new File(Environment.getExternalStorageDirectory().toString());
        for (File temp : f.listFiles()) {
            if (temp.getName().equals("temp.jpg")) {
                f = temp;
                break;
            }
        }
        try {
            //Add picture to gallery
            Recipe recipe = db.getRecipe(recipeName);
            if(requestCode == 1) {
                File file = resizeAndSaveImg(f.getAbsolutePath(), 500);
                recipe.setRecipe(file.toString());
            } else {
                File file = resizeAndSaveImg(f.getAbsolutePath(), 250);
                recipe.addPic(file.toString());
            }
            db.updateRecipe(recipeName, recipe);

            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Add picture by choosing from the gallery and save it
     */
    public void addGalleryPicture(int requestCode, Intent data) {
        //Select image from gallery
        Uri selectedImage = data.getData();
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);

        // Add picture to recipe
        Recipe recipe = db.getRecipe(recipeName);
        if(requestCode == 2) {
            File file = resizeAndSaveImg(c.getString(columnIndex), 500);
            recipe.setRecipe(file.toString());
        } else {
            File file = resizeAndSaveImg(c.getString(columnIndex), 250);
            recipe.addPic(file.toString());
        }
        db.updateRecipe(recipeName, recipe);

        c.close();
    }

    /* Resizes the bitmap and saves it in the EmptyYourFridge map
     */
    public File resizeAndSaveImg(String oldPath, int size) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        Bitmap bigmap = BitmapFactory.decodeFile(oldPath, bitmapOptions);

        //Make bitmap smaller
        int width = size;
        int height = Math.round(bigmap.getHeight()*width/bigmap.getWidth());

        //Find place to store bitmap in EmptyYourFridge map
        Bitmap bitmap = Bitmap.createScaledBitmap(bigmap, width, height, true);
        String path = android.os.Environment.getExternalStorageDirectory()
                + File.separator + "EmptyYourFridge";
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //Actually store bitmap
        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
        try {
            OutputStream outFile = new FileOutputStream(file);
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

        return file;
    }

    /* If addExtraPictButton clicked, add an extra picture to the recipe
     */
    public void addExtraPictButton(View view) {
        if(checkIfLegalName()) {
            addRecipePict("extra");
        }
    }

    /* If addExtraTextButton clicked, add notes to the recipe
     */
    public void addExtraTextButton(View view) {
        if (checkIfLegalName()) {
            Intent typeRecipeIntent = new Intent(this, typeRecipeActivity.class);
            typeRecipeIntent.putExtra("TextKind", "extra");
            typeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
            startActivity(typeRecipeIntent);
        }
    }

    /* If saveRecipeButton clicked, save the recipe
     */
    public void saveRecipeButton(View view) {
        if (checkIfLegalName()) {
            Intent seeRecipeIntent = new Intent(this, seeRecipeActivity.class);
            seeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
            startActivity(seeRecipeIntent);
        }
    }
}
