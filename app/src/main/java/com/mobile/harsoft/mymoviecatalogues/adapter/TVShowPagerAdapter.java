package com.mobile.harsoft.mymoviecatalogues.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.harsoft.mymoviecatalogues.fragments.tvshows.FavouriteTVShowFragment;
import com.mobile.harsoft.mymoviecatalogues.fragments.tvshows.TVAiringTodayFragment;

public class TVShowPagerAdapter extends FragmentStatePagerAdapter {

    private int numberTabs;

    public TVShowPagerAdapter(FragmentManager fm, int numberTabs) {
        super(fm);
        this.numberTabs = numberTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new TVAiringTodayFragment();
            case 1:
                return new FavouriteTVShowFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberTabs;
    }
}
