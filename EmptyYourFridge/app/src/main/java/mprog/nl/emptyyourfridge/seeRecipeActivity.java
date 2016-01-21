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
import java.util.ArrayList;

public class seeRecipeActivity extends AppCompatActivity {

    String recipeName;
    Recipe recipe;
    ImageView smallImage;
    ImageView largeImage;

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
        largeImage = (ImageView) findViewById(R.id.largeImage);
        smallImage = (ImageView) findViewById(R.id.recipeImage);

        recipe = db.getRecipe(recipeName);
        showRecipe();

        nameText.setText(recipeName, TextView.BufferType.EDITABLE);
        notesText.setText(recipe.getNotes());

        ArrayAdapter<String> adapter = new IngredientList(seeRecipeActivity.this, recipe.getNecAmountList(),
                recipe.getNecIngredientList(), recipe.getPosAmountList(), recipe.getPosIngredientList(),
                recipe.getAllIngredientPrint());
        ingredientsView.setAdapter(adapter);

        largeImage.setVisibility(View.GONE);
    }

    public void showRecipe() {
        if (recipe.getRecipe().endsWith(".jpg")) {
            TextView recipeText = (TextView) findViewById(R.id.recipe);
            recipeText.setVisibility(View.GONE);
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(recipe.getRecipe(), bitmapOptions);
            smallImage.setImageBitmap(bitmap);
            largeImage.setImageBitmap(bitmap);
            clickPictures();
        } else {
            ImageView recipeImage = (ImageView) findViewById(R.id.recipeImage);
            recipeImage.setVisibility(View.GONE);
            TextView recipeText = (TextView) findViewById(R.id.recipe);
            recipeText.setText(recipe.getRecipe());
        }
    }

    public void clickPictures() {
        smallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                largeImage.setVisibility(View.VISIBLE);
            }
        });
        largeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                largeImage.setVisibility(View.GONE);
            }
        });
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
