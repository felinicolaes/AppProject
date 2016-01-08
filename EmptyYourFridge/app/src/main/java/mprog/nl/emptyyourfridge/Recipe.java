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
    ArrayList<String> noteArray;
    String recipe;

    public Recipe(String name, String recipe) {
        this.name = name;
        this.recipe = recipe;
    }

    public Recipe(String name, ArrayList<String> necIngredients, ArrayList<String> posIngredients,
                  String recipe) {
        this.name = name;
        this.necIngredients = necIngredients;
        this.posIngredients = posIngredients;
        this.recipe = recipe;
        this.pictArray = new ArrayList<Image>();
        this.noteArray = new ArrayList<String>();
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

    public ArrayList<String> getNotes() {
        return noteArray;
    }

    public String getRecipe() {
        return recipe;
    }

    /*
        SET FUNCTIONS
     */
    public void getNecIngredient(ArrayList<String> necIngredients) {
        this.necIngredients = necIngredients;
    }

    public void getPosIngredient(ArrayList<String> posIngredients) {
        this.posIngredients = posIngredients;
    }

    public void getName(String name) {
        this.name = name;
    }

    public void getPics(ArrayList<Image> pictArray) {
        this.pictArray = pictArray;
    }

    public void getNotes(ArrayList<String> noteArray) {
        this.noteArray = noteArray;
    }

    public void getRecipe(String recipe) {
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

    public void addNote(String note) {
        noteArray.add(note);
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

    public void removeNote(String note) {
        noteArray.remove(note);
    }


}
