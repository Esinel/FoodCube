package com.os.stefanos.foodcube.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by stefanos on 10.9.16..
 */
public class RestaurantAboutAdapter extends SimpleAdapter{


    public RestaurantAboutAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}
