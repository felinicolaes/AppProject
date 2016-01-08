package mprog.nl.emptyyourfridge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class recipeListActivity extends AppCompatActivity {

    ArrayList<String> items;
    ListView listView;
    DatabaseHandler db;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        listView = (ListView) findViewById(R.id.listView);
        recipes = new ArrayList<Recipe>();
        items = new ArrayList<String>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int i, long id) {
                Object recipeName = listView.getItemAtPosition(i);
                seeRecipe(recipeName);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int i, long id) {
                deleteItem(i);
                return true;
            }
        });

        db = new DatabaseHandler(this);
        // Inserting Recipes
        db.addRecipe(new Recipe("Ravioli", "1"));
        db.addRecipe(new Recipe("Friet met friet", "2"));
        db.addRecipe(new Recipe("Tortilla", "3"));
        db.addRecipe(new Recipe("Pasta met kip", "4"));


        makeList();
    }

    public void seeRecipe(Object recipeName) {
        Intent seeRecipeIntent = new Intent(this, seeRecipeActivity.class);
        seeRecipeIntent.putExtra("RecipeName", recipeName.toString());
        startActivity(seeRecipeIntent);
    }

    public void makeList() {
        recipes = db.getAllRecipes();
        items.clear();

        for (Recipe i : recipes) {
            items.add(i.getName());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, items);
        listView.setAdapter(adapter);
    }

    // code from http://www.codeproject.com/Articles/569966/SharedplusPreferencesplusandplusYes-fNoplusAlertp
    public void deleteItem(int i) {
        final int item = i;
        new AlertDialog.Builder(this)
                .setTitle("Delete item")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        reallyDelete(item);
                        Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();

    }

    public void reallyDelete(int i) {
        db.deleteRecipe(recipes.get(i));
        makeList();
        Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_LONG).show();
    }

}
