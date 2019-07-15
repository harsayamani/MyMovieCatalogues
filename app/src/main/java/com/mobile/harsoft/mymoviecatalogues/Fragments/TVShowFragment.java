package com.mobile.harsoft.mymoviecatalogues.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.harsoft.mymoviecatalogues.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {


    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        return view;
    }

}
