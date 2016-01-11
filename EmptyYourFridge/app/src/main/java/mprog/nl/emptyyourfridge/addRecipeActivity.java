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

    ArrayList<String> items;
    ListView listView;
    EditText nameText;
    String prevActivity;
    DatabaseHandler db;
    String recipeName;
    String recipe;
    String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        db = new DatabaseHandler(this);
        Bundle extra = getIntent().getExtras();
        nameText = (EditText) findViewById(R.id.name);
        items = new ArrayList<String>();

        prevActivity = extra.getString("ActivityName");
        recipeName = extra.getString("RecipeName");
        nameText.setText(recipeName, TextView.BufferType.EDITABLE);


        if (extra.containsKey("IngredientName")) {
            items.add(extra.getString("IngredientName"));
        } else if (extra.containsKey("notes")) {
            notes = extra.getString("notes");
        } else if (extra.containsKey("recipe")) {
            recipe = extra.getString("recipe");
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
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, items);
        listView.setAdapter(adapter);
    }


    public void deleteItem(int i) {
        items.remove(i);
        makeList();
        Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_LONG).show();
    }

    public void addIngredientButton(View view) {
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
