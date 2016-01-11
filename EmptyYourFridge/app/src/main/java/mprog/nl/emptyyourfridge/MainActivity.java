package mprog.nl.emptyyourfridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ListView listView;
    EditText addIngredient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<String>();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
