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

import com.mobile.harsoft.mymoviecatalogues.Adapter.MovieRecyclerAdapter;
import com.mobile.harsoft.mymoviecatalogues.DataClass.Movie;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.SQLiteDatabaseHelper.DbMovies;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private MovieRecyclerAdapter adapter;
    private ArrayList<Movie> movies;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        setHasOptionsMenu(true);
        getData(view);

        return view;
    }

    private void getData(View view) {
        DbMovies dbMovies = new DbMovies(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recycler1);
        movies = new ArrayList<>();
        SQLiteDatabase readData = dbMovies.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = readData.rawQuery("select * from tb_movie", null);
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Movie movie = new Movie();
            movie.setTitle(cursor.getString(1));
            movie.setIlustration(cursor.getInt(2));
            movie.setYear(cursor.getString(3));
            movie.setCategory(cursor.getString(5));
            movie.setSynopsis(cursor.getString(4));
            movie.setRate(cursor.getDouble(6));
            movie.setMetascore(cursor.getDouble(7));
            movies.add(movie);
        }
        adapter = new MovieRecyclerAdapter(movies, getContext());
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
                for (Movie data : movies){
                    String title = data.getTitle().toLowerCase();
                    if (title.contains(s)){
                        movieArrayList.add(data);
                    }
                }
                adapter.setFilter(movieArrayList);
                return true;
            }
        });
        item.setActionView(searchView);
    }


}
