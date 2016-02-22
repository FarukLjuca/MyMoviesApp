package com.atlantbh.mymoviesapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atlantbh.mymoviesapp.activities.MovieListBaseFragment;
import com.atlantbh.mymoviesapp.activities.MovieListNowPlayingFragment;
import com.atlantbh.mymoviesapp.activities.MovieListPopularFragment;
import com.atlantbh.mymoviesapp.activities.MovieListTopRatedFragment;

public class MovieListPagerAdapter extends FragmentPagerAdapter {
    private String category;

    public MovieListPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public String getCategory() {
        return category;
    }

    @Override
    public Fragment getItem(int position) {
        MovieListBaseFragment fragment = null;

        if(position == 0) {
            MovieListPopularFragment popularFragment = new MovieListPopularFragment();
            fragment = popularFragment.newInstance();
        }
        else if (position == 1) {
            MovieListNowPlayingFragment featuredFragment = new MovieListNowPlayingFragment();
            fragment = featuredFragment.newInstance();
        }
        else if (position == 2) {
            MovieListTopRatedFragment recentFragment = new MovieListTopRatedFragment();
            fragment = recentFragment.newInstance();
        }

        category = fragment.getCategory();

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
