package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import model.Meal;
import model.Tag;

/**
 * Created by stefanos on 10.9.16..
 */
public class FoodTypeDAO {
    public static final String TAG = "FoodTypeDAO";
    protected static SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;

    public FoodTypeDAO(Context context){
        this.mContext = context;
        mDatabaseHelper = DatabaseHelper.getHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void save(Meal meal, Tag tag){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FOODTYPE_MEAL_ID, meal.getId());
        values.put(DatabaseHelper.COLUMN_FOODTYPE_MEAL_ID, tag.getId());

        mDatabase.insert(DatabaseHelper.TABLE_FOODTYPE, null, values);
    }

    public static ArrayList<Tag> getMealTags(Meal meal){
        ArrayList<Tag> tags = new ArrayList<Tag>();

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_FOODTYPE
                + " WHERE " + DatabaseHelper.COLUMN_FOODTYPE_MEAL_ID + " = ?";

        Cursor cursor = mDatabase.rawQuery(sql, new String[] { meal.getId() + "" });

        while (cursor.moveToNext()) {
            Tag tag = TagDAO.getTag(cursor.getInt(1));
            tags.add(tag);
        }
        return tags;
    }

    public static ArrayList<Meal> getTagedMeals(Tag tag){
        ArrayList<Meal> meals = new ArrayList<Meal>();

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_FOODTYPE
                + " WHERE " + DatabaseHelper.COLUMN_FOODTYPE_TAG_ID + " = ?";

        Cursor cursor = mDatabase.rawQuery(sql, new String[] { tag.getId() + "" });

        while (cursor.moveToNext()) {
            Meal meal = MealDAO.getMeal(cursor.getInt(0));
            meals.add(meal);
        }
        return meals;
    }

    public static ArrayList<Meal> getTagedMealsById(int tagID){
        ArrayList<Meal> meals = new ArrayList<Meal>();

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_FOODTYPE
                + " WHERE " + DatabaseHelper.COLUMN_FOODTYPE_TAG_ID + " = ?";

        Cursor cursor = mDatabase.rawQuery(sql, new String[] { tagID + "" });

        while (cursor.moveToNext()) {
            Meal meal = MealDAO.getMeal(cursor.getInt(0));
            meals.add(meal);
        }
        return meals;
    }

}
