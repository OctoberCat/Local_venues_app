package com.forsquare_android_vternovoi.activities;


import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.adapters.TabsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

//todo 10.12 get reliable models
// TODO: 09.12.15 manage response with Retrofit
public class MainActivity extends AppCompatActivity implements TabListener {

    @Bind(R.id.pager)
    ViewPager viewPager;//todo ask about supp library and PageAdapter

    private ActionBar actionBar;

    private TabsAdapter tabsAdapter;

    // TODO: 11.12.15 Please use material TabLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {//todo change on non deprecated stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //init stuff

        setSupportActionBar(); // TODO: 11.12.15 TOOLBAR HERE
        actionBar = getActionBar();
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        //
        viewPager.setAdapter(tabsAdapter);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab = actionBar.newTab();
        tab.setText("Places"); // TODO: 11.12.15 please move to String resources

        tab.setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab();
        tab.setText("Map");
        actionBar.addTab(tab);



        //check scroll and change tab
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
