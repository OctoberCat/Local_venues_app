package com.localvenues.ui.activity;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.localvenues.R;
import com.localvenues.adapter.PagerAdapter;
import com.localvenues.service.WebService;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final String LOCATION_TAG = "location_test";
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    /* @Bind(R.id.toolbar)
     Toolbar toolbar;*/
    @Bind(R.id.pager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //setSupportActionBar(toolbar);
        //
        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
*/

        //
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        setupIcons();

        if (isOnline()) {
            //do my service stuff
            Log.i(LOG_TAG, "Starting venue  webservice");
            WebService.exploreVenues(this, "49.998099,36.233676", "500", "10", "0");
        } else {
            Snackbar.make(coordinatorLayout, "No Internet connection! Stored data will be used.", Snackbar.LENGTH_INDEFINITE).show();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
/*
        ///Location updates///
        Log.i(LOCATION_TAG, "onConnected");
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(180000);// 3 minutes
        mLocationRequest.setFastestInterval(5000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        //Last location//
*/


    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOCATION_TAG, location.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOCATION_TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(LOCATION_TAG, "Connection failed");
/*

        //I assume that it is good place for this
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            Log.i(LOCATION_TAG, "Last location" + lastLocation.toString());
        } else {
            Snackbar.make(coordinatorLayout, "Device location is required", Snackbar.LENGTH_LONG).show();
        }
*/
    }

    private void setupIcons() {
        try {
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_list);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_map);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}
