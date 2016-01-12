package mprog.nl.emptyyourfridge;

import android.media.Image;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Feli on 8-1-2016.
 */
public class Recipe {
    String necIngredients;
    String posIngredients;
    String name;
    String pictArray;
    String notes;
    String recipe;
    Gson necGson;
    Gson posGson;

    public Recipe(String name, String recipe, String notes) {
        this.name = name;
        ArrayList<String> empty = new ArrayList<String>();
        this.necGson = new Gson();
        this.necIngredients = necGson.toJson(empty);
        this.posGson = new Gson();
        this.posIngredients = posGson.toJson(empty);
        this.recipe = recipe;
        this.pictArray = "";
        this.notes = notes;
    }

    public Recipe(String name, String recipe, String notes, ArrayList<String> necIngredients,
                  ArrayList<String> posIngredients) {
        this.name = name;
        this.necGson = new Gson();
        this.necIngredients = necGson.toJson(necIngredients);
        this.posGson = new Gson();
        this.posIngredients = posGson.toJson(posIngredients);
        this.recipe = recipe;
        this.pictArray = "";
        this.notes = notes;
    }

    public Recipe(String name, String recipe, String notes, String necIngredients,
                  String posIngredients) {
        this.name = name;
        this.necGson = new Gson();
        this.necIngredients = necIngredients;
        this.posGson = new Gson();
        this.posIngredients = posIngredients;
        this.recipe = recipe;
        this.pictArray = "";
        this.notes = notes;
    }

    /*
        GET FUNCTIONS
     */
    public ArrayList<String> getAllIngredient() {
        ArrayList<String> allIngredient = getNecIngredientList();
        for (String item : getPosIngredientList()) {
            allIngredient.add(item);
        }
        return allIngredient;
    }

    public ArrayList<String> getAllIngredientPrint() {
        ArrayList<String> allIngredient = getNecIngredientList();
        for (String item : getPosIngredientList()) {
            allIngredient.add(item + " (optional)");
        }
        return allIngredient;
    }

    public String getNecIngredient() {
        return necIngredients;
    }

    public ArrayList<String> getNecIngredientList() {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return necGson.fromJson(necIngredients, type);
    }

    public String getPosIngredient() {
        return posIngredients;
    }

    public ArrayList<String> getPosIngredientList() {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return posGson.fromJson(posIngredients, type);
    }

    public String getName() {
        return name;
    }

    public String getPics() {
        return pictArray;
    }

    public String getNotes() {
        return notes;
    }

    public String getRecipe() {
        return recipe;
    }

    /*
        SET FUNCTIONS
     */
    public void setNecIngredient(String necIngredients) {
        this.necIngredients = necIngredients;
    }

    public void setNecIngredient(ArrayList<String> necIngredientsList) {
        this.necIngredients = necGson.toJson(necIngredientsList);
    }

    public void setPosIngredient(String posIngredients) {
        this.posIngredients = posIngredients;
    }

    public void setPosIngredient(ArrayList<String> posIngredientsList) {
        this.posIngredients = posGson.toJson(posIngredientsList);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPics(String pictArray) {
        this.pictArray = pictArray;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    /*
        ADD FUNCTIONS
     */
    public void addNecIngredient(String necIngredient) {
        ArrayList<String> necIngredientsList = getNecIngredientList();
        necIngredientsList.add(necIngredient);
        setNecIngredient(necIngredientsList);
    }

    public void addPosIngredient(String posIngredient) {
        ArrayList<String> posIngredientsList = getPosIngredientList();
        posIngredientsList.add(posIngredient);
        setPosIngredient(posIngredientsList);
    }

    /*
        REMOVE FUNCTTIONS
     */
    public void removeIngredient(String ingredient) {
        ArrayList<String> necIngredientsList = getNecIngredientList();
        ArrayList<String> posIngredientsList = getPosIngredientList();
        if (necIngredientsList.contains(ingredient)) {
            necIngredientsList.remove(ingredient);
            setNecIngredient(necIngredientsList);
        } else if (posIngredientsList.contains(ingredient)) {
            posIngredientsList.remove(ingredient);
            setPosIngredient(posIngredientsList);
        }
    }

    public void removeNecIngredient(String necIngredient) {
        ArrayList<String> necIngredientsList = getNecIngredientList();
        necIngredientsList.remove(necIngredient);
        setNecIngredient(necIngredientsList);
    }

    public void removePosIngredient(String posIngredient) {
        ArrayList<String> posIngredientsList = getPosIngredientList();
        posIngredientsList.remove(posIngredient);
        setPosIngredient(posIngredientsList);
    }

}
