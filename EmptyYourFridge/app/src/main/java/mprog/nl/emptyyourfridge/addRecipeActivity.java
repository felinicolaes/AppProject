package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.graphics.Color;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Bundle extra = getIntent().getExtras();
        nameText = (EditText) findViewById(R.id.name);
        items = new ArrayList<String>();

        prevActivity = extra.getString("ActivityName");
        String recipeName = extra.getString("RecipeName");
        nameText.setText(recipeName, TextView.BufferType.EDITABLE);

        if (extra.containsKey("IngredientName")) {
            items.add(extra.getString("IngredientName"));
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
        listView = (ListView) findViewById(R.id.listView);
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
        Intent typeRecipeIntent = new Intent(this, typeRecipeActivity.class);
        typeRecipeIntent.putExtra("TextKind", "recipe");
        typeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
        startActivity(typeRecipeIntent);
    }

    public void addExtraPictButton(View view) {

    }

    public void addExtraTextButton(View view) {
        Intent typeRecipeIntent = new Intent(this, typeRecipeActivity.class);
        typeRecipeIntent.putExtra("TextKind", "extra");
        typeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
        startActivity(typeRecipeIntent);
    }

    public void saveRecipeButton(View view) {
        Intent seeRecipeIntent = new Intent(this, seeRecipeActivity.class);
        seeRecipeIntent.putExtra("RecipeName", nameText.getText().toString());
        startActivity(seeRecipeIntent);
    }
}
