package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
            addItem(extra.getString("IngredientName"), extra.getString("Status"));
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
            ingredients = db.getRecipe(recipeName).getAllIngredientPrint();
        } else {
            ingredients = new ArrayList<String>();
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, ingredients);
        listView.setAdapter(adapter);
    }

    public void addItem(String itemName, String optionality) {
        Recipe recipe = db.getRecipe(recipeName);
        if (optionality.equals("necessary")) {
            recipe.addNecIngredient(itemName);
        } else {
            recipe.addPosIngredient(itemName);
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

    public void addIngredientButton(View view) {
        if (prevActivity.equals("MainActivity")) {
            db.addRecipe(new Recipe(nameText.getText().toString(), "", ""));
            Toast.makeText(getApplicationContext(), "Recipe added", Toast.LENGTH_LONG).show();
        }
        Intent addIngredientIntent = new Intent(this, addIngredientActivity.class);
        addIngredientIntent.putExtra("ActivityName", "addRecipeActivity");
        addIngredientIntent.putExtra("RecipeName", nameText.getText().toString());
        startActivity(addIngredientIntent);
    }

    public void addRecipePictButton(View view) {

    }

    public void addRecipeTextButton(View view) {
        if (prevActivity.equals("MainActivity")) {
            db.addRecipe(new Recipe(nameText.getText().toString(), "", ""));
        }

        Intent typeRecipeIntent = new Intent(this, typeRecipeActivity.class);
        typeRecipeIntent.putExtra("TextKind", "recipe");
        typeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
        startActivity(typeRecipeIntent);
    }

    public void addExtraPictButton(View view) {

    }

    public void addExtraTextButton(View view) {
        if (prevActivity.equals("MainActivity")) {
            db.addRecipe(new Recipe(nameText.getText().toString(), "", ""));
        }

        Intent typeRecipeIntent = new Intent(this, typeRecipeActivity.class);
        typeRecipeIntent.putExtra("TextKind", "extra");
        typeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
        startActivity(typeRecipeIntent);
    }

    public void saveRecipeButton(View view) {
        if (!nameText.getText().toString().equals(recipeName) && !prevActivity.equals("MainActivity")) {
            Recipe newRecipe = db.getRecipe(recipeName);
            newRecipe.setName(nameText.getText().toString());
            db.updateRecipe(recipeName, newRecipe);
        }

        Intent seeRecipeIntent = new Intent(this, seeRecipeActivity.class);
        seeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
        startActivity(seeRecipeIntent);
    }
}
