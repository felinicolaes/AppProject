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
    String necAmount;
    String posIngredients;
    String posAmount;
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
        this.necAmount = necGson.toJson(empty);
        this.posGson = new Gson();
        this.posIngredients = posGson.toJson(empty);
        this.posAmount = posGson.toJson(empty);
        this.recipe = recipe;
        this.pictArray = posGson.toJson(empty);
        this.notes = notes;
    }

    public Recipe(String name, String recipe, String notes, ArrayList<String> necIngredients, ArrayList<String> necAmount,
                  ArrayList<String> posIngredients, ArrayList<String> posAmount) {
        this.name = name;
        this.necGson = new Gson();
        this.necIngredients = necGson.toJson(necIngredients);
        this.necAmount = necGson.toJson(necAmount);
        this.posGson = new Gson();
        this.posIngredients = posGson.toJson(posIngredients);
        this.posAmount = posGson.toJson(posAmount);
        this.recipe = recipe;
        this.pictArray = posGson.toJson(new ArrayList<String>());
        this.notes = notes;
    }

    public Recipe(String name, String recipe, String notes, String necIngredients, String necAmount,
                  String posIngredients, String posAmount, String pictArray) {
        this.name = name;
        this.necGson = new Gson();
        this.necIngredients = necIngredients;
        this.necAmount = necAmount;
        this.posGson = new Gson();
        this.posIngredients = posIngredients;
        this.posAmount = posAmount;
        this.recipe = recipe;
        this.pictArray = pictArray;
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
        ArrayList<String> allIngredient = new ArrayList<String>();
        for (int i = 0 ; i < getNecIngredientList().size(); i++) {
            allIngredient.add(getNecAmountList().get(i) + " " + getNecIngredientList().get(i));
        }
        for (int i = 0 ; i < getPosIngredientList().size(); i++) {
            allIngredient.add(getPosAmountList().get(i) + " " + getPosIngredientList().get(i) + " (optional)");
        }
        return allIngredient;
    }

    public String getNecIngredient() {
        return necIngredients;
    }

    public String getNecAmount() {
        return necAmount;
    }

    public ArrayList<String> getNecIngredientList() {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return necGson.fromJson(necIngredients, type);
    }

    public ArrayList<String> getNecAmountList() {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return necGson.fromJson(necAmount, type);
    }

    public String getPosIngredient() {
        return posIngredients;
    }

    public String getPosAmount() {
        return posAmount;
    }

    public ArrayList<String> getPosIngredientList() {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return posGson.fromJson(posIngredients, type);
    }

    public ArrayList<String> getPosAmountList() {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return posGson.fromJson(posAmount, type);
    }

    public String getName() {
        return name;
    }

    public String getPics() {
        return pictArray;
    }

    public ArrayList<String> getPicsList() {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return posGson.fromJson(pictArray, type);
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
    public void setNecIngredient(ArrayList<String> necIngredientsList) {
        this.necIngredients = necGson.toJson(necIngredientsList);
    }

    public void setNecAmount(ArrayList<String> necAmountList) {
        this.necAmount = necGson.toJson(necAmountList);
    }

    public void setPosIngredient(ArrayList<String> posIngredientsList) {
        this.posIngredients = posGson.toJson(posIngredientsList);
    }

    public void setPosAmount(ArrayList<String> posAmountList) {
        this.posAmount = posGson.toJson(posAmountList);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPics(ArrayList<String> pictArray) {
        this.pictArray = posGson.toJson(pictArray);;
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
    public void addNecIngredient(String necIngredient, String necAmount) {
        ArrayList<String> necIngredientsList = getNecIngredientList();
        necIngredientsList.add(necIngredient);
        setNecIngredient(necIngredientsList);
        ArrayList<String> necAmountList = getNecAmountList();
        necAmountList.add(necAmount);
        setNecAmount(necAmountList);
    }

    public void addPosIngredient(String posIngredient, String posAmount) {
        ArrayList<String> posIngredientsList = getPosIngredientList();
        posIngredientsList.add(posIngredient);
        setPosIngredient(posIngredientsList);
        ArrayList<String> posAmountList = getPosAmountList();
        posAmountList.add(posAmount);
        setPosAmount(posAmountList);
    }

    public void addPic(String pic) {
        ArrayList<String> picsList = getPicsList();
        picsList.add(pic);
        setPics(picsList);
    }

    /*
        REMOVE FUNCTTIONS
     */
    public void removeIngredient(String ingredient) {
        ArrayList<String> necIngredientsList = getNecIngredientList();
        ArrayList<String> posIngredientsList = getPosIngredientList();
        if (necIngredientsList.contains(ingredient)) {
            removeNecIngredient(ingredient);
        } else if (posIngredientsList.contains(ingredient)) {
            removePosIngredient(ingredient);
        }
    }

    public void removeNecIngredient(String ingredient) {
        ArrayList<String> necIngredientsList = getNecIngredientList();
        ArrayList<String> necAmountList = getNecAmountList();
        int i = necIngredientsList.indexOf(ingredient);
        necIngredientsList.remove(i);
        necAmountList.remove(i);
        setNecIngredient(necIngredientsList);
        setNecAmount(necAmountList);
    }

    public void removePosIngredient(String ingredient) {
        ArrayList<String> posIngredientsList = getPosIngredientList();
        ArrayList<String> posAmountList = getPosAmountList();
        int i = posIngredientsList.indexOf(ingredient);
        posAmountList.remove(i);
        posIngredientsList.remove(i);
        setPosIngredient(posIngredientsList);
        setPosAmount(posAmountList);
    }

    public void removePic(String pic) {
        ArrayList<String> picsList = getPicsList();
        picsList.remove(pic);
        setPics(picsList);
    }

}
