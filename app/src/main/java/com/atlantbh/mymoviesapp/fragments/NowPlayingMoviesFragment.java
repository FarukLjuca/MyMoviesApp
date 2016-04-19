package com.atlantbh.mymoviesapp.fragments;

import com.atlantbh.mymoviesapp.helpers.AppString;

public class NowPlayingMoviesFragment extends MoviesFragment {
    @Override
    public int getCategory() {
        return AppString.CATEGORY_NOW_PLAYING;
    }
}
