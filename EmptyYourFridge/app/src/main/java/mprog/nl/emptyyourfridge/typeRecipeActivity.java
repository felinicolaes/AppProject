package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Empty Your Fridge App - Feli Nicolaes, feli.nicolaes@uva.student.nl
 *
 * seeRecipeActivity shows all information about a recipe
 */

public class typeRecipeActivity extends AppCompatActivity {
    String recipeName;
    String textKind;
    TextView mainText;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_recipe);

        Bundle extra = getIntent().getExtras();
        recipeName = extra.getString("RecipeName");
        textKind = extra.getString("TextKind");

        TextView nameText = (TextView) findViewById(R.id.name);
        TextView kindText = (TextView) findViewById(R.id.kind);
        nameText.setText(recipeName, TextView.BufferType.EDITABLE);
        kindText.setText("edit " + textKind, TextView.BufferType.EDITABLE);

        db = new DatabaseHandler(this);
        setEditText();
    }

    /* Set previously typed recipe/notes as text so user can easily change them
     */
    public void setEditText() {
        mainText = (TextView) findViewById(R.id.mainText);
        Recipe recipe = db.getRecipe(recipeName);

        if (textKind.equals("recipe") && !recipe.getRecipe().endsWith(".jpg")) {
            mainText.setText(recipe.getRecipe(), TextView.BufferType.EDITABLE);
        } else if (textKind.equals("extra")){
            mainText.setText(recipe.getNotes(), TextView.BufferType.EDITABLE);
        }
    }

    /* If saveButton clicked, save the typed text and bring user back to editRecipeActivity
     */
    public void saveButton(View view) {
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