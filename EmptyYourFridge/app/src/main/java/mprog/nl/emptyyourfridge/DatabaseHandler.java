package mprog.nl.emptyyourfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

/**
 * Created by Feli on 8-1-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //code used from/tutorial from http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "recipes";

    // Table name
    private static final String TABLE_RECIPES = "recipe";

    // Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_RECIPE = "recipe";
    private static final String KEY_NOTES = "notes";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "(" + KEY_NAME + " TEXT,"
                + KEY_RECIPE + " TEXT," + KEY_NOTES + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);

        // Create tables again
        onCreate(db);
    }

    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_RECIPE, recipe.getRecipe());
        values.put(KEY_NOTES, recipe.getNotes());

        // Inserting Row
        db.insert(TABLE_RECIPES, null, values);
        db.close(); // Closing database connection
    }

    public Recipe getRecipe(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECIPES, new String[]{
                        KEY_NAME, KEY_RECIPE, KEY_NOTES}, KEY_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Recipe recipe = new Recipe(cursor.getString(0), cursor.getString(1), cursor.getString(2));
        return recipe;
    }

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECIPES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                // Adding recipe to list
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }

        // return recipe list
        return recipeList;
    }

    public int updateRecipe(String oldName, Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_RECIPE, recipe.getRecipe());
        values.put(KEY_NOTES, recipe.getNotes());

        // updating row
        return db.update(TABLE_RECIPES, values, KEY_NAME + " = ?",
                new String[] { String.valueOf(oldName) });
    }

    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, KEY_NAME + " = ?",
                new String[]{String.valueOf(recipe.getName())});
        db.close();
    }

}