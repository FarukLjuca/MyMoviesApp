package com.atlantbh.mymoviesapp.fragments;

import com.atlantbh.mymoviesapp.helpers.AppString;

public class PopularMoviesFragment extends MoviesFragment {
    @Override
    public int getCategory() {
        return AppString.CATEGORY_POPULAR;
    }
}
