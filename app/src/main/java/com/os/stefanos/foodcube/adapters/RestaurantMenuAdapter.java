package com.os.stefanos.foodcube.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.activities.MealActivity;

import java.util.List;

import model.Meal;

/**
 * Created by stefanos on 10.9.16..
 */
public class RestaurantMenuAdapter extends ArrayAdapter<Meal> {

    private Context context;
    List<Meal> menuMeals;

    public RestaurantMenuAdapter(Context context, int layoutID ,List<Meal> menuMeals){
        super(context, R.layout.restaurant_menu_item, menuMeals);
        this.context = context;
        this.menuMeals = menuMeals;
    }

    private class ViewHolder {
        TextView menuMealNameTxt;
        TextView menuMealTagsTxt;
        ImageView menuMealPhoto;
    }

    @Override
    public int getCount() {
        return menuMeals.size();
    }

    @Override
    public Meal getItem(int position) {
        return menuMeals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.restaurant_menu_item, null);
            holder = new ViewHolder();


            holder.menuMealNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_rest_menu_meal_name);
            holder.menuMealTagsTxt = (TextView) convertView
                    .findViewById(R.id.txt_rest_menu_meal_tag);
            holder.menuMealPhoto = (ImageView) convertView.findViewById(R.id.img_rest_menu_meal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Meal meal = (Meal) getItem(position);
        holder.menuMealNameTxt.setText(meal.getName());
        //holder.menuMealTagsTxt.setText(meal.getTags().toString());
        holder.menuMealTagsTxt.setText(meal.getTagsBrusceti());
        holder.menuMealPhoto.setImageBitmap(meal.getImage());
        // GET ID AND SHOW NEW ACTIVITY
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealActivity.class);
                intent.putExtra("mealID", meal.getId()); //Put your id to your next Intent
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public void add(Meal meal) {
        menuMeals.add(meal);
        notifyDataSetChanged();
        super.add(meal);
    }

    @Override
    public void remove(Meal meal) {
        menuMeals.remove(meal);
        notifyDataSetChanged();
        super.remove(meal);
    }
}
