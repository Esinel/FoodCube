package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import model.Meal;
import model.Restaurant;
import model.Tag;

/**
 * Created by stefanos on 10.9.16..
 */
public class MealDAO {

    public static final String TAG = "MealDAO";
    protected static SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    private Context mContext;

    public MealDAO(Context context){
        this.mContext = context;
        mDatabaseHelper = DatabaseHelper.getHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void save(Meal meal){
        FoodTypeDAO ftDAO = new FoodTypeDAO(mContext);
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_RESTAURANT_NAME, meal.getName());
        values.put(DatabaseHelper.COLUMN_RESTAURANT_DESCRIPTION, meal.getDescription());
        values.put(DatabaseHelper.COLUMN_MEAL_PRICE, meal.getPrice());

        //intermediate "FoodType" table
        for (Tag tag : meal.getTags()){
            ftDAO.save(meal, tag);
        }
        mDatabase.insert(DatabaseHelper.TABLE_MEAL, null, values);
    }

    public static ArrayList<Meal> getMeals() {
        ArrayList<Meal> meals = new ArrayList<Meal>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_MEAL,
                new String[]{DatabaseHelper.COLUMN_MEAL_ID,
                        DatabaseHelper.COLUMN_MEAL_NAME,
                        DatabaseHelper.COLUMN_MEAL_DESCRIPTION,
                        DatabaseHelper.COLUMN_MEAL_PRICE,
                        DatabaseHelper.COLUMN_MEAL_RESTAURANT_ID,
                        DatabaseHelper.COLUMN_MEAL_PHOTO},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Meal meal = new Meal();
            meal.setId(cursor.getInt(0));
            meal.setName(cursor.getString(1));
            meal.setDescription(cursor.getString(2));
            meal.setPrice(cursor.getLong(3));
            meal.setRestaurant(RestaurantDAO.getRestaurant(cursor.getInt(4)));
            meal.setImgUrl(cursor.getString(5));
            List<Tag> mealTags = FoodTypeDAO.getMealTags(meal);
            meal.setTags(mealTags);
            meals.add(meal);
        }
        return meals;
    }

    public static ArrayList<Meal> getMealsByRest(int id) {
        ArrayList<Meal> meals = new ArrayList<Meal>();
        Meal meal;

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_MEAL
                + " WHERE " + DatabaseHelper.COLUMN_MEAL_RESTAURANT_ID + " = ?";

        Cursor cursor = mDatabase.rawQuery(sql, new String[] { id + "" });

        while (cursor.moveToNext()) {
            meal = new Meal();
            meal.setId(cursor.getInt(0));
            meal.setName(cursor.getString(1));
            meal.setDescription(cursor.getString(2));
            meal.setPrice(cursor.getLong(3));
            meal.setRestaurant(RestaurantDAO.getRestaurant(cursor.getInt(4)));
            meal.setImgUrl(cursor.getString(5));
            List<Tag> mealTags = FoodTypeDAO.getMealTags(meal);
            meal.setTags(mealTags);

            meals.add(meal);
        }
        return meals;
    }


    public static Meal getMeal(int id) {
        Meal meal = null;

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_MEAL
                + " WHERE " + DatabaseHelper.COLUMN_MEAL_ID + " = ?";

        Cursor cursor = mDatabase.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            meal = new Meal();
            meal.setId(cursor.getInt(0));
            meal.setName(cursor.getString(1));
            meal.setDescription(cursor.getString(2));
            meal.setPrice(cursor.getLong(3));
            meal.setRestaurant(RestaurantDAO.getRestaurant(cursor.getInt(4)));
            meal.setImgUrl(cursor.getString(5));
            List<Tag> mealTags = FoodTypeDAO.getMealTags(meal);
            meal.setTags(mealTags);
        }
        return meal;
    }

    public static ArrayList<Meal> getMealsByTagList(ArrayList<Integer> tagList, int restaurantID) {
        ArrayList<Meal> meals = new ArrayList<Meal>();
        Meal meal;
        String inCondition = "(";
        for (int i=0; i<tagList.size(); i++){
            inCondition += String.valueOf(tagList.get(i));
            if (i < tagList.size() - 1){
                inCondition += ", ";
            }else{
                inCondition += ") ";
            }
        }



        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_MEAL + " m INNER JOIN " + DatabaseHelper.TABLE_FOODTYPE + " f ON m._id = f.meal_id WHERE f.tag_id IN " + inCondition + " AND m.restaurant_id = " + String.valueOf(restaurantID) + " ;";

        Cursor cursor = mDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            meal = new Meal();
            meal.setId(cursor.getInt(0));
            meal.setName(cursor.getString(1));
            meal.setDescription(cursor.getString(2));
            meal.setPrice(cursor.getLong(3));
            meal.setRestaurant(RestaurantDAO.getRestaurant(cursor.getInt(4)));
            meal.setImgUrl(cursor.getString(5));
            List<Tag> mealTags = FoodTypeDAO.getMealTags(meal);
            meal.setTags(mealTags);

            meals.add(meal);
        }
        return meals;
    }
}
