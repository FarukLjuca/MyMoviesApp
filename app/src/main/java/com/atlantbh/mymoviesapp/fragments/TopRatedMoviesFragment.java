package com.atlantbh.mymoviesapp.fragments;

public class TopRatedMoviesFragment extends MoviesFragment {
    @Override
    public int getCategory() {
        return CATEGORY_TOP_RATED;
    }

    public TopRatedMoviesFragment() {}
}
