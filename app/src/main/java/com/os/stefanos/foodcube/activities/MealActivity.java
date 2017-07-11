package com.os.stefanos.foodcube.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.os.stefanos.foodcube.services.OrderService;
import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.tools.AppTools;

import org.w3c.dom.Text;

import dao.MealDAO;
import dao.RestaurantDAO;
import model.Meal;
import model.Restaurant;

public class MealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        // setting meal content
        Intent intent = getIntent();
        int mealID = intent.getIntExtra("mealID", 0);
        // we now have the restaurant ID -> iscrtaj interfejs na osnovu RestaurantDAO.getRestaurant
        Meal selectedMeal = MealDAO.getMeal(mealID);
        Bitmap mealSinglePhoto;
        mealSinglePhoto = AppTools.getBitmapFromAssetsMeals(this, selectedMeal.getImgUrl());
        selectedMeal.setImage(mealSinglePhoto);

        TextView mealName = (TextView) findViewById(R.id.txt_meal_title);
        TextView mealDescr = (TextView) findViewById(R.id.txt_meal_descr);
        TextView mealPrice = (TextView) findViewById(R.id.txt_meal_price);
        ImageView mealPhoto = (ImageView) findViewById(R.id.img_meal_single);

        mealName.setText(selectedMeal.getName());
        mealDescr.setText(selectedMeal.getDescription());
        mealPrice.setText(String.valueOf(selectedMeal.getPrice()));
        mealPhoto.setImageBitmap(selectedMeal.getImage());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar sb = Snackbar.make(view, "Porudzbina se obradjuje", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = sb.getView();
                sbView.setBackgroundColor(R.color.snackBarColor);
                sb.show();

                //startuj servis
                Intent intent = new Intent(getApplicationContext(), OrderService.class);
                startService(intent);
            }
        });
    }





}
