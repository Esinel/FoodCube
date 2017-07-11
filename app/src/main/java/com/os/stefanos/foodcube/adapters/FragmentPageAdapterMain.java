package com.os.stefanos.foodcube.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.activities.fragments.RestaurantListFragment;
import com.os.stefanos.foodcube.activities.fragments.RestaurantMapFragment;

/**
 * Created by stefanos on 5.9.16..
 */

// -> Adapter rensponsible for tab switching mechanism
public class FragmentPageAdapterMain extends FragmentPagerAdapter {

    String [] tabs;
    public FragmentPageAdapterMain(FragmentManager fm, Context context){
        super(fm);
        tabs = context.getResources().getStringArray(R.array.restaurants_tabs);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RestaurantListFragment();
            case 1:
                return new RestaurantMapFragment();
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