package com.os.stefanos.foodcube.activities.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.adapters.RestaurantAboutAdapter;
import com.os.stefanos.foodcube.tools.AppTools;

import org.w3c.dom.Text;

import dao.FoodTypeDAO;
import dao.RestaurantDAO;
import model.Restaurant;


public class RestaurantAboutFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_ITEM_ID = "restaurant_about";
    protected View mView;
    FoodTypeDAO foodTypeDAO;
    RestaurantDAO restaurantDAO;
    RestaurantAboutAdapter restaurantAboutAdapter;
    private OnFragmentInteractionListener mListener;

    public RestaurantAboutFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RestaurantAboutFragment newInstance(String param1, String param2) {
        RestaurantAboutFragment fragment = new RestaurantAboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //setting up aboutRest clicks
        Intent intent = getActivity().getIntent();
        int restaurantID = intent.getIntExtra("restaurantID", 0);
        Restaurant selectedRestaurant = RestaurantDAO.getRestaurant(restaurantID);
        Bitmap restPhoto;
        restPhoto = AppTools.getBitmapFromAssets(getContext(), selectedRestaurant.getPhotoUrl());
        selectedRestaurant.setSmallPhoto(restPhoto);



        // Inflating it with passed Restaurant

        TextView name = (TextView) view.findViewById(R.id.txt_rest_about_name);
        TextView descr = (TextView) view.findViewById(R.id.txt_rest_about_descr);
        TextView call = (TextView) view.findViewById(R.id.txt_rest_about_call);
        TextView sms = (TextView) view.findViewById(R.id.txt_rest_about_sms);
        TextView workTime = (TextView) view.findViewById(R.id.txt_rest_about_working_time);
        TextView website = (TextView) view.findViewById(R.id.txt_rest_about_website);
        Button btnShare = (Button) view.findViewById(R.id.btn_share);
        ImageView restPhotoContainer = (ImageView) view.findViewById(R.id.img_rest_about);

        name.setText(selectedRestaurant.getName());
        descr.setText(selectedRestaurant.getDescription());
        call.setText(selectedRestaurant.getPhone());
        sms.setText(selectedRestaurant.getPhone());
        workTime.setText(selectedRestaurant.getWorkingTime());
        website.setText(selectedRestaurant.getSite().toString());
        website.setMovementMethod(LinkMovementMethod.getInstance());
        restPhotoContainer.setImageBitmap(selectedRestaurant.getSmallPhoto());

        //setting click listeners
        call.setOnClickListener(this);
        sms.setOnClickListener(this);
        website.setOnClickListener(this);
        btnShare.setOnClickListener(this);

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_about, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // handling clicks
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_rest_about_call:
                TextView callNumHolder = (TextView) v;
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + callNumHolder.getText().toString()));
                startActivity(callIntent);
                break;
            case R.id.txt_rest_about_sms:
                TextView smsNumHolder = (TextView) v;
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("sms:" + smsNumHolder.getText().toString()));
                startActivity(smsIntent);
                break;
            case R.id.txt_rest_about_website:
                TextView uriHolder = (TextView) v;
                Uri uri = Uri.parse(uriHolder.getText().toString()); // missing 'http://' will cause crashed
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(siteIntent);
                break;
            case R.id.btn_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my favourite restaurant ever - www.bajka.com");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
