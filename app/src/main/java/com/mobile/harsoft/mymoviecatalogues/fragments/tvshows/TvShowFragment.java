package com.mobile.harsoft.mymoviecatalogues.fragments.tvshows;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.adapter.TvShowPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(false);
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout();
        return view;
    }

    private void tabLayout() {
        FragmentManager fragmentManager = getChildFragmentManager();
        TvShowPagerAdapter tvShowPagerAdapter = new TvShowPagerAdapter(fragmentManager, tabLayout.getTabCount());
        viewPager.setAdapter(tvShowPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });
    }

}
