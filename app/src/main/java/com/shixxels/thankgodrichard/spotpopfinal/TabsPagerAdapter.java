package com.shixxels.thankgodrichard.spotpopfinal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shixxels.thankgodrichard.spotpopfinal.segment.Feeds;
import com.shixxels.thankgodrichard.spotpopfinal.segment.Maplist;
import com.shixxels.thankgodrichard.spotpopfinal.segment.SpotAdd;

/**
 * Created by thankgodrichard on 10/20/16.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new Feeds();
            case 1:
                // Games fragment activity
                return new Maplist();
            case 2:
                // Movies fragment activity
                return new SpotAdd();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}