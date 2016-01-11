package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class typeRecipeActivity extends AppCompatActivity {
    String recipeName;
    String textKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_recipe);

        Bundle extra = getIntent().getExtras();
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView kindText = (TextView) findViewById(R.id.kind);
        recipeName = extra.getString("RecipeName");
        textKind = extra.getString("TextKind");

        nameText.setText(recipeName, TextView.BufferType.EDITABLE);
        kindText.setText("edit " + textKind, TextView.BufferType.EDITABLE);
    }

    public void saveButton(View view) {
        DatabaseHandler db = new DatabaseHandler(this);
        TextView mainText = (TextView) findViewById(R.id.mainText);

        Recipe recipe = db.getRecipe(recipeName);
        if (textKind.equals("recipe")) {
            recipe.setRecipe(mainText.getText().toString());
            db.updateRecipe(recipeName, recipe);
        } else if (textKind.equals("extra")){
            recipe.setNotes(mainText.getText().toString());
            db.updateRecipe(recipeName, recipe);
        }

        Intent addRecipeIntent = new Intent(this, addRecipeActivity.class);
        addRecipeIntent.putExtra("RecipeName", recipeName);
        addRecipeIntent.putExtra("ActivityName", "typeRecipeActivity");
        startActivity(addRecipeIntent);
    }

}
