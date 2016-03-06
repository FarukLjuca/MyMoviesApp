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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.api.ActorAPI;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.adapters.ActorAdapter;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.ActorList;
import com.atlantbh.mymoviesapp.model.Genre;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.gpu.PixelationFilterTransformation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieDetailsActivity extends AppCompatActivity {
    private long movieId;

    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    @Bind(R.id.ivDetailsBackdrop)
    ImageView backdrop;
    @Bind(R.id.tvDetailsTitle)
    TextView title;
    @Bind(R.id.tvDetailsReleaseYear)
    TextView releaseYear;
    @Bind(R.id.tvDetailsRuntimeAndGenres)
    TextView runtimeAndGenres;
    @Bind(R.id.tvDetailsOverview)
    TextView overview;
    @Bind(R.id.ivDetailsInfo)
    ImageView info;
    @Bind(R.id.rbDetailsRating)
    RatingBar detailsRating;
    @Bind(R.id.tvDetailsVoteAverage)
    TextView voteAverage;
    @Bind(R.id.tvDetailsVoteCount)
    TextView voteCount;

    private Context getContext() {
        return MovieDetailsActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        backdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (720.0 / 1280.0 * displaymetrics.widthPixels)));

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId", -1);
        if (movieId == -1) {
            Toast.makeText(getApplicationContext(), "Movie Id nije poslan, dakle bundle nije poslan", Toast.LENGTH_SHORT).show();
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MovieAPI movieAPI = retrofit.create(MovieAPI.class);

            Call<Movie> call = movieAPI.loadMovieById(movieId);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Response<Movie> response, Retrofit retrofit) {
                    Movie movie = response.body();

                    Picasso.with(MovieDetailsActivity.this)
                            .load("https://image.tmdb.org/t/p/w1280" + movie.getBackdropPath())
                            .placeholder(R.drawable.actor_placeholder_curved)
                            .into(backdrop);

                    List<Transformation> transformations = new ArrayList<>();
                    transformations.add(new BlurTransformation(MovieDetailsActivity.this, 25));
                    ImageView backdropBlur = (ImageView) findViewById(R.id.ivDetailsBackdropBlur);
                    Picasso.with(MovieDetailsActivity.this)
                            .load("https://image.tmdb.org/t/p/w1280" + movie.getBackdropPath())
                            .transform(transformations)
                            .into(backdropBlur);

                    title.setText(movie.getOriginalTitle());

                    // Parsing and setting release year
                    Date movieDate = null;
                    try {
                        movieDate = movie.getReleaseDate();
                    } catch (ParseException ex) {
                        Toast toast = Toast.makeText(MovieDetailsActivity.this, R.string.invalidParse, Toast.LENGTH_LONG);
                        toast.show();
                    }
                    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy");
                    String stringDate = "(" + simpleDate.format(movieDate) + ")";
                    releaseYear.setText(stringDate);

                    String runtimeString = "";
                    int runtime = movie.getRuntime();
                    runtimeString += runtime / 60;
                    runtimeString += "h ";
                    runtimeString += runtime - (runtime / 60) * 60;
                    runtimeString += "m";
                    String stringGenres = runtimeString + " |";
                    List<Genre> genreList = movie.getGenres();
                    for (int i = 0; i < genreList.size(); i++) {
                        stringGenres += " " + genreList.get(i).getName();
                    }
                    runtimeAndGenres.setText(stringGenres);

                    ImageView poster = (ImageView) findViewById(R.id.ivDetailsPoster);
                    Picasso.with(MovieDetailsActivity.this)
                            .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                            .placeholder(R.drawable.actor_placeholder_curved)
                            .into(poster);

                    overview.setText(movie.getOverview());
                    overview.post(new Runnable() {
                        @Override
                        public void run() {
                            if(overview.getLineCount() < 7) {
                                info.setColorFilter(R.color.lightgray);
                            }
                        }
                    });

                    detailsRating.setRating(movie.getVoteAverage() / 2);
                    voteAverage.setText(Float.toString(movie.getVoteAverage()));
                    voteCount.setText(Integer.toString(movie.getVoteCount()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(MovieDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


            Retrofit retrofitActors = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final ActorAPI actorAPI = retrofit.create(ActorAPI.class);

            Call<ActorList> callActor = actorAPI.loadActorsByMovieId(movieId);
            callActor.enqueue(new Callback<ActorList>() {
                @Override
                public void onResponse(Response<ActorList> response, Retrofit retrofit) {
                    ActorList actors = response.body();

                    RecyclerView cast = (RecyclerView) findViewById(R.id.rvDetailsCast);
                    cast.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MovieDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    cast.setLayoutManager(layoutManager);
                    RecyclerView.Adapter castAdapter = new ActorAdapter(getContext(), actors, new ActorAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Actor actor) {
                            Intent intent = new Intent(getContext(), ActorDetailsActivity.class);
                            intent.putExtra("actorId", actor.getId());
                            startActivity(intent);
                        }
                    });
                    cast.setAdapter(castAdapter);
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(MovieDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        SetFonts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast t;
        switch (item.getItemId()) {
            case R.id.itSearch:
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void Video_Click(View view) {
        Intent intent = new Intent(MovieDetailsActivity.this, MovieVideoActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }

    public void Info_Click(View view) {
        Layout l = overview.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines-1) > 0) {
                    new AlertDialog.Builder(MovieDetailsActivity.this)
                        .setTitle("Detailed overview")
                        .setMessage(overview.getText())
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                }
        }
    }

    private void SetFonts() {
        title.setTypeface(FontHelper.getFont(getContext(), FontHelper.AVENIR_REGULAR));
        releaseYear.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        runtimeAndGenres.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
    }
}
