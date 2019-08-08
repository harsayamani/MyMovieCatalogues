package com.mobile.harsoft.mymoviecatalogues.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.harsoft.mymoviecatalogues.fragments.movies.FavouriteMoviesFragment;
import com.mobile.harsoft.mymoviecatalogues.fragments.movies.UpcomingMoviesFragment;

public class MoviePagerAdapter extends FragmentStatePagerAdapter {

    private int numberTabs;

    public MoviePagerAdapter(FragmentManager fm, int numberTabs) {
        super(fm);
        this.numberTabs = numberTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new UpcomingMoviesFragment();
            case 1:
                return new FavouriteMoviesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberTabs;
    }
}
