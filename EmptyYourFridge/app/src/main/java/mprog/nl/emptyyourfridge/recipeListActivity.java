package mprog.nl.emptyyourfridge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Map;

/**
 * Empty Your Fridge App - Feli Nicolaes, feli.nicolaes@uva.student.nl
 *
 * RecipeListActivity shows the user all the recipes in the database
 */

public class recipeListActivity extends AppCompatActivity {
    ArrayList<String> items;
    ListView listView;
    DatabaseHandler db;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        listView = (ListView) findViewById(R.id.ingredients);
        recipes = new ArrayList<Recipe>();
        items = new ArrayList<String>();

        db = new DatabaseHandler(this);
        //Add some default recipes if the database is empty
        if (db.getAllRecipes().size() == 0) {
            Toast.makeText(getApplicationContext(),
                    "No recipes were added yet, so we added some simple ones for you go get started!",
                    Toast.LENGTH_SHORT).show();
            addRecipes();
        }

        setOnClickListeners();
        makeList();
    }

    /* Set onClickListener for normal and long click to the ListView-items
     */
    public void setOnClickListeners() {
        //Go to recipe if recipe is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int i, long id) {
                Object recipeName = listView.getItemAtPosition(i);
                seeRecipe(recipeName);
            }
        });

        //Delete recipe if recipe is clicked
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int i, long id) {
                deleteItem(i);
                return true;
            }
        });
    }

    /* Go to seeRecipeIntent, where the user can see the recipe in detail
     */
    public void seeRecipe(Object recipeName) {
        Intent seeRecipeIntent = new Intent(this, seeRecipeActivity.class);
        seeRecipeIntent.putExtra("RecipeName", recipeName.toString());
        startActivity(seeRecipeIntent);
    }

    /* Show a list containing all recipes
     */
    public void makeList() {
        getFilteredRecipes();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, items);
        listView.setAdapter(adapter);
    }

    /* Ask the user whether he really wants to remove a recipe from the database
     */
    public void deleteItem(int i) {
        final int item = i;
        new AlertDialog.Builder(this)
                .setTitle("Delete item")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reallyDelete(item);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    /* Delete a recipe from the database and listview
     */
    public void reallyDelete(int i) {
        db.deleteRecipe(recipes.get(i));
        makeList();
        Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
    }

    /* Get all recipes containing certain ingredients
     */
    public void getFilteredRecipes() {
        items.clear();
        recipes = db.getAllRecipes();

        for (Recipe recipe : recipes) {
            items.add(recipe.getName());
            for (String ingredient : getPrevIngredients()) {
                if (!recipe.getAllIngredient().contains(ingredient)) {
                    items.remove(recipe.getName());
                }
            }
        }
    }

    /* Get all ingredients the recipes have to be filtered on
     */
    public ArrayList<String> getPrevIngredients() {
        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        Map<String, ?> allPrefs = settings.getAll();
        ArrayList<String> ingredients = new ArrayList<String>();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            ingredients.add(entry.getKey().toLowerCase());
        }
        return ingredients;
    }

    /* Add some default recipes if no recipes were present in the database
     */
    public void addRecipes(){
        db.addRecipe(new Recipe("Broodje gezond", "Leg alles op broodje, klaar!", "Lekker lekker",
                makeArrayList(new String[]{"brood", "kaas", "sla"}), makeArrayList(new String[]{"2 snee", "1 plak", ""}),
                makeArrayList(new String[]{"ham"}), makeArrayList(new String[]{"1 plak"}) ) );
        db.addRecipe(new Recipe("Broodje kaas", "Leg alles op broodje, klaar!", "Lekker lekker",
                makeArrayList(new String[]{"brood", "kaas"}), makeArrayList(new String[]{"2 snee", "1 plak"}),
                makeArrayList(new String[]{}), makeArrayList(new String[]{})));
        db.addRecipe(new Recipe("BoterHAM", "Leg alles op broodje, klaar!", "Lekker lekker",
                makeArrayList(new String[]{"brood", "ham"}), makeArrayList(new String[]{"2 snee", "1 plak"}),
                makeArrayList(new String[]{}), makeArrayList(new String[]{}) ) );
        db.addRecipe(new Recipe("Croissant", "Leg alles erop, klaar!", "Lekker lekker",
                makeArrayList(new String[]{"croissant"}), makeArrayList(new String[]{"1"}),
                makeArrayList(new String[]{"kaas", "jam"}), makeArrayList(new String[]{"", ""}) ) );
    }

    /* Make an ArrayList from a String[]
     */
    public ArrayList<String> makeArrayList(String[] strings) {
        ArrayList<String> list = new ArrayList<String>();
        for (String s : strings)
        {
            list.add(s);
        }
        return list;
    }

    /* If backButton is clicked, go back to the MainActivity
     */
    public void backButton(View view) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}



