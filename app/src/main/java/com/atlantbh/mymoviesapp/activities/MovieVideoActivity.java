package com.atlantbh.mymoviesapp.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.atlantbh.mymoviesapp.api.VideoAPI;
import com.atlantbh.mymoviesapp.model.VideoList;
import com.atlantbh.mymoviesapp.R;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieVideoActivity extends YouTubeFailureRecoveryActivity {
    private int movieId;
    private String videoKey;

    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyBbChM9SBrXSgLsQk43VOBu8A9hu1lgcPY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_video);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.tbDetailsToolbar);
        setActionBar(myToolbar);
        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", -1);

        if (movieId != -1) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            VideoAPI movieAPI = retrofit.create(VideoAPI.class);

            Call<VideoList> call = movieAPI.loadVideosByMovieId(movieId);
            call.enqueue(new Callback<VideoList>() {
                @Override
                public void onResponse(Response<VideoList> response, Retrofit retrofit) {
                    VideoList videoList = response.body();
                    videoKey = videoList.getVideos().get(0).getKey();
                    YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.frYoutube);
                    youTubePlayerFragment.initialize(YoutubeDeveloperKey, MovieVideoActivity.this);
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(MovieVideoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.frYoutube);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(videoKey);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.itSearch:
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
