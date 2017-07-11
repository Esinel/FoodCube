package dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;

import com.os.stefanos.foodcube.tools.AppTools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Restaurant;
import model.Tag;

/**
 * Created by stefanos on 7.9.16..
 */
public class RestaurantDAO {

    public static final String TAG = "RestaurantDAO";
    protected static SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;
    public Context mContext;

    public RestaurantDAO(Context context){
        this.mContext = context;
        mDatabaseHelper = DatabaseHelper.getHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void save(Restaurant restaurant){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_RESTAURANT_NAME, restaurant.getName());
        values.put(DatabaseHelper.COLUMN_RESTAURANT_DESCRIPTION, restaurant.getDescription());
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_PHOTO);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_ADDRESS_LAT);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_ADDRESS_LNG);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_STARTHOUR);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_STARTMINUTE);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_ENDHOUR);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_ENDMINUTE);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_PHONE);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_EMAIL);
        values.putNull(DatabaseHelper.COLUMN_RESTAURANT_SITE);


        // and so on....

        mDatabase.insert(DatabaseHelper.TABLE_RESTAURANT, null, values);
    }

    public static ArrayList<Restaurant> getRestaurants() {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        URL restSiteURL = null;
        String siteString = "";

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_RESTAURANT,
                new String[]{DatabaseHelper.COLUMN_RESTAURANT_ID,
                             DatabaseHelper.COLUMN_RESTAURANT_NAME,
                             DatabaseHelper.COLUMN_RESTAURANT_DESCRIPTION,
                             DatabaseHelper.COLUMN_RESTAURANT_PHOTO,
                             DatabaseHelper.COLUMN_RESTAURANT_ADDRESS_LAT,
                             DatabaseHelper.COLUMN_RESTAURANT_ADDRESS_LNG,
                             DatabaseHelper.COLUMN_RESTAURANT_STARTHOUR,
                             DatabaseHelper.COLUMN_RESTAURANT_STARTMINUTE,
                             DatabaseHelper.COLUMN_RESTAURANT_ENDHOUR,
                             DatabaseHelper.COLUMN_RESTAURANT_ENDMINUTE,
                             DatabaseHelper.COLUMN_RESTAURANT_PHONE,
                             DatabaseHelper.COLUMN_RESTAURANT_EMAIL,
                             DatabaseHelper.COLUMN_RESTAURANT_SITE},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Restaurant restaurant = new Restaurant();

            restaurant.setId(cursor.getInt(0));
            restaurant.setName(cursor.getString(1));
            restaurant.setDescription(cursor.getString(2));
            restaurant.setPhotoUrl(cursor.getString(3));
            // na osnovu foto URL -> plus custom fun za resize
            restaurant.setSmallPhoto(null);
            restaurant.setLargePhoto(null);
            // funkcija long i lat Adr(lng, lat)
            restaurant.setLatitude(cursor.getFloat(4));
            restaurant.setLongitude(cursor.getFloat(5));
            restaurant.setAddress(null);

            restaurant.setStartHour(cursor.getInt(6));
            restaurant.setStartMinute(cursor.getInt(7));
            restaurant.setEndHour(cursor.getInt(8));
            restaurant.setEndMinute(cursor.getInt(9));
            restaurant.setPhone(cursor.getString(10));
            restaurant.setEmail(cursor.getString(11));
            siteString = cursor.getString(12);
            try {
                restSiteURL = new URL(siteString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            restaurant.setSite(restSiteURL);

            restaurants.add(restaurant);
        }
        return restaurants;
    }

    // with native query
    public static Restaurant getRestaurant(int id) {
        Restaurant restaurant = null;
        URL restSiteURL = null;
        String siteString = "";

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_RESTAURANT
                + " WHERE " + DatabaseHelper.COLUMN_RESTAURANT_ID + " = ?";

        Cursor cursor = mDatabase.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            restaurant = new Restaurant();
            restaurant.setId(cursor.getInt(0));
            restaurant.setName(cursor.getString(1));
            restaurant.setDescription(cursor.getString(2));

            restaurant.setPhotoUrl(cursor.getString(3));
            // na osnovu foto URL -> plus custom fun za resize
            restaurant.setSmallPhoto(null);
            restaurant.setLargePhoto(null);
            // funkcija long i lat Adr(lng, lat)
            restaurant.setLatitude(cursor.getFloat(4));
            restaurant.setLongitude(cursor.getFloat(5));

            restaurant.setStartHour(cursor.getInt(6));
            restaurant.setStartMinute(cursor.getInt(7));
            restaurant.setEndHour(cursor.getInt(8));
            restaurant.setEndMinute(cursor.getInt(9));
            restaurant.setPhone(cursor.getString(10));
            restaurant.setEmail(cursor.getString(11));
            siteString = cursor.getString(12);
            try {
                restSiteURL = new URL(siteString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            restaurant.setSite(restSiteURL);
        }
        return restaurant;
    }


    public static ArrayList<Restaurant> getFilteredByWorkingTime(int startHour)
    {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        Restaurant restaurant;
        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_RESTAURANT
                + " WHERE " + DatabaseHelper.COLUMN_RESTAURANT_STARTHOUR + " = ?";

        Cursor cursor = mDatabase.rawQuery(sql, new String[] { String.valueOf(startHour)+"" });

        while (cursor.moveToNext()) {
            restaurant = new Restaurant();
            URL restSiteURL = null;
            String siteString = "";

            restaurant.setId(cursor.getInt(0));
            restaurant.setName(cursor.getString(1));
            restaurant.setDescription(cursor.getString(2));
            restaurant.setPhotoUrl(cursor.getString(3));
            // na osnovu foto URL -> plus custom fun za resize
            restaurant.setSmallPhoto(null);
            restaurant.setLargePhoto(null);
            // funkcija long i lat Adr(lng, lat)
            restaurant.setLatitude(cursor.getFloat(4));
            restaurant.setLongitude(cursor.getFloat(5));

            restaurant.setStartHour(cursor.getInt(6));
            restaurant.setStartMinute(cursor.getInt(7));
            restaurant.setEndHour(cursor.getInt(8));
            restaurant.setEndMinute(cursor.getInt(9));
            restaurant.setPhone(cursor.getString(10));
            restaurant.setEmail(cursor.getString(11));
            siteString = cursor.getString(12);
            try {
                restSiteURL = new URL(siteString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            restaurant.setSite(restSiteURL);

            restaurants.add(restaurant);
        }
        return restaurants;
    }
}
