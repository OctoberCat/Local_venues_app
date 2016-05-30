package com.localvenues.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.localvenues.ui.fragment.ListFragment;
import com.localvenues.ui.fragment.MapFragment;

/**
 * Created by valentyn on 10.04.16.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListFragment();
            case 1:
                return new MapFragment();
        }
    return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        /*switch (position) {
            case 0:
                return "list";
            case 1:
                return "map";
        }*/
        return null;
    }


    @Override
        public int getCount () {
            return 2;
        }
    }
