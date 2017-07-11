package com.os.stefanos.foodcube.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.Toast;

import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.adapters.RestaurantMenuAdapter;
import com.os.stefanos.foodcube.tools.AppTools;

import java.util.ArrayList;

import dao.FoodTypeDAO;
import dao.MealDAO;
import dao.TagDAO;
import model.Meal;
import model.Tag;

public class FilterByTagDialog extends DialogFragment {
    private ArrayList<Integer> tagList;
    private ArrayList<Meal> mealList;
    public static FilterByTagDialog newInstance(int num){
        FilterByTagDialog dialogFragment = new FilterByTagDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        dialogFragment.setArguments(bundle);

        return dialogFragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        tagList = new ArrayList<>();
        mealList = new ArrayList<>();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Filter by tag")
                .setMultiChoiceItems(R.array.filter_tags, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean checked) {
                        if(checked){
                            tagList.add(i+1);
                        }

                    }
                })
                .setIcon(android.R.drawable.ic_menu_sort_by_size)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // set adapter to match meals by tags

                                Intent intent = getActivity().getIntent();
                                int restaurantID = intent.getIntExtra("restaurantID", 0);
                                mealList = MealDAO.getMealsByTagList(tagList, restaurantID);
                                fillWithImages(mealList);

                                if (mealList != null) {
                                    if (mealList.size() != 0) {
                                        RestaurantMenuAdapter restaurantMenuAdapter = new RestaurantMenuAdapter(getActivity(), R.layout.restaurant_menu_item,
                                                mealList);
                                        ListView mealListView = (ListView) getActivity().findViewById(R.id.list_meals);
                                        mealListView.setAdapter(restaurantMenuAdapter);
                                    } else {
                                        ListView mealListView = (ListView) getActivity().findViewById(R.id.list_meals);
                                        mealListView.setAdapter(null);
                                        Toast.makeText(getActivity(), "No Meals Records",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }

                            }
                        }
                )
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
                    }
                })
                .create();
    }

    private ArrayList<Meal> fillWithImages(ArrayList<Meal> meals){
        Bitmap mealPhoto;
        for (Meal m : meals){
            mealPhoto = AppTools.getBitmapFromAssetsMeals(getContext(), m.getImgUrl());
            m.setImage(mealPhoto);
        }
        return meals;
    }
}