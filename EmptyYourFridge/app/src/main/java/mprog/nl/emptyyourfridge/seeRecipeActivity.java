package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class seeRecipeActivity extends AppCompatActivity {

    String recipeName;
    Recipe recipe;
    ImageView smallImage;
    ImageView largeImage;
    ArrayList<String> picsList;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_recipe);

        db = new DatabaseHandler(this);

        Bundle extra = getIntent().getExtras();
        recipeName = extra.getString("RecipeName");
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView notesText = (TextView) findViewById(R.id.notes);
        ListView ingredientsView = (ListView) findViewById(R.id.ingredients);
        largeImage = (ImageView) findViewById(R.id.largeImage);
        smallImage = (ImageView) findViewById(R.id.recipeImage);

        recipe = db.getRecipe(recipeName);
        showRecipe();
        if (!recipe.getPics().equals("")) {
            addImagesToTheGallery();
        }

        nameText.setText(recipeName, TextView.BufferType.EDITABLE);
        notesText.setText(recipe.getNotes());

        ArrayAdapter<String> adapter = new IngredientList(seeRecipeActivity.this, recipe.getNecAmountList(),
                recipe.getNecIngredientList(), recipe.getPosAmountList(), recipe.getPosIngredientList(),
                recipe.getAllIngredientPrint());
        ingredientsView.setAdapter(adapter);

        largeImage.setVisibility(View.GONE);
    }

    public void showRecipe() {
        largeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                largeImage.setVisibility(View.GONE);
            }
        });
        if (recipe.getRecipe().endsWith(".jpg")) {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            if (BitmapFactory.decodeFile(recipe.getRecipe(), bitmapOptions) == null) {
                recipe.setRecipe("");
            } else{
                TextView recipeText = (TextView) findViewById(R.id.recipe);
                recipeText.setVisibility(View.GONE);
                Bitmap bitmap = BitmapFactory.decodeFile(recipe.getRecipe(), bitmapOptions);
                smallImage.setImageBitmap(bitmap);
                largeImage.setImageBitmap(bitmap);
                smallImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        largeImage.setVisibility(View.VISIBLE);
                    }
                });
            }
        } else {
            ImageView recipeImage = (ImageView) findViewById(R.id.recipeImage);
            recipeImage.setVisibility(View.GONE);
            TextView recipeText = (TextView) findViewById(R.id.recipe);
            recipeText.setText(recipe.getRecipe());
        }
    }

    private void addImagesToTheGallery() {
        System.out.println("in functie");
        picsList = recipe.getPicsList();
        System.out.println("pics geget");
        LinearLayout layout = (LinearLayout) findViewById(R.id.imageGallery);
        System.out.println("layout geget");
        System.out.println("size piclist "+picsList.size());

        System.out.println("list is " + picsList);
        for (int i = 0; i < picsList.size(); i++) {
                System.out.println("set pic number "+i);
                layout.addView(getImageView(i));
        }
    }

    public ImageView getImageView(final int i){
        final ImageView imageView = new ImageView(this);
        imageView.setId(i);
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(picsList.get(i), bitmapOptions);
        imageView.setImageBitmap(bitmap);

        imageView.setLongClickable(true);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(picsList.get(i), bitmapOptions);
                largeImage.setImageBitmap(bitmap);
                largeImage.setVisibility(View.VISIBLE);
                System.out.println("clicked pic number " + i);
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageView.setVisibility(View.GONE);
                System.out.println("removed pic number " + i);
                recipe.removePic(picsList.get(i));
                db.updateRecipe(recipeName, recipe);
                picsList = recipe.getPicsList();
                return true;
            }
        });

        return imageView;
    }

    public void editButton(View view) {
        Intent editRecipeIntent = new Intent(this, addRecipeActivity.class);
        editRecipeIntent.putExtra("RecipeName", recipeName);
        editRecipeIntent.putExtra("ActivityName", "seeRecipeActivity");
        startActivity(editRecipeIntent);
    }

    public void backButton(View view) {
        Intent recipeListIntent = new Intent(this, recipeListActivity.class);
        recipeListIntent.putExtra("RecipeName", recipeName);
        recipeListIntent.putExtra("ActivityName", "seeRecipeActivity");
        startActivity(recipeListIntent);
    }
}
