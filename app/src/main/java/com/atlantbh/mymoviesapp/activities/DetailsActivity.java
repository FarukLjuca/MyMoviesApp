package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbRequest;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.fragments.DetailsFragment;
import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.MovieList;
import com.atlantbh.mymoviesapp.model.User;

public class DetailsActivity extends AppCompatActivity {
    private int movieId;
    private int tvId;

    private String overview;
    private TextView tvOverview;

    private boolean favorite = false;

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
        intent.putExtra(AppString.MOVIE_ID, movieId);
        startActivity(intent);
    }

    public void Info_Click(View view) {
        Layout l = tvOverview.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getString(R.string.detailedOverview))
                            .setMessage(overview)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
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

    public void favoriteClick(View view) {
        if (AppHelper.isOnline()) {
            favorite();
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.clLoginCoordinator), "Check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction("Refresh", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favorite();
                }
            });
            snackbar.show();
        }
    }

    private void favorite() {
        ImageView imageView = (ImageView) findViewById(R.id.ivDetailsFavorite);

        //Todo: Ako je u listi favorita, onda ga odfavoritisi, u suprotom ga favoritisi
        if (User.isLoggedIn()) {
            if (imageView != null) {
                //User.favorite(movieId, true, "movie", imageView);
                Toast.makeText(getContext(), "Todo dalje", Toast.LENGTH_SHORT).show();
            }
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.clDetailsCoordinator), "You need to be logged in", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction("Login", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            snackbar.show();
        }
    }
}
