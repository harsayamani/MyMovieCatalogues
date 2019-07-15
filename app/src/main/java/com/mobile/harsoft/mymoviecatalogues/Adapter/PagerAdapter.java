package com.mobile.harsoft.mymoviecatalogues.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.harsoft.mymoviecatalogues.Fragments.MoviesFragment;
import com.mobile.harsoft.mymoviecatalogues.Fragments.TVShowFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberTabs;

    public PagerAdapter(FragmentManager fm, int numberTabs) {
        super(fm);
        this.numberTabs = numberTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new MoviesFragment();
            case 1:
                return new TVShowFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberTabs;
    }
}
