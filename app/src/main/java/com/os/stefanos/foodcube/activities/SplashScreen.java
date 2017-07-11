package com.os.stefanos.foodcube.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.os.stefanos.foodcube.R;

import dao.DatabaseHelper;

public class SplashScreen extends Activity {
    private static boolean mManual;
    private static final String PREF_VISIBILITY = "show_pref";

    Thread startTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean visibility = prefs.getBoolean(PREF_VISIBILITY, false);

        if (visibility == false) {
            mManual = true;
            Intent intent = new Intent(SplashScreen.this, RestaurantsActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_splash);

        //database initialisation
        DatabaseHelper ingredientHelper = new DatabaseHelper(this);
        ingredientHelper.createDatabase();
        Log.i("main", "onCreate zavrsen!");


        RelativeLayout relativeClick = (RelativeLayout) findViewById(R.id.lyt_splash);
        relativeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityFinished();
                mManual = true;
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mManual) {
                    return;
                }
                else {
                    activityFinished();
                }
            }
        }, 2500);

    }
    private void activityFinished() {
        Intent intent = new Intent(SplashScreen.this, RestaurantsActivity.class);
        startActivity(intent);
        finish();
    }


}
