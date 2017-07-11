package com.os.stefanos.foodcube.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.activities.RestaurantActivity;
import com.os.stefanos.foodcube.activities.RestaurantsActivity;

import java.util.List;

import model.Restaurant;

/**
 * Created by stefanos on 8.9.16..
 */
public class RestaurantsListAdapter extends ArrayAdapter<Restaurant>{

    private Context context;
    List<Restaurant> restaurants;

    public RestaurantsListAdapter(Context context, int layoutID ,List<Restaurant> restaurants){
        super(context, R.layout.restaurant_list_item, restaurants);
        this.context = context;
        this.restaurants = restaurants;
    }

    private class ViewHolder {
        TextView restNameTxt;
        TextView restDescrTxt;
        ImageView restImg;
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Restaurant getItem(int position) {
        return restaurants.get(position);
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
            convertView = inflater.inflate(R.layout.restaurant_list_item, null);
            holder = new ViewHolder();


            holder.restNameTxt = (TextView) convertView
                    .findViewById(R.id.txt_rest_list_name);
            holder.restDescrTxt = (TextView) convertView
                    .findViewById(R.id.txt_rest_list_descr);
            holder.restImg = (ImageView) convertView.findViewById(R.id.img_restaurant_list);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Restaurant restaurant = (Restaurant) getItem(position);
        holder.restNameTxt.setText(restaurant.getName());
        holder.restDescrTxt.setText(restaurant.getDescription());
        holder.restImg.setImageBitmap(restaurant.getSmallPhoto());
        // GET ID AND SHOW NEW ACTIVITY
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RestaurantActivity.class);
                intent.putExtra("restaurantID", restaurant.getId()); //Put your id to your next Intent
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public void add(Restaurant restaurant) {
        restaurants.add(restaurant);
        notifyDataSetChanged();
        super.add(restaurant);
    }

    @Override
    public void remove(Restaurant restaurant) {
        restaurants.remove(restaurant);
        notifyDataSetChanged();
        super.remove(restaurant);
    }
}
