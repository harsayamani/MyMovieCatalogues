package com.mobile.harsoft.mymoviecatalogues.fragments.tvshows;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.adapter.TvShowRecyclerAdapter;
import com.mobile.harsoft.mymoviecatalogues.model.TvShow;
import com.mobile.harsoft.mymoviecatalogues.sqlitehelper.DbFavTvShows;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteTVShowFragment extends Fragment {

    private TvShowRecyclerAdapter adapter;
    private ArrayList<TvShow> tvShows;
    private RecyclerView recyclerView;
    private Context context;
    private SwipeRefreshLayout refreshLayout;
    private DbFavTvShows dbFavTvShows;
    private TextView textNotFound;


    public FavouriteTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context = getContext();
        recyclerView = view.findViewById(R.id.recycler3);
        refreshLayout = view.findViewById(R.id.refresh);
        dbFavTvShows = new DbFavTvShows(getActivity());
        textNotFound = view.findViewById(R.id.text);

        if (savedInstanceState != null) {
            tvShows = savedInstanceState.getParcelableArrayList("tvShow");
            adapter = new TvShowRecyclerAdapter(tvShows, context);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            getData();
        }

        refresh();
    }

    private void refresh() {
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        getData();
                    }
                }, 2000);
            }
        });
    }

    private void getData() {
        tvShows = new ArrayList<>();
        SQLiteDatabase database = dbFavTvShows.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery("select*from tb_tvShow", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            textNotFound.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            TvShow tvShow = new TvShow();

            String[] country = {cursor.getString(7)};

            tvShow.setId(cursor.getInt(0));
            tvShow.setName(cursor.getString(1));
            tvShow.setPoster_path(cursor.getString(2));
            tvShow.setFirst_air_date(cursor.getString(3));
            tvShow.setOverview(cursor.getString(4));
            tvShow.setPopularity(cursor.getDouble(5));
            tvShow.setVote_average(cursor.getDouble(6));
            tvShow.setOrigin_country(country);
            tvShows.add(tvShow);
        }

        adapter = new TvShowRecyclerAdapter(tvShows, context);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        layoutManager(recyclerView);
    }

    private void layoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(context);
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
                for (TvShow data : tvShows) {
                    String title = data.getName().toLowerCase();
                    if (title.contains(s)) {
                        tvShowArrayList.add(data);
                    }
                }
                adapter.setFilter(tvShowArrayList);
                return true;
            }
        });
        item.setActionView(searchView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("tvShow", tvShows);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}
