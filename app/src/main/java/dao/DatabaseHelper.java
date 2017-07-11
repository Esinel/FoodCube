package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;


/**
 * Created by stefanos on 6.9.16..
 */

// -> Class that is rensponsible for creating/managing database structure
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_DIR = "/data/data/com.os.stefanos.foodcube/databases/";
    private static final String DATABASE_NAME = "restaurant.db";
    private static final String DATABASE_PATH = DATABASE_DIR + DATABASE_NAME;

    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "DatabaseHelper";

    private final Context myContext;


    // restaurant table
    public static final String TABLE_RESTAURANT = "restaurant_table";
    public static final String COLUMN_RESTAURANT_ID = "_id";
    public static final String COLUMN_RESTAURANT_NAME = "name";
    public static final String COLUMN_RESTAURANT_DESCRIPTION = "description";
    public static final String COLUMN_RESTAURANT_PHOTO = "photo";
    public static final String COLUMN_RESTAURANT_ADDRESS_LAT = "address_lat";
    public static final String COLUMN_RESTAURANT_ADDRESS_LNG = "address_lng";
    public static final String COLUMN_RESTAURANT_STARTHOUR = "start_hour";
    public static final String COLUMN_RESTAURANT_STARTMINUTE = "start_minute";
    public static final String COLUMN_RESTAURANT_ENDHOUR = "end_hour";
    public static final String COLUMN_RESTAURANT_ENDMINUTE = "end_minute";
    public static final String COLUMN_RESTAURANT_PHONE = "phone";
    public static final String COLUMN_RESTAURANT_EMAIL = "email";
    public static final String COLUMN_RESTAURANT_SITE = "site";
    //restoraunt has more than 1 meal (meal table has restoraunt id)


    // meal table
    public static final String TABLE_MEAL = "meal_table";
    public static final String COLUMN_MEAL_ID = "_id";
    public static final String COLUMN_MEAL_NAME = "name";
    public static final String COLUMN_MEAL_DESCRIPTION = "description";
    public static final String COLUMN_MEAL_PRICE = "price";
    public static final String COLUMN_MEAL_RESTAURANT_ID = "restaurant_id";
    public static final String COLUMN_MEAL_PHOTO = "photo";

    // tag table
    public static final String TABLE_TAG = "tag_table";
    public static final String COLUMN_TAG_ID = "_id";
    public static final String COLUMN_TAG_NAME = "name";

    // foodType table - associative entry
    public static final String TABLE_FOODTYPE = "foodType_table";
    public static final String COLUMN_FOODTYPE_MEAL_ID = "meal_id";
    public static final String COLUMN_FOODTYPE_TAG_ID = "tag_id";


    // TABLE CREATION

    // restaurant table creation
    private static final String SQL_CREATE_TABLE_RESTAURANT = "CREATE TABLE " + TABLE_RESTAURANT + "("
            + COLUMN_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RESTAURANT_NAME + " TEXT NOT NULL, "
            + COLUMN_RESTAURANT_DESCRIPTION + " TEXT NOT NULL, "
            + COLUMN_RESTAURANT_PHOTO + " TEXT, "
            + COLUMN_RESTAURANT_ADDRESS_LAT + " REAL, "
            + COLUMN_RESTAURANT_ADDRESS_LNG + " REAL, "
            + COLUMN_RESTAURANT_STARTHOUR + " INTEGER, "
            + COLUMN_RESTAURANT_STARTMINUTE + " INTEGER, "
            + COLUMN_RESTAURANT_ENDHOUR + " INTEGER, "
            + COLUMN_RESTAURANT_ENDMINUTE + " INTEGER, "
            + COLUMN_RESTAURANT_PHONE + " TEXT, "
            + COLUMN_RESTAURANT_EMAIL + " TEXT, "
            + COLUMN_RESTAURANT_SITE + " TEXT "
            + ");";

    // meal table creation
    private static final String SQL_CREATE_TABLE_MEAL = "CREATE TABLE " + TABLE_MEAL + "("
            + COLUMN_MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MEAL_NAME + " TEXT NOT NULL, "
            + COLUMN_MEAL_DESCRIPTION + " TEXT NOT NULL, "
            + COLUMN_MEAL_PRICE + " REAL NOT NULL, "
            + COLUMN_MEAL_RESTAURANT_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_MEAL_RESTAURANT_ID + ") REFERENCES restaurant_table(" + COLUMN_RESTAURANT_ID + ")"
            + ");";

    // tag table creation
    private static final String SQL_CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG + "("
            + COLUMN_TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TAG_NAME + " TEXT NOT NULL "
            + ");";

    // foodType table creation
    private static final String SQL_CREATE_TABLE_FOODTYPE = "CREATE TABLE " + TABLE_FOODTYPE + "("
            + COLUMN_FOODTYPE_MEAL_ID + " INTEGER NOT NULL, "
            + COLUMN_FOODTYPE_TAG_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_FOODTYPE_MEAL_ID + ") REFERENCES meal_table(" + COLUMN_MEAL_ID + ")"
            + "FOREIGN KEY(" + COLUMN_FOODTYPE_TAG_ID + ") REFERENCES tag_table(" + COLUMN_TAG_ID + ")"
            + "PRIMARY KEY (" + COLUMN_FOODTYPE_MEAL_ID + ", " + COLUMN_FOODTYPE_TAG_ID + ")"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    /*
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.myContext = context;
    }
    */

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_RESTAURANT);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MEAL);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TAG);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FOODTYPE);
        */
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading db from version +" + oldVersion + " to " + newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_RESTAURANT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_MEAL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_TAG);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_FOODTYPE);

        onCreate(sqLiteDatabase);
    }

    public void createDatabase() {
        createDB();
    }

    public void createDB() {
        boolean dbExist = DBExists();

        if (!DBExists()) {
            //this.getWritableDatabase();

            copyDBFromResource();
        }else{
            //this.getWritableDatabase();

            copyDBFromResource();
        }
    }


    private boolean DBExists() {

        SQLiteDatabase db = null;

        try {
            String databasePath = DATABASE_PATH;
            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
        } catch (SQLiteException e) {
            Log.e("SqlHelper", "database not found!");
        }
        return db != null ? true : false;
    }

    private void copyDBFromResource(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String dbFilePath = DATABASE_PATH;

        try{
            inputStream = myContext.getAssets().open(DATABASE_NAME);
            outputStream = new FileOutputStream(dbFilePath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0 ){
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.w("Database Helper", "DB compied");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getHelper(Context context){
        if (instance == null)
            instance = new DatabaseHelper(context);
        return instance;
    }


}
