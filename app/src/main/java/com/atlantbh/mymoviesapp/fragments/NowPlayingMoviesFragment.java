package com.atlantbh.mymoviesapp.fragments;

public class NowPlayingMoviesFragment extends MoviesFragment {
    @Override
    public int getCategory() {
        return CATEGORY_NOW_PLAYING;
    }

    public NowPlayingMoviesFragment() {}
}
