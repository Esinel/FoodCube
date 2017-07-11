package com.os.stefanos.foodcube.activities.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.activities.RestaurantsActivity;
import com.os.stefanos.foodcube.adapters.RestaurantsListAdapter;
import com.os.stefanos.foodcube.tools.AppTools;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import dao.DatabaseHelper;
import dao.MealDAO;
import dao.RestaurantDAO;
import dao.TagDAO;
import model.Meal;
import model.Restaurant;
import model.Tag;

/**
 * Created by stefanos on 5.9.16..
 */
public class RestaurantListFragment extends Fragment {

    public static final String ARG_ITEM_ID = "restaurant_list";

    Activity activity;
    ListView restaurantListView;
    ArrayList<Restaurant> restaurants;

    RestaurantsListAdapter restaurantListAdapter;
    RestaurantDAO restaurantDAO;

    private GetRestaurantTask task;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = getActivity();
        restaurantDAO = new RestaurantDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_restoraunt_list, container, false);
        findViewsById(layout);
        task = new GetRestaurantTask(activity);
        task.execute((Void) null);

        updateView();
        return layout;
    }

    private void findViewsById(View view) {
        restaurantListView = (ListView) view.findViewById(R.id.list_restaurants);
    }

    public class GetRestaurantTask extends AsyncTask<Void, Void, ArrayList<Restaurant>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetRestaurantTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Restaurant> doInBackground(Void... arg0) {
            //checking if filtering
            int time = 0;
            if (getActivity().getIntent().getExtras() != null){
                time = getActivity().getIntent().getExtras().getInt("filterTime");
            }

            if (time == 0){
                ArrayList<Restaurant> restaurantList = RestaurantDAO.getRestaurants();
                fillWithImages(restaurantList);
                return restaurantList;
            }else{
                ArrayList<Restaurant> restaurantList = RestaurantDAO.getFilteredByWorkingTime(time);
                fillWithImages(restaurantList);
                return restaurantList;
            }

        }

        private ArrayList<Restaurant> fillWithImages(ArrayList<Restaurant> resaurants){
            Bitmap restPhoto;
            for (Restaurant r : resaurants){
                restPhoto = AppTools.getBitmapFromAssets(getContext(), r.getPhotoUrl());
                r.setSmallPhoto(restPhoto);
            }
            return restaurants;
        }

        @Override
        protected void onPostExecute(ArrayList<Restaurant> restList) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                Log.d("restaurants", restList.toString());
                restaurants = restList;
                if (restList != null) {
                    if (restList.size() != 0) {
                        restaurantListAdapter = new RestaurantsListAdapter(activity, R.layout.restaurant_list_item,
                                restList);
                        restaurantListView.setAdapter(restaurantListAdapter);
                    } else {
                        Toast.makeText(activity, "No Restaurants Records",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
    }

    /*
     * This method is invoked from RestaurantsActivity onFinishDialog() method. It is
     * called from CustomEmpDialogFragment when an employee record is updated.
     * This is used for communicating between fragments.
     */
    public void updateView() {


        task = new GetRestaurantTask(activity);
        task.execute((Void) null);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}