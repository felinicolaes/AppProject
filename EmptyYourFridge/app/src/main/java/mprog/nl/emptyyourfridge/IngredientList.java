package mprog.nl.emptyyourfridge;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientList extends ArrayAdapter<String>{

    private final Activity context;
    ArrayList<String> necAmounts, necIngredients,posAmounts, posIngredients;

    public IngredientList(Activity context, ArrayList<String> necAmounts, ArrayList<String> necIngredients,
                          ArrayList<String> posAmounts, ArrayList<String> posIngredients,
                          ArrayList<String> allIngredients) {
        super(context, R.layout.smalllist, allIngredients);
        System.out.println("super gefixt");
        this.context = context;
        System.out.println("necamounts "+ necAmounts);
        System.out.println("necing "+ necIngredients);
        System.out.println("posamounts "+ posAmounts);
        System.out.println("posing "+ posIngredients);
        this.necAmounts = necAmounts;
        this.necIngredients = necIngredients;
        this.posAmounts = posAmounts;
        this.posIngredients = posIngredients;
        System.out.println("dingen gethist");
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        System.out.println("in view");
        LayoutInflater inflater = context.getLayoutInflater();
        View itemView= inflater.inflate(R.layout.smalllist, null, true);
        System.out.println("itemview af");

        TextView amount = (TextView) itemView.findViewById(R.id.amount);
        TextView ingredient = (TextView) itemView.findViewById(R.id.ingredient);
        TextView optional = (TextView) itemView.findViewById(R.id.optional);

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