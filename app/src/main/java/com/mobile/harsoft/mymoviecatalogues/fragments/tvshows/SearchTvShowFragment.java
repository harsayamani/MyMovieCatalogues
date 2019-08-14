package com.mobile.harsoft.mymoviecatalogues.fragments.tvshows;


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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.harsoft.mymoviecatalogues.BuildConfig;
import com.mobile.harsoft.mymoviecatalogues.R;
import com.mobile.harsoft.mymoviecatalogues.adapter.TvShowRecyclerAdapter;
import com.mobile.harsoft.mymoviecatalogues.api.APIClient;
import com.mobile.harsoft.mymoviecatalogues.model.TvShow;
import com.mobile.harsoft.mymoviecatalogues.response.ResultTv;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTvShowFragment extends Fragment {

    private TvShowRecyclerAdapter adapter;
    private ArrayList<TvShow> tvShows;
    private RecyclerView recyclerView;
    private ResultTv resultTV;
    private Context context;
    private SearchView searchView;
    private TextView textNotFound;


    public SearchTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tv_show, container, false);
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
            tvShows = savedInstanceState.getParcelableArrayList("tvShows");
            adapter = new TvShowRecyclerAdapter(tvShows, context);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            getData();
        }

        searchTV();
    }

    private void searchTV() {
        searchView.setQueryHint(getString(R.string.search_tv_show));
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

                    Call<ResultTv> call = APIClient.getInstance()
                            .baseAPI()
                            .getTvSearch(BuildConfig.API_KEY, BuildConfig.LANGUAGE, s);

                    call.enqueue(new Callback<ResultTv>() {
                        @Override
                        public void onResponse(@NonNull Call<ResultTv> call, @NonNull Response<ResultTv> response) {
                            resultTV = response.body();
                            assert resultTV != null;
                            tvShows = new ArrayList<>(Arrays.asList(resultTV.getTvShows()));
                            adapter.setFilter(tvShows);
                            adapter.notifyDataSetChanged();
                            textNotFound.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResultTv> call, @NonNull Throwable t) {
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

        Call<ResultTv> call = APIClient.getInstance()
                .baseAPI()
                .getTvTopRated(BuildConfig.API_KEY, BuildConfig.LANGUAGE, 1);

        call.enqueue(new Callback<ResultTv>() {
            @Override
            public void onResponse(@NonNull Call<ResultTv> call, @NonNull Response<ResultTv> response) {
                resultTV = response.body();
                assert resultTV != null;
                tvShows = new ArrayList<>(Arrays.asList(resultTV.getTvShows()));
                adapter = new TvShowRecyclerAdapter(tvShows, getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                textNotFound.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(@NonNull Call<ResultTv> call, @NonNull Throwable t) {
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
        outState.putParcelableArrayList("tvShows", tvShows);
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
