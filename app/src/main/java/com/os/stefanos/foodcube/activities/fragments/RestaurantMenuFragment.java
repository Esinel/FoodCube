package com.os.stefanos.foodcube.activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.adapters.RestaurantMenuAdapter;
import com.os.stefanos.foodcube.dialogs.FilterByTagDialog;
import com.os.stefanos.foodcube.tools.AppTools;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import dao.FoodTypeDAO;
import dao.MealDAO;
import dao.TagDAO;
import model.Meal;


public class RestaurantMenuFragment extends Fragment {
    private static final String ARG_ITEM_ID = "restaurant_menu";

    Activity activity;
    ListView mealListView;
    ArrayList<Meal> menuMeals;

    RestaurantMenuAdapter restaurantMenuAdapter;
    MealDAO mealDAO;
    FoodTypeDAO foodTypeDAO;
    TagDAO tagDAO;

    private GetMenuMealsTask task;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        mealDAO = new MealDAO(activity);
        foodTypeDAO = new FoodTypeDAO(activity);
        tagDAO = new TagDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstnaceState){

    View layout = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);
    findViewsById(layout);
    task = new GetMenuMealsTask(activity);
    task.execute((Void) null);
    updateView();
    return layout;
    }

    private void findViewsById(View view) {
        mealListView = (ListView) view.findViewById(R.id.list_meals);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //filtering by tags
        FloatingActionButton btnFilter = (FloatingActionButton) view.findViewById(R.id.btn_filter_meal);
        btnFilter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //show dialog for TAG filtering
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        DialogFragment dialog = new FilterByTagDialog(); // creating new object
                        dialog.show(fm, "123");
                    }
                }
        );
    }
/*
    @Override
    protected Dialog onCreateDialog(int id){
        return new TimePickerDialog(RestaurantActivity.this, fTimePickerListener, 12, 0, false);
    }

    protected TimePickerDialog.OnTimeSetListener fTimePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    Intent filterIntent = new Intent(RestaurantActivity.this, RestaurantsActivity.class);
                    filterIntent.putExtra("filterTime", i);
                    startActivity(filterIntent);
                }
            }; */

public class GetMenuMealsTask extends AsyncTask<Void, Void, ArrayList<Meal>> {

    private final WeakReference<Activity> activityWeakRef;

    public GetMenuMealsTask(Activity context) {
        this.activityWeakRef = new WeakReference<Activity>(context);
    }

    @Override
    protected ArrayList<Meal> doInBackground(Void... arg0) {

        Intent intent = getActivity().getIntent();
        int restID = intent.getIntExtra("restaurantID", 0);
        ArrayList<Meal> menuMealList = MealDAO.getMealsByRest(restID);
        fillWithImages(menuMealList);
        return menuMealList;
    }

    private ArrayList<Meal> fillWithImages(ArrayList<Meal> meals){
        Bitmap mealPhoto;
        for (Meal m : meals){
            mealPhoto = AppTools.getBitmapFromAssetsMeals(getContext(), m.getImgUrl());
            m.setImage(mealPhoto);
        }
        return meals;
    }

    @Override
    protected void onPostExecute(ArrayList<Meal> menuMealList) {
        if (activityWeakRef.get() != null
                && !activityWeakRef.get().isFinishing()) {
            Log.d("meals", menuMealList.toString());
            menuMeals = menuMealList;
            if (menuMealList != null) {
                if (menuMealList.size() != 0) {
                    restaurantMenuAdapter = new RestaurantMenuAdapter(activity, R.layout.restaurant_menu_item,
                            menuMealList);
                    mealListView.setAdapter(restaurantMenuAdapter);
                } else {
                    Toast.makeText(activity, "No Meals Records",
                            Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}


    public void updateView() {
        task = new GetMenuMealsTask(activity);
        task.execute((Void) null);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
