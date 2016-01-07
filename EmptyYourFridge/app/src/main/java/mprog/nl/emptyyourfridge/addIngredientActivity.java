package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class addIngredientActivity extends AppCompatActivity {
    String prevActivity;
    String recipeName;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        edit =  (EditText) findViewById(R.id.ingredient);

        Bundle extra = getIntent().getExtras();
        prevActivity = extra.getString("ActivityName");
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        if (prevActivity.equals("MainActivity")) {
            checkBox.setVisibility(View.INVISIBLE);
        } else {
            recipeName = extra.getString("RecipeName");
        }
    }

    public void addIngredientButton(View view) {
        if (prevActivity.equals("MainActivity")) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra("IngredientName", edit.getText().toString());
            startActivity(mainIntent);
        } else {
            Intent addRecipeIntent = new Intent(this, addRecipeActivity.class);
            addRecipeIntent.putExtra("RecipeName", recipeName);
            addRecipeIntent.putExtra("IngredientName", edit.getText().toString());
            addRecipeIntent.putExtra("ActivityName", "addIngredientActivity");
            startActivity(addRecipeIntent);
        }
    }
}
