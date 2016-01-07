package mprog.nl.emptyyourfridge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class recipeListActivity extends AppCompatActivity {

    ArrayList<String> items;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        items = new ArrayList<String>();
        items.add("Spanish Tortilla");

        makeList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int i, long id) {
                Object recipeName = listView.getItemAtPosition(i);
                seeRecipe(recipeName);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int i, long id) {
                deleteItem(i);
                return false;
            }
        });


    }

    public void seeRecipe(Object recipeName) {
        Intent seeRecipeIntent = new Intent(this, seeRecipeActivity.class);
        seeRecipeIntent.putExtra("RecipeName", recipeName.toString());
        startActivity(seeRecipeIntent);
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

}
