package mprog.nl.emptyyourfridge;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Empty Your Fridge App - Feli Nicolaes, feli.nicolaes@uva.student.nl
 *
 * IngredientList is a custom ListView showing ingredients with their amounts and optionality
 */

public class IngredientList extends ArrayAdapter<String>{

    private final Activity context;
    ArrayList<String> necAmounts, necIngredients,posAmounts, posIngredients;

    /* Constructior sets variables
     */
    public IngredientList(Activity context, ArrayList<String> necAmounts, ArrayList<String> necIngredients,
                          ArrayList<String> posAmounts, ArrayList<String> posIngredients,
                          ArrayList<String> allIngredients) {
        super(context, R.layout.smalllist, allIngredients);
        this.context = context;
        this.necAmounts = necAmounts;
        this.necIngredients = necIngredients;
        this.posAmounts = posAmounts;
        this.posIngredients = posIngredients;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View itemView= inflater.inflate(R.layout.smalllist, null, true);

        TextView amount = (TextView) itemView.findViewById(R.id.amount);
        TextView ingredient = (TextView) itemView.findViewById(R.id.ingredient);
        TextView optional = (TextView) itemView.findViewById(R.id.optional);

        //Fill textviews with the right information
        if (i < necAmounts.size()) {
            amount.setText(necAmounts.get(i));
            ingredient.setText(necIngredients.get(i));
            optional.setText("");
        } else {
            amount.setText(posAmounts.get(i-necAmounts.size()));
            ingredient.setText(posIngredients.get(i-necAmounts.size()));
            optional.setText("(optional)");
        }
        return itemView;
    }
}