package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;

public class seeRecipeActivity extends AppCompatActivity {

    String recipeName;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_recipe);

        DatabaseHandler db = new DatabaseHandler(this);

        Bundle extra = getIntent().getExtras();
        recipeName = extra.getString("RecipeName");
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView notesText = (TextView) findViewById(R.id.notes);
        ListView ingredientsView = (ListView) findViewById(R.id.ingredients);

        recipe = db.getRecipe(recipeName);
        showRecipe();

        nameText.setText(recipeName, TextView.BufferType.EDITABLE);
        notesText.setText(recipe.getNotes());

        ArrayAdapter<String> adapter =
                  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, recipe.getAllIngredientPrint());
        ingredientsView.setAdapter(adapter);


    }

    public void showRecipe() {
        if (recipe.getRecipe().startsWith("/storage/emulated/0/EmptyYourFridge/")) {
            ScrollView recipeScrollView = (ScrollView) findViewById(R.id.recipeScrollView);
            recipeScrollView.setVisibility(View.GONE);
            ImageView image = (ImageView) findViewById(R.id.recipeImage);
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(recipe.getRecipe(), bitmapOptions);
            image.setImageBitmap(bitmap);
        } else {
            ImageView recipeImage = (ImageView) findViewById(R.id.recipeImage);
            recipeImage.setVisibility(View.GONE);
            TextView recipeText = (TextView) findViewById(R.id.recipe);
            recipeText.setText(recipe.getRecipe());
        }
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
