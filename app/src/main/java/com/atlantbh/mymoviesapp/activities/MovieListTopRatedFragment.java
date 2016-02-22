package com.atlantbh.mymoviesapp.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atlantbh.mymoviesapp.R;

public class MovieListTopRatedFragment extends MovieListBaseFragment {
    @Override
    public String getCategory() {
        return "top_rated";
    }

    public MovieListTopRatedFragment() {}

    public MovieListBaseFragment newInstance() {
        MovieListBaseFragment fragment = new MovieListTopRatedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
