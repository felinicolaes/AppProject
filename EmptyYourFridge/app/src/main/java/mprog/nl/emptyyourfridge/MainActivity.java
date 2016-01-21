package mprog.nl.emptyyourfridge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ListView listView;
    SharedPreferences settings;
    String addedIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("MyPrefsFile", 0);

        items = new ArrayList<String>();
        if (getIntent().getExtras() != null) {
            Bundle extra = getIntent().getExtras();
            SharedPreferences.Editor editor = settings.edit();
            addedIngredient = extra.getString("IngredientName");
            editor.putString(addedIngredient, "ingredient");
            editor.commit();
        }
        makeList();
        
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                deleteItem(position);
                return false;
            }
        });
    }

    public void getPrevIngredients() {
        Map<String, ?> allPrefs = settings.getAll();
        items.clear();

        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            items.add(entry.getKey().toLowerCase());
        }
    }

    public void makeList() {
        getPrevIngredients();
        listView = (ListView) findViewById(R.id.ingredients);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, items);
        listView.setAdapter(adapter);
    }

    public void deleteItem(int i) {
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(items.get(i));
        editor.apply();
        makeList();
        Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_LONG).show();
    }

    public void addIngredientButton(View view) {
        Intent addIngredientIntent = new Intent(this, addIngredientActivity.class);
        addIngredientIntent.putExtra("ActivityName", "MainActivity");
        addIngredientIntent.putExtra("RecipeName", "");
        startActivity(addIngredientIntent);
    }

    public void addRecipeButton(View view) {
        Intent addRecipeIntent = new Intent(this, addRecipeActivity.class);
        addRecipeIntent.putExtra("ActivityName", "MainActivity");
        addRecipeIntent.putExtra("RecipeName", "");
        startActivity(addRecipeIntent);
    }

    public void searchButton(View view) {
        Intent recipeListIntent = new Intent(this, recipeListActivity.class);
        startActivity(recipeListIntent);
    }

}
