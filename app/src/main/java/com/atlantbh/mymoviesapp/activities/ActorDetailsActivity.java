package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.adapters.MovieCreditsAdapter;
import com.atlantbh.mymoviesapp.adapters.TvCreditsAdapter;
import com.atlantbh.mymoviesapp.api.ActorAPI;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.credits.Credits;
import com.atlantbh.mymoviesapp.model.credits.MovieCredits;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ActorDetailsActivity extends AppCompatActivity {

    private int actorId;

    @Bind(R.id.tbMainToolbar)
    Toolbar mainToolbar;
    @Bind(R.id.ivActorBackdrop)
    ImageView actorBackdrop;
    @Bind(R.id.ivActorPoster)
    ImageView actorPoster;
    @Bind(R.id.tvTitle)
    TextView actorName;
    @Bind(R.id.tvActorSubtitle)
    TextView actorSubtitile;
    @Bind(R.id.tvActorBiography)
    TextView actorBiography;
    @Bind(R.id.rvActorMovies)
    RecyclerView actorMovies;
    @Bind(R.id.rvActorTvSeries)
    RecyclerView actorTvSeries;

    private Context getContext() { return ActorDetailsActivity.this; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_details);
        ButterKnife.bind(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        actorBackdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (720.0 / 1280.0 * displaymetrics.widthPixels)));

        setSupportActionBar(mainToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        Intent intent = getIntent();
        actorId = intent.getIntExtra("actorId", -1);
        if (actorId == -1) {
            Toast.makeText(getApplicationContext(), "Actor Id nije poslan, dakle bundle nije poslan", Toast.LENGTH_SHORT).show();
        } else {
            Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

            ActorAPI actorAPI = retrofit.create(ActorAPI.class);

            Call<Actor> call = actorAPI.loadActorById(actorId);
            call.enqueue(new Callback<Actor>() {
                @Override
                public void onResponse(Response<Actor> response, Retrofit retrofit) {
                    Actor actor = response.body();

                    if (actor.getImageList().getImages().size() > 0) {
                        Picasso.with(getContext())
                            .load("https://image.tmdb.org/t/p/w1280" + actor.getImageList().getImages().get(0).getMovie().getBackdropPath())
                            .placeholder(R.drawable.actor_placeholder_curved)
                            .into(actorBackdrop);
                    }
                    else {
                        Picasso.with(getContext())
                            .load(R.drawable.actor_placeholder_curved)
                            .into(actorBackdrop);
                    }

                    Picasso.with(getContext())
                            .load("https://image.tmdb.org/t/p/w500" + actor.getProfilePath())
                            .placeholder(R.drawable.actor_placeholder_curved)
                            .into(actorPoster);

                    actorName.setText(actor.getName());
                    actorSubtitile.setText(actor.getFrequentJobs());
                    if (actor.getBiography() != null) {
                        actorBiography.setText(actor.getBiography().replace("\n", " "));
                    }

                    RecyclerView movieCredits = (RecyclerView) findViewById(R.id.rvActorMovies);
                    movieCredits.setHasFixedSize(true);
                    RecyclerView.LayoutManager movieCreditsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    movieCredits.setLayoutManager(movieCreditsLayoutManager);
                    RecyclerView.Adapter movieCreditsAdapter = new MovieCreditsAdapter(getContext(), actor.getMovieCredits(), new MovieCreditsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Credits credits) {
                            Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                            intent.putExtra("movieId", credits.getId());
                            startActivity(intent);
                        }
                    });
                    movieCredits.setAdapter(movieCreditsAdapter);

                    RecyclerView tvCredits = (RecyclerView) findViewById(R.id.rvActorTvSeries);
                    tvCredits.setHasFixedSize(true);
                    RecyclerView.LayoutManager tvCreditsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    tvCredits.setLayoutManager(tvCreditsLayoutManager);
                    RecyclerView.Adapter tvCreditsAdapter = new TvCreditsAdapter(getContext(), actor.getTvCredits(), new TvCreditsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Credits credits) {
                            Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                            intent.putExtra("movieId", credits.getId());
                            startActivity(intent);
                        }
                    });
                    tvCredits.setAdapter(tvCreditsAdapter);
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

            SetFonts();
        }
    }

    private void SetFonts() {
        actorName.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        actorSubtitile.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        actorBiography.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
    }

    public void Info_Click(View view) {
        Layout l = actorBiography.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines-1) > 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Detailed biography")
                            .setMessage(actorBiography.getText())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
        }
    }
}
