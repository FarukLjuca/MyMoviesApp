package com.atlantbh.mymoviesapp.fragments;

import com.atlantbh.mymoviesapp.helpers.AppString;

public class TopRatedMoviesFragment extends MoviesFragment {
    @Override
    public int getCategory() {
        return AppString.CATEGORY_TOP_RATED;
    }
}
