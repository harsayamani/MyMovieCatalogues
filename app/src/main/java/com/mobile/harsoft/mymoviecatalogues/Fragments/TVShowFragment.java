package com.mobile.harsoft.mymoviecatalogues.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.mobile.harsoft.mymoviecatalogues.Adapter.TvShowRecyclerAdapter;
import com.mobile.harsoft.mymoviecatalogues.DataClass.TvShow;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.Response.ResultTV;
import com.mobile.harsoft.mymoviecatalogues.RestAPI.APIClient;
import com.mobile.harsoft.mymoviecatalogues.RestAPI.BuildConfig;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {

    private TvShowRecyclerAdapter adapter;
    private ArrayList<TvShow> tvShows;
    private RecyclerView recyclerView;
    private ResultTV resultTV;
    private Context context;

    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        context = getContext();
        recyclerView = view.findViewById(R.id.recycler2);
        layoutManager(recyclerView);

        if (savedInstanceState != null) {
            tvShows = savedInstanceState.getParcelableArrayList("tvShows");
            adapter = new TvShowRecyclerAdapter(tvShows, context);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            getData();
        }
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(R.string.tv_show_catalogues);
        progressDialog.setIcon(R.drawable.ic_live_tv_white_24dp);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<ResultTV> call = APIClient.getInstance()
                .baseAPI()
                .getTvAiringToday(BuildConfig.API_KEY, BuildConfig.LANGUAGE, 1);

        call.enqueue(new Callback<ResultTV>() {
            @Override
            public void onResponse(@NonNull Call<ResultTV> call, @NonNull Response<ResultTV> response) {
                resultTV = response.body();
                assert resultTV != null;
                tvShows = new ArrayList<>(Arrays.asList(resultTV.getTvShows()));
                adapter = new TvShowRecyclerAdapter(tvShows, getContext());
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<ResultTV> call, @NonNull Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("Error : ", t.toString());
                progressDialog.dismiss();
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
        outState.putParcelableArrayList("tvShows", tvShows);
        super.onSaveInstanceState(outState);
    }
}
