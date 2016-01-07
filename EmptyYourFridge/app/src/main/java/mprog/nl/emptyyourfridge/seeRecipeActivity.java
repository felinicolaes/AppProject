package mprog.nl.emptyyourfridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class seeRecipeActivity extends AppCompatActivity {

    String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_recipe);

        Bundle extra = getIntent().getExtras();
        recipeName = extra.getString("RecipeName");
        TextView nameText = (TextView) findViewById(R.id.name);

        nameText.setText(recipeName, TextView.BufferType.EDITABLE);
    }

    public void editButton(View view) {
        Intent editRecipeIntent = new Intent(this, addRecipeActivity.class);
        editRecipeIntent.putExtra("RecipeName", recipeName);
        editRecipeIntent.putExtra("ActivityName", "seerecipeActivity");
        startActivity(editRecipeIntent);
    }
}
