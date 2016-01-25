package mprog.nl.emptyyourfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Empty Your Fridge App - Feli Nicolaes, feli.nicolaes@uva.student.nl
 *
 * DatabaseHandler contains all functions for creating a database and working with it.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //tutorial from http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipes";
    private static final String TABLE_RECIPES = "recipe";

    // Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_RECIPE = "recipe";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_NEC_INGR = "necIngredients";
    private static final String KEY_NEC_AMOUNT = "necAmounts";
    private static final String KEY_POS_INGR = "posIngredients";
    private static final String KEY_POS_AMOUNT = "posAmounts";
    private static final String KEY_PICS = "pictureList";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Creating Tables
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "(" + KEY_NAME + " TEXT,"
                + KEY_RECIPE + " TEXT," + KEY_NOTES + " TEXT," + KEY_NEC_INGR + " TEXT,"
                + KEY_NEC_AMOUNT + " TEXT," + KEY_POS_INGR + " TEXT," + KEY_POS_AMOUNT + " TEXT," +
                KEY_PICS + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    /* Upgrading database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);

        // Create tables
        onCreate(db);
    }

    /* Add new recipe to database
     */
    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Put all values into a ContentValues
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_RECIPE, recipe.getRecipe());
        values.put(KEY_NOTES, recipe.getNotes());
        values.put(KEY_NEC_INGR, recipe.getNecIngredient());
        values.put(KEY_NEC_AMOUNT, recipe.getNecAmount());
        values.put(KEY_POS_INGR, recipe.getPosIngredient());
        values.put(KEY_POS_AMOUNT, recipe.getPosAmount());
        values.put(KEY_PICS, recipe.getPics());

        // Inserting Row with values
        db.insert(TABLE_RECIPES, null, values);
        db.close();
    }

    /* Return recipe with given name
     */
    public Recipe getRecipe(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        //find recipe with corresponding name
        Cursor cursor = db.query(TABLE_RECIPES, new String[]{ KEY_NAME, KEY_RECIPE, KEY_NOTES,
                        KEY_NEC_INGR, KEY_NEC_AMOUNT, KEY_POS_INGR, KEY_POS_AMOUNT, KEY_PICS}, KEY_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //Create recipe object
        Recipe recipe = new Recipe(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7));
        return recipe;
    }

    /* Get all recipes from database
     */
    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECIPES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // loop through all rows and add all recipe-objects to list
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7));
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }

        return recipeList;
    }

    /* Update recipe with given name
     */
    public int updateRecipe(String oldName, Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_RECIPE, recipe.getRecipe());
        values.put(KEY_NOTES, recipe.getNotes());
        values.put(KEY_NEC_INGR, recipe.getNecIngredient());
        values.put(KEY_NEC_AMOUNT, recipe.getNecAmount());
        values.put(KEY_POS_INGR, recipe.getPosIngredient());
        values.put(KEY_POS_AMOUNT, recipe.getPosAmount());
        values.put(KEY_PICS, recipe.getPics());

        // update row
        return db.update(TABLE_RECIPES, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(oldName) });
    }

    /* Delete recipe with given name
     */
    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, KEY_NAME + " = ?",
                new String[]{String.valueOf(recipe.getName())});
        db.close();
    }

}