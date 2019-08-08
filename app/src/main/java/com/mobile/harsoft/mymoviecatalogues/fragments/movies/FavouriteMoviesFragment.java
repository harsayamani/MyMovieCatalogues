package com.mobile.harsoft.mymoviecatalogues.fragments.movies;


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
import com.mobile.harsoft.mymoviecatalogues.adapter.MovieRecyclerAdapter;
import com.mobile.harsoft.mymoviecatalogues.model.Movie;
import com.mobile.harsoft.mymoviecatalogues.sqlitehelper.DbFavMovies;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteMoviesFragment extends Fragment {

    private MovieRecyclerAdapter adapter;
    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private Context context;
    private SwipeRefreshLayout refreshLayout;
    private DbFavMovies dbFavMovies;
    private TextView textNotFound;

    public FavouriteMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        context = getContext();
        recyclerView = view.findViewById(R.id.recycler3);
        refreshLayout = view.findViewById(R.id.refresh);
        dbFavMovies = new DbFavMovies(getActivity());
        textNotFound = view.findViewById(R.id.text);

        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("movies");
            adapter = new MovieRecyclerAdapter(movies, context);
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
        movies = new ArrayList<>();
        SQLiteDatabase database = dbFavMovies.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery("select*from tb_movie", null);
        cursor.moveToFirst();

        if (cursor.getCount()>0){
            textNotFound.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Movie movie = new Movie();
            movie.setId(cursor.getInt(0));
            movie.setTitle(cursor.getString(1));
            movie.setPoster_path(cursor.getString(2));
            movie.setRelease_date(cursor.getString(3));
            movie.setOverview(cursor.getString(4));
            movie.setPopularity(cursor.getDouble(5));
            movie.setVote_average(cursor.getDouble(6));
            movie.setVote_count(cursor.getInt(7));
            movie.setGenre_ids(null);
            movies.add(movie);
        }

        adapter = new MovieRecyclerAdapter(movies, context);
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
        searchView.setQueryHint(getString(R.string.search_movie));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();
                ArrayList<Movie> movieArrayList = new ArrayList<>();
                for (Movie data : movies) {
                    String title = data.getTitle().toLowerCase();
                    if (title.contains(s)) {
                        movieArrayList.add(data);
                    }
                }
                adapter.setFilter(movieArrayList);
                return true;
            }
        });
        item.setActionView(searchView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("movies", movies);
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
