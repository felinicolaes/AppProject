package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class seeRecipeActivity extends AppCompatActivity {

    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_recipe);

        DatabaseHandler db = new DatabaseHandler(this);

        Bundle extra = getIntent().getExtras();
        recipeName = extra.getString("RecipeName");
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView recipeText = (TextView) findViewById(R.id.recipe);
        TextView notesText = (TextView) findViewById(R.id.notes);
        ListView ingredientsView = (ListView) findViewById(R.id.ingredients);

        Recipe recipe = db.getRecipe(recipeName);

        nameText.setText(recipeName, TextView.BufferType.EDITABLE);
        recipeText.setText(recipe.getRecipe());
        notesText.setText(recipe.getNotes());

        ArrayAdapter<String> adapter =
                  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, recipe.getAllIngredientPrint());
        ingredientsView.setAdapter(adapter);
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
