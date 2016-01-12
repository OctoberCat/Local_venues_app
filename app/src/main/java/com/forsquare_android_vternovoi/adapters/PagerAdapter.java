package com.forsquare_android_vternovoi.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.forsquare_android_vternovoi.fragments.MapFragment;
import com.forsquare_android_vternovoi.fragments.RevenueListFragment;

/**
 * Created by valentin on 09.12.15.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RevenueListFragment();
            case 1:
                return new MapFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}