package com.atlantbh.mymoviesapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atlantbh.mymoviesapp.fragments.MoviesFragment;
import com.atlantbh.mymoviesapp.fragments.NowPlayingMoviesFragment;
import com.atlantbh.mymoviesapp.fragments.PopularMoviesFragment;
import com.atlantbh.mymoviesapp.fragments.TopRatedMoviesFragment;

public class MoviePagerAdapter extends FragmentPagerAdapter {
    public MoviePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        MoviesFragment fragment = null;

        if(position == 0) fragment = new PopularMoviesFragment();
        else if (position == 1) fragment = new NowPlayingMoviesFragment();
        else if (position == 2) fragment = new TopRatedMoviesFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "POPULAR";
            case 1:
                return "NOW PLAYING";
            case 2:
                return "TOP RATED";
        }
        return null;
    }
}
