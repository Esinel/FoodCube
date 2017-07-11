package com.os.stefanos.foodcube.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.os.stefanos.foodcube.R;
public class PrefsActivity extends PreferenceActivity {

    //public SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

//    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    //int storedPreference = preferences.getInt("storedInt", 0);
    /*
    edit preferences
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt("storedInt", storedPreference); // value to store
    editor.commit();
    */
}
