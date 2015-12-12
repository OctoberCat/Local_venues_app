package com.forsquare_android_vternovoi.activities;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.adapters.TabsAdapter;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener /*implements android.support.v7.app.ActionBar.TabListener*/ {

    @Bind(R.id.pager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @BindString(R.string.placesTab)
    String placesTab;
    @BindString(R.string.mapTab)
    String mapTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //init stuff
        tabs.addTab(tabs.newTab().setText(placesTab));
        tabs.addTab(tabs.newTab().setText(mapTab));

        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(this);
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

}
