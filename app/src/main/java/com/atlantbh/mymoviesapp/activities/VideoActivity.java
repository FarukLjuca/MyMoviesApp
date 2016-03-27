package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.api.UserAPI;
import com.atlantbh.mymoviesapp.api.VideoAPI;
import com.atlantbh.mymoviesapp.fragments.VideoFragment;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.RatingValue;
import com.atlantbh.mymoviesapp.model.User;
import com.atlantbh.mymoviesapp.model.VideoList;
import com.atlantbh.mymoviesapp.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class VideoActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    private int movieId;
    private String videoKey;
    private YouTubePlayer youTubePlayer;
    private ScrollView scrollView;
    private VideoFragment videoFragment;

    public YouTubePlayer getYouTubePlayer() {
        return youTubePlayer;
    }

    private static final String YoutubeDeveloperKey = "AIzaSyBbChM9SBrXSgLsQk43VOBu8A9hu1lgcPY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbVideoToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        Intent intent = getIntent();
        movieId = intent.getIntExtra(AppString.MOVIE_ID, -1);

        videoFragment = (VideoFragment) getSupportFragmentManager().findFragmentById(R.id.frVideoContent);
        if (videoFragment != null) {
            videoFragment.setMovieId(movieId);
        }

        scrollView = (ScrollView) findViewById(R.id.svVideoScrollView);

        if (movieId > 0) {
            Retrofit retrofit = AppHelper.getRetrofit();
            VideoAPI videoAPI = retrofit.create(VideoAPI.class);

            Call<VideoList> call = videoAPI.loadVideosByMovieId(movieId);
            call.enqueue(new Callback<VideoList>() {
                @Override
                public void onResponse(Response<VideoList> response, Retrofit retrofit) {
                    VideoList videoList = response.body();
                    if (videoList != null && videoList.getFirst() != null) {
                        videoKey = videoList.getFirst().getKey();
                        YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.frYoutube);
                        youTubePlayerFragment.initialize(YoutubeDeveloperKey, VideoActivity.this);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(VideoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (videoKey != "" && youTubePlayer != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                youTubePlayer.setFullscreen(true);
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                youTubePlayer.setFullscreen(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (videoKey != "") {
            youTubePlayer.cueVideo(videoKey);
        }
        this.youTubePlayer = youTubePlayer;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "There was a problem with initialization of youtube player.", Toast.LENGTH_LONG).show();
    }

    public void rateMovieVideo(View view) {
           videoFragment.rateMovieVideo(view);
    }

    public String getVideoKey() {
        return videoKey;
    }
}
