package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.fragments.DetailsFragment;
import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.helpers.AppString;

public class DetailsActivity extends AppCompatActivity {
    private int movieId;
    private int tvId;

    DetailsFragment detailsFragment;
    private ShareActionProvider mShareActionProvider;

    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbDetailsToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        movieId = getIntent().getIntExtra(AppString.MOVIE_ID, -1);
        tvId = getIntent().getIntExtra(AppString.TV_ID, -1);

        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frDetailsContent);
        if (detailsFragment != null) {
            detailsFragment.setMovieId(movieId);
            detailsFragment.setTvId(tvId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);

        StringBuilder builder = new StringBuilder();
        builder.append("https://www.themoviedb.org/");
        builder.append(movieId > 0 ? "movie/" : "tv/");
        builder.append(movieId > 0 ? movieId : tvId);

        sendIntent.putExtra(Intent.EXTRA_TEXT, builder.toString());
        sendIntent.setType("text/plain");

        mShareActionProvider.setShareIntent(sendIntent);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void videoClick(View view) {
        detailsFragment.videoClick();
    }

    public void detailableInfoClick(View view) {
        detailsFragment.detailableInfoClick();
    }

    public void favoriteClick(View view) {
        detailsFragment.favoriteClick();
    }

    public void rateMovie(View view) {
        detailsFragment.rateMovie(view);
    }
}
