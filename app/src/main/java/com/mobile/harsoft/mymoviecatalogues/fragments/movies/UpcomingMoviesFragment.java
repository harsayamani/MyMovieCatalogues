package com.mobile.harsoft.mymoviecatalogues.fragments.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.adapter.MovieRecyclerAdapter;
import com.mobile.harsoft.mymoviecatalogues.api.APIClient;
import com.mobile.harsoft.mymoviecatalogues.api.BuildConfig;
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
public class UpcomingMoviesFragment extends Fragment {

    private MovieRecyclerAdapter adapter;
    private ResultMovie resultMovie;
    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private Context context;
    private SwipeRefreshLayout refreshLayout;

    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        context = getContext();
        recyclerView = view.findViewById(R.id.recycler1);
        refreshLayout = view.findViewById(R.id.refresh);
        layoutManager(recyclerView);

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
                        Call<ResultMovie> call = APIClient.getInstance()
                                .baseAPI()
                                .getUpcomingMovie(BuildConfig.API_KEY, BuildConfig.LANGUAGE, 1);

                        call.enqueue(new Callback<ResultMovie>() {
                            @Override
                            public void onResponse(@NonNull Call<ResultMovie> call, @NonNull Response<ResultMovie> response) {
                                resultMovie = response.body();
                                assert resultMovie != null;
                                movies = new ArrayList<>(Arrays.asList(resultMovie.getMovies()));
                                adapter = new MovieRecyclerAdapter(movies, context);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResultMovie> call, @NonNull Throwable t) {
                                Toast.makeText(context, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
                                Log.e("Error : ", t.toString());
                            }
                        });
                    }
                }, 2000);
            }
        });
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(R.string.movie_catalogues);
        progressDialog.setIcon(R.drawable.ic_movie_white_24dp);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        Call<ResultMovie> call = APIClient.getInstance()
                .baseAPI()
                .getUpcomingMovie(BuildConfig.API_KEY, BuildConfig.LANGUAGE, 1);

        call.enqueue(new Callback<ResultMovie>() {
            @Override
            public void onResponse(@NonNull Call<ResultMovie> call, @NonNull Response<ResultMovie> response) {
                resultMovie = response.body();
                assert resultMovie != null;
                movies = new ArrayList<>(Arrays.asList(resultMovie.getMovies()));
                adapter = new MovieRecyclerAdapter(movies, context);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<ResultMovie> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show();
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
}
