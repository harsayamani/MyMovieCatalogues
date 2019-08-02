package com.mobile.harsoft.mymoviecatalogues;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mobile.harsoft.mymoviecatalogues.Fragments.MoviesFragment;
import com.mobile.harsoft.mymoviecatalogues.Fragments.TVShowFragment;

public class MainActivity extends AppCompatActivity {

    private MoviesFragment moviesFragment;
    private TVShowFragment tvShowFragment;
    private int navTab = 1;
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.movie:
                    setTitle(R.string.movies);
                    fragment = new MoviesFragment();
                    navTab = 1;
                    break;
                case R.id.tvshow:
                    setTitle(R.string.tv_show);
                    fragment = new TVShowFragment();
                    navTab = 2;
                    break;
            }
            loadFragment();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareView();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        tabLayout();
        if (savedInstanceState != null) {
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
            if (fragment instanceof MoviesFragment) {
                moviesFragment = (MoviesFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                navTab = 1;
            } else if (fragment instanceof TVShowFragment) {
                tvShowFragment = (TVShowFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                navTab = 2;
            }
        }
        loadFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.languages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tranlate) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        switch (navTab) {
            case 1:
                getSupportFragmentManager().putFragment(outState, "fragment", moviesFragment);
                break;
            case 2:
                getSupportFragmentManager().putFragment(outState, "fragment", tvShowFragment);
                break;
        }
    }

    private void prepareView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        moviesFragment = new MoviesFragment();
        tvShowFragment = new TVShowFragment();
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);

    }

    private void loadFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (navTab) {
            case 1:
                fragmentTransaction.replace(R.id.fl_container, moviesFragment, MoviesFragment.class.getSimpleName());
                break;
            case 2:
                fragmentTransaction.replace(R.id.fl_container, tvShowFragment, TVShowFragment.class.getSimpleName());
        }
        fragmentTransaction.commit();
    }

//    private void tabLayout() {
//        final ViewPager viewPager = findViewById(R.id.pager);
//        final TabLayout tabLayout = findViewById(R.id.tab_layout);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager, tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }


}
