package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Empty Your Fridge App - Feli Nicolaes, feli.nicolaes@uva.student.nl
 *
 * seeRecipeActivity shows all information about a recipe
 */

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
        recipe = db.getRecipe(recipeName);

        if (!recipe.getPics().equals("")) {
            addImagesToTheGallery();
        }

        setRecipeViews();
        showRecipe();
        makeList();
    }

    /* Set and make all TextViews and ImageViews
     */
    public void setRecipeViews() {
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView notesText = (TextView) findViewById(R.id.notes);
        nameText.setText(recipeName, TextView.BufferType.EDITABLE);
        notesText.setText(recipe.getNotes());

        smallImage = (ImageView) findViewById(R.id.recipeImage);
        largeImage = (ImageView) findViewById(R.id.largeImage);
        largeImage.setVisibility(View.GONE);

        largeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                largeImage.setVisibility(View.GONE);
            }
        });
    }

    /* Show list containing all ingredients of this recipe
     */
    public void makeList() {
        ListView ingredientsView = (ListView) findViewById(R.id.ingredients);

        ArrayAdapter<String> adapter = new IngredientList(seeRecipeActivity.this, recipe.getNecAmountList(),
                recipe.getNecIngredientList(), recipe.getPosAmountList(), recipe.getPosIngredientList(),
                recipe.getAllIngredientPrint());
        ingredientsView.setAdapter(adapter);
    }

    /* Show the recipe-text if the recipe has text or the recipeImage if the recipe has an image
     */
    public void showRecipe() {
        if (recipe.getRecipe().endsWith(".jpg")) {
            if (BitmapFactory.decodeFile(recipe.getRecipe(), new BitmapFactory.Options()) == null) {
                recipe.setRecipe("");
            } else{
                showRecipeImage();
            }
        } else {
            ImageView recipeImage = (ImageView) findViewById(R.id.recipeImage);
            recipeImage.setVisibility(View.GONE);
            TextView recipeText = (TextView) findViewById(R.id.recipe);
            recipeText.setText(recipe.getRecipe());
        }
    }

    /* Set the recipe image and set an on click listener to enlarge it
     */
    private void showRecipeImage() {
        TextView recipeText = (TextView) findViewById(R.id.recipe);
        recipeText.setVisibility(View.GONE);
        Bitmap bitmap = BitmapFactory.decodeFile(recipe.getRecipe(), new BitmapFactory.Options());

        smallImage.setImageBitmap(bitmap);

        smallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeFile(recipe.getRecipe(), new BitmapFactory.Options());
                largeImage.setImageBitmap(bitmap);
                largeImage.setVisibility(View.VISIBLE);
            }
        });
    }

    /* Get all extra images and show them in a gallery-like layout
     */
    private void addImagesToTheGallery() {
        ArrayList<String> oldPicsList = recipe.getPicsList();
        picsList = new ArrayList<String>();

        //delete all pictures that are not found from recipe
        for (String pic:oldPicsList) {
            Bitmap bitmap = BitmapFactory.decodeFile(pic, new BitmapFactory.Options());
            if (bitmap != null) {
                picsList.add(pic);
            }
        }
        recipe.setPics(picsList);
        db.updateRecipe(recipe.getName(), recipe);

        //set all pictures into the layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.imageGallery);
        for (int i = 0; i < picsList.size(); i++) {
            layout.addView(getImageView(i));
        }
    }

    /* Make an ImageView to show the extra image in
     */
    public ImageView getImageView(int i){
        final ImageView imageView = new ImageView(this);
        imageView.setId(i);
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(picsList.get(i), bitmapOptions);

        imageView.setImageBitmap(bitmap);
        imageView.setAdjustViewBounds(true);
        setClickListeners(imageView, picsList.get(i));

        return imageView;
    }

    public void setClickListeners(final ImageView imageView, final String path) {
        //enlarge if clicked on image
        imageView.setLongClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(path, bitmapOptions);
                largeImage.setImageBitmap(bitmap);
                largeImage.setVisibility(View.VISIBLE);
            }
        });

        //delete if long clicked on image
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                imageView.setVisibility(View.GONE);
                recipe.removePic(path);
                db.updateRecipe(recipeName, recipe);
                picsList = recipe.getPicsList();
                return true;
            }
        });
    }

    /* If editButton clicked, go to editRecipeActivity to edit this recipe
     */
    public void editButton(View view) {
        Intent editRecipeIntent = new Intent(this, addRecipeActivity.class);
        editRecipeIntent.putExtra("RecipeName", recipeName);
        editRecipeIntent.putExtra("ActivityName", "seeRecipeActivity");
        startActivity(editRecipeIntent);
    }

    /* If backButton clicked, go to recipeListActivitu
     */
    public void backButton(View view) {
        Intent recipeListIntent = new Intent(this, recipeListActivity.class);
        recipeListIntent.putExtra("RecipeName", recipeName);
        recipeListIntent.putExtra("ActivityName", "seeRecipeActivity");
        startActivity(recipeListIntent);
    }
}
