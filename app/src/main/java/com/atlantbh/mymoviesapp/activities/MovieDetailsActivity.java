package com.atlantbh.mymoviesapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.api.ActorAPI;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.adapters.ActorAdapter;
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
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieDetailsActivity extends AppCompatActivity {
    private long movieId;

    private Toolbar toolbar;
    @Bind(R.id.ivDetailsBackdrop)
    ImageView backdrop;
    private TextView title;
    private TextView releaseYear;
    private TextView runtimeAndGenres;
    private TextView overview;
    private RatingBar detailsRating;
    private TextView voteAverage;
    private TextView voteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        // Populating private variables
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        title = (TextView) findViewById(R.id.tvDetailsTitle);
        releaseYear = (TextView) findViewById(R.id.tvDetailsReleaseYear);
        runtimeAndGenres = (TextView) findViewById(R.id.tvDetailsRuntimeAndGenres);
        overview = (TextView) findViewById(R.id.tvDetailsOverview);
        detailsRating = (RatingBar) findViewById(R.id.rbDetailsRating);
        voteAverage = (TextView) findViewById(R.id.tvDetailsVoteAverage);
        voteCount = (TextView) findViewById(R.id.tvDetailsVoteCount);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        Intent intent = getIntent();
        movieId = intent.getLongExtra("movieId", -1);
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
                            .into(backdrop);

                    List<Transformation> transformations = new ArrayList<Transformation>();
                    transformations.add(new BlurTransformation(MovieDetailsActivity.this));
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
                            .into(poster);

                    overview.setText(movie.getOverview());

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
                    RecyclerView.Adapter castAdapter = new ActorAdapter(MovieDetailsActivity.this, actors);
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast t;
        switch (item.getItemId()) {
            case R.id.action_settings:
                t = Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT);
                t.show();
                return true;

            case R.id.itSearch:
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

    private void SetFonts() {
        Typeface avenirRegular = Typeface.createFromAsset(getAssets(), "fonts/AvenirNextLTPro-Regular.otf");
        Typeface robotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        Typeface robotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        title.setTypeface(avenirRegular);
        releaseYear.setTypeface(robotoMedium);
        runtimeAndGenres.setTypeface(robotoRegular);
    }
}
