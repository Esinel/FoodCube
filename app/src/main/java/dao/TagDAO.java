package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import model.Tag;

/**
 * Created by stefanos on 7.9.16..
 */
public class TagDAO {

    public static final String TAG = "TagDAO";

    protected static SQLiteDatabase mDatabase;
    private static DatabaseHelper mDatabaseHelper;
    private Context mContext;

    public TagDAO(Context context) {
        this.mContext = context;
        mDatabaseHelper = DatabaseHelper.getHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void save(Tag tag) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TAG_NAME, tag.getName());
        mDatabase.insert(DatabaseHelper.TABLE_TAG, null, values);
    }

    public static ArrayList<Tag> getTags() {
        ArrayList<Tag> tags = new ArrayList<Tag>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_TAG,
                new String[]{DatabaseHelper.COLUMN_TAG_ID, DatabaseHelper.COLUMN_TAG_NAME},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Tag tag = new Tag();
            tag.setId(cursor.getInt(0));
            tag.setName(cursor.getString(1));
            tags.add(tag);
        }
        return tags;
    }

    public static Tag getTag(int id) {
        Tag tag = null;

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_TAG
                + " WHERE " + DatabaseHelper.COLUMN_TAG_ID + " = ?";

        Cursor cursor = mDatabase.rawQuery(sql, new String[]{id + ""});

        if (cursor.moveToNext()) {
            tag = new Tag();
            tag.setId(cursor.getInt(0));
            tag.setName(cursor.getString(1));
        }
        return tag;

    }


    public static List<Tag> stringListToTagList(List<String> tagFilter)
    {
        List<Tag> tags = new ArrayList<Tag>();

        for(String tagId : tagFilter)
        {
            tags.add(TagDAO.getTag(Integer.parseInt(tagId)));
        }

        return tags;
    }
}
