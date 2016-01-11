package mprog.nl.emptyyourfridge;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Feli on 8-1-2016.
 */
public class Recipe {
    ArrayList<String> necIngredients;
    ArrayList<String> posIngredients;
    String name;
    ArrayList<Image> pictArray;
    String notes;
    String recipe;

    public Recipe(String name, String recipe, String notes) {
        this.name = name;
        this.necIngredients = new ArrayList<String>();
        this.posIngredients = new ArrayList<String>();
        this.recipe = recipe;
        this.pictArray = new ArrayList<Image>();
        this.notes = notes;
    }

    public Recipe(String name, ArrayList<String> necIngredients, ArrayList<String> posIngredients,
                  String recipe) {
        this.name = name;
        this.necIngredients = necIngredients;
        this.posIngredients = posIngredients;
        this.recipe = recipe;
        this.pictArray = new ArrayList<Image>();
        this.notes = "";
    }

    /*
        GET FUNCTIONS
     */
    public ArrayList<String> getIngredient() {
        ArrayList<String> allIngredient = necIngredients;
        allIngredient.addAll(posIngredients);
        return allIngredient;
    }

    public ArrayList<String> getNecIngredient() {
        return necIngredients;
    }

    public ArrayList<String> getPosIngredient() {
        return posIngredients;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Image> getPics() {
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
    public void setNecIngredient(ArrayList<String> necIngredients) {
        this.necIngredients = necIngredients;
    }

    public void setPosIngredient(ArrayList<String> posIngredients) {
        this.posIngredients = posIngredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPics(ArrayList<Image> pictArray) {
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
        necIngredients.add(necIngredient);
    }

    public void addPosIngredient(String posIngredient) {
        posIngredients.add(posIngredient);
    }

    public void addPicture(Image picture) {
        pictArray.add(picture);
    }

    /*
        REMOVE FUNCTTIONS
     */
    public void removeNecIngredient(String necIngredient) {
        necIngredients.remove(necIngredient);
    }

    public void removePosIngredient(String posIngredient) {
        posIngredients.remove(posIngredient);
    }

    public void removePicture(Image picture) {
        pictArray.remove(picture);
    }

}
