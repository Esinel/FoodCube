package com.os.stefanos.foodcube.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.activities.fragments.RestaurantAboutFragment;
import com.os.stefanos.foodcube.activities.fragments.RestaurantMenuFragment;

/**
 * Created by stefanos on 10.9.16..
 */
public class FragmentPageAdapterRestaurant extends FragmentPagerAdapter {

    String [] tabs;
    public FragmentPageAdapterRestaurant(FragmentManager fm, Context context){
        super(fm);
        tabs = context.getResources().getStringArray(R.array.restaurant_tabs);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RestaurantAboutFragment();
            case 1:
                return new RestaurantMenuFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
