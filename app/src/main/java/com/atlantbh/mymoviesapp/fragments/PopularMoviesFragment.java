package com.atlantbh.mymoviesapp.fragments;

public class PopularMoviesFragment extends MoviesFragment {
    @Override
    public int getCategory() {
        return CATEGORY_POPULAR;
    }

    public PopularMoviesFragment() {}
}
