package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Layout;
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

    private String overview;
    private TextView tvOverview;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = (Toolbar) findViewById(R.id.tbDetailsToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        movieId = getIntent().getIntExtra(AppString.MOVIE_ID, -1);
        tvId = getIntent().getIntExtra(AppString.TV_ID, -1);

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frDetailsContent);
        if (detailsFragment != null) {
            detailsFragment.setMovieId(movieId);
            detailsFragment.setTvId(tvId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itSearch:
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Video_Click(View view) {
        Intent intent = new Intent(getContext(), VideoActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }

    public void Info_Click(View view) {
        Layout l = tvOverview.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Detailed overview")
                            .setMessage(overview)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
        }
    }

    public Context getContext() {
        return this;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setTvOverview(TextView tvOverview) {
        this.tvOverview = tvOverview;
    }
}
