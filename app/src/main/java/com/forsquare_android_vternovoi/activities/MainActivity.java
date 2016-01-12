package com.forsquare_android_vternovoi.activities;


import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.adapters.PagerAdapter;

import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, LocationListener{

    @Bind(R.id.pager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @BindString(R.string.placesTab)
    String placesTab;
    @BindString(R.string.mapTab)
    String mapTab;

    private LocationManager locationManager;
    public static final String LocKey = "LocationInfo";
    private String bestProvider;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //get Location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        bestProvider = locationManager.getBestProvider(criteria, true);
        //init tabs
        tabs.addTab(tabs.newTab().setText(placesTab).setIcon(R.drawable.ic_list_white_24dp));
        tabs.addTab(tabs.newTab().setText(mapTab).setIcon(R.drawable.ic_explore_white_24dp));

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        //check providers. If providers disabled, use last known location from SharedPreferences
        if (locationManager.isProviderEnabled(bestProvider)) {
            locationManager.requestLocationUpdates(bestProvider, 1000 * 60, 100, this);


        } else {

        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    //LocationListener
    @Override
    public void onLocationChanged(Location location) {
        location.getLongitude();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String ll = String.format("%1$.2f,%2$.2f",location.getLatitude(),location.getLongitude());
        Log.i("onLocationChanged",ll);
        sharedPreferences.edit().putString(LocKey, ll).apply();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
