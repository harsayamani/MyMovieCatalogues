package com.mobile.harsoft.mymoviecatalogues.Fragments;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.harsoft.mymoviecatalogues.Adapter.TvShowRecyclerAdapter;
import com.mobile.harsoft.mymoviecatalogues.DataClass.TvShow;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.SQLiteDatabaseHelper.DbTvShows;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {

    private TvShowRecyclerAdapter adapter;
    private ArrayList<TvShow> tvShows;

    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        setHasOptionsMenu(true);
        getData(view);
        return view;
    }

    private void getData(View view) {
        DbTvShows dbTvShows = new DbTvShows(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler2);
        tvShows = new ArrayList<>();
        SQLiteDatabase readData = dbTvShows.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = readData.rawQuery("select * from tb_tvShow", null);
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            TvShow tvShow = new TvShow();
            tvShow.setTitle(cursor.getString(1));
            tvShow.setIlustration(cursor.getInt(2));
            tvShow.setYear(cursor.getString(3));
            tvShow.setCategory(cursor.getString(5));
            tvShow.setSynopsis(cursor.getString(4));
            tvShow.setRate(cursor.getDouble(6));
            tvShow.setStars(cursor.getString(7));
            tvShows.add(tvShow);
        }
        adapter = new TvShowRecyclerAdapter(tvShows, getContext());
        recyclerView.setAdapter(adapter);

        layoutManager(recyclerView);
    }

    private void layoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(getContext());
        searchView.setQueryHint(getString(R.string.search_tv_show));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();
                ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
                for (TvShow data : tvShows){
                    String title = data.getTitle().toLowerCase();
                    if (title.contains(s)){
                        tvShowArrayList.add(data);
                    }
                }
                adapter.setFilter(tvShowArrayList);
                return true;
            }
        });
        item.setActionView(searchView);
    }
}
