package com.os.stefanos.foodcube.activities.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.os.stefanos.foodcube.R;
import com.os.stefanos.foodcube.activities.RestaurantActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.RestaurantDAO;
import model.Restaurant;

/**
 * TODO: Created by Stefanos on 9/8/2016.
 */
public class RestaurantMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private List<Restaurant> mRestaurants;
    private Marker mMarker;
    private GoogleApiClient mGoogleApiClient;
    private Map<Marker, Restaurant> mRestaurantMap;

    private static int REQUEST_SETTINGS = 1;
    private static final String PREF_NAME = "user_name";
    private static final String PREF_SURNAME = "user_surname";
    private static final String PREF_ADDRESS = "user_address";
    private static final String PREF_PHONE = "user_phone";
    private static final String PREF_VISIBILITY = "visibility_splash_screen";

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(getContext(), "location changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private LocationManager mLocationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRestaurantMap = new HashMap<>();


        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10000, mLocationListener);
//        if(mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addConnectionCallbacks(new GoogleApiClient.addConnectionCallbacks(new GoogleApiClient()){
//
//                    })
//        }
        /*LatLng latLng;
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

            }
        }).addApi(LocationServices.API)
                .build();

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(lastLocation != null) {
            latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("You are here")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);//ako je dostupna ranije,dobicemo je
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_restoraunt_map, container, false);

        mMapView = (MapView) view.findViewById(R.id.restaurants_map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();


        //checking if filtering
        int time = 0;
        if (getActivity().getIntent().getExtras() != null){
            time = getActivity().getIntent().getExtras().getInt("filterTime");
        }
        if (time == 0) {
            mRestaurants = RestaurantDAO.getRestaurants();
        }else{
            mRestaurants = RestaurantDAO.getFilteredByWorkingTime(time);
        }

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {e.printStackTrace();}

/*
        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                mMarker = mGoogleMap.addMarker(new MarkerOptions().position(loc));
                if(mGoogleMap != null){
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                }
            }
        };

        mGoogleMap.setOnMyLocationButtonClickListener(myLocationChangeListener);*/

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;

                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.setOnMarkerClickListener(RestaurantMapFragment.this);
                for(Restaurant r : mRestaurants) {
                    LatLng location = new LatLng(r.getLatitude(), r.getLongitude());
                    mMarker = googleMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(r.getName())
                            .snippet(r.getDescription())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

                    );
                    mRestaurantMap.put(mMarker, r);
                }

                /*Location location = mGoogleMap.getMyLocation();
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

            }
        });
        return view;
    }







    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(mMarker)) {
            Restaurant restaurant = mRestaurantMap.get(mMarker);
            Intent intent = new Intent(getActivity(), RestaurantActivity.class);
            intent.putExtra("restaurantID", restaurant.getId());
            startActivity(intent);
            return true;
        }
        return false;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}