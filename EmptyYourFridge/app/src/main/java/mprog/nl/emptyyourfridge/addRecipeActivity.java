package mprog.nl.emptyyourfridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class addRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Bundle extra = getIntent().getExtras();
        String activityName = extra.getString("ActivityName");

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(activityName);
    }
}
