package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Empty Your Fridge App - Feli Nicolaes, feli.nicolaes@uva.student.nl
 *
 * addIngredientActivity is responsible for adding ingredients to a recipe or adding ingredients to
 * filter on
 */

public class addIngredientActivity extends AppCompatActivity {
    String prevActivity;
    String recipeName;
    EditText editIngredient;
    EditText editAmount;
    CheckBox checkBox;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        editIngredient =  (EditText) findViewById(R.id.ingredient);
        editAmount =  (EditText) findViewById(R.id.amount);

        Bundle extra = getIntent().getExtras();
        prevActivity = extra.getString("ActivityName");
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        if (prevActivity.equals("MainActivity")) {
            checkBox.setVisibility(View.INVISIBLE);
            editAmount.setVisibility(View.INVISIBLE);
        } else {
            recipeName = extra.getString("RecipeName");
        }

        makeList();
    }

    /* Shows a list with all ingredients previously added to existing recipes
     */
    public void makeList() {
        listView = (ListView) findViewById(R.id.ingredients);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, getAllIngredients());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int i, long id) {
                editIngredient.setText(getAllIngredients().get(i));
            }
        });
    }

    /* Adds the ingredient to either the filter or the recipe, depending on the previous activity
     */
    public void addIngredientButton(View view) {
        if (editIngredient.getText().toString().equals("")) {
            Toast.makeText(addIngredientActivity.this, "Please enter an ingredient", Toast.LENGTH_SHORT).show();
        } else {
            if (prevActivity.equals("MainActivity")) {
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.putExtra("IngredientName", editIngredient.getText().toString());
                startActivity(mainIntent);
            } else {
                addToRecipe();
            }
        }
    }

    /* Adds the ingredient to the recipe the user was editing/adding
     */
    public void addToRecipe() {
        Intent addRecipeIntent = new Intent(this, addRecipeActivity.class);
        if (checkBox.isChecked()) {
            addRecipeIntent.putExtra("Status", "optional");
        } else {
            addRecipeIntent.putExtra("Status", "necessary");
        }
        addRecipeIntent.putExtra("RecipeName", recipeName);
        addRecipeIntent.putExtra("IngredientName", editIngredient.getText().toString());
        addRecipeIntent.putExtra("AmountName", editAmount.getText().toString());
        addRecipeIntent.putExtra("ActivityName", "addIngredientActivity");
        startActivity(addRecipeIntent);
    }

    /* Gets a list with all ingredients previously added to existing recipes
     */
    public ArrayList<String> getAllIngredients() {
        ArrayList<String> allIngredients = new ArrayList<String>();
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<Recipe> allRecipes = db.getAllRecipes();
        for (Recipe recipe : allRecipes) {
            for (String ingredient : recipe.getAllIngredient()) {
                if (!allIngredients.contains(ingredient)) {
                    allIngredients.add(ingredient);
                }
            }
        }
        return allIngredients;
    }
}
