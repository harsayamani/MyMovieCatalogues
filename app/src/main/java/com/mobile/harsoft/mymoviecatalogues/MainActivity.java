package com.mobile.harsoft.mymoviecatalogues;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mobile.harsoft.mymoviecatalogues.fragments.movies.MoviesFragment;
import com.mobile.harsoft.mymoviecatalogues.fragments.tvshows.TVShowFragment;

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
                    fragment = new MoviesFragment();
                    navTab = 1;
                    break;
                case R.id.tvshow:
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

        if (savedInstanceState != null) {
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
            if (fragment instanceof MoviesFragment) {
                moviesFragment = (MoviesFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                navTab = 1;
            } else if (fragment instanceof TVShowFragment) {
                tvShowFragment = (TVShowFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
                navTab = 2;
            }
        } else {
            loadFragment();
        }

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
                fragmentTransaction.addToBackStack(null).replace(R.id.fl_container, moviesFragment, MoviesFragment.class.getSimpleName());
                break;
            case 2:
                fragmentTransaction.addToBackStack(null).replace(R.id.fl_container, tvShowFragment, TVShowFragment.class.getSimpleName());
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(getString(R.string.exit_message));
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.setCancelable(true);
            }
        });
        alertDialog.show();
    }
}
