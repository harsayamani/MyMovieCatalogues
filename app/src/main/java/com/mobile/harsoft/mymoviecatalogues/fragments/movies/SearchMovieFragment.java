package com.mobile.harsoft.mymoviecatalogues.fragments.movies;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.BuildConfig;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.adapter.MovieRecyclerAdapter;
import com.mobile.harsoft.mymoviecatalogues.api.APIClient;
import com.mobile.harsoft.mymoviecatalogues.model.Movie;
import com.mobile.harsoft.mymoviecatalogues.response.ResultMovie;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovieFragment extends Fragment {

    private MovieRecyclerAdapter adapter;
    private ResultMovie resultMovie;
    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private Context context;
    private TextView textNotFound;
    private android.support.v7.widget.SearchView searchView;

    public SearchMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        recyclerView = view.findViewById(R.id.recycler3);
        textNotFound = view.findViewById(R.id.text);
        searchView = view.findViewById(R.id.search);
        layoutManager(recyclerView);

        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("movies");
            adapter = new MovieRecyclerAdapter(movies, context);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            getData();
        }
        searchMovie();
    }

    private void searchMovie() {
        searchView.setQueryHint(getString(R.string.search_movie));
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                s = s.toLowerCase();

                if (s.length() == 0) {
                    getData();
                } else {
                    Call<ResultMovie> call = APIClient.getInstance()
                            .baseAPI()
                            .getMovieSearch(BuildConfig.API_KEY, BuildConfig.LANGUAGE, s);

                    call.enqueue(new Callback<ResultMovie>() {
                        @Override
                        public void onResponse(@NonNull Call<ResultMovie> call, @NonNull Response<ResultMovie> response) {
                            resultMovie = response.body();
                            assert resultMovie != null;
                            movies = new ArrayList<>(Arrays.asList(resultMovie.getMovies()));
                            adapter.setFilter(movies);
                            adapter.notifyDataSetChanged();
                            textNotFound.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResultMovie> call, @NonNull Throwable t) {
                            Log.e("Error : ", t.toString());
                            textNotFound.setVisibility(View.VISIBLE);
                        }
                    });
                }
                return true;
            }
        });
    }

    private void getData() {

        Call<ResultMovie> call = APIClient.getInstance()
                .baseAPI()
                .getMovieTopRated(BuildConfig.API_KEY, BuildConfig.LANGUAGE, 1);

        call.enqueue(new Callback<ResultMovie>() {
            @Override
            public void onResponse(@NonNull Call<ResultMovie> call, @NonNull Response<ResultMovie> response) {

                if (response.isSuccessful()) {
                    resultMovie = response.body();

                    if (resultMovie != null) {
                        movies = new ArrayList<>(Arrays.asList(resultMovie.getMovies()));
                        adapter = new MovieRecyclerAdapter(movies, context);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        textNotFound.setVisibility(View.INVISIBLE);
                    } else {
                        textNotFound.setVisibility(View.VISIBLE);
                    }

                } else {
                    textNotFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultMovie> call, @NonNull Throwable t) {
                textNotFound.setVisibility(View.VISIBLE);
                Log.e("Error : ", t.toString());
            }
        });
    }

    private void layoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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
