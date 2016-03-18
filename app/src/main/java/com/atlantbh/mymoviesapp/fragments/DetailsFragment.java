package com.atlantbh.mymoviesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.activities.ActorActivity;
import com.atlantbh.mymoviesapp.activities.DetailsActivity;
import com.atlantbh.mymoviesapp.activities.MovieListActivity;
import com.atlantbh.mymoviesapp.adapters.ActorAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.api.TvAPI;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.Detailable;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.Tv;
import com.atlantbh.mymoviesapp.model.realm.RealmMovie;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieBasic;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailsFragment extends Fragment {
    private int movieId;
    private int tvId;

    @Bind(R.id.ivDetailsBackdrop)
    ImageView backdrop;
    @Bind(R.id.tvDetailsTitle)
    TextView title;
    @Bind(R.id.tvDetailsYear)
    TextView year;
    @Bind(R.id.tvDetailsSubtitle)
    TextView subtitle;
    @Bind(R.id.tvDetailsBasicText)
    TextView basicText;
    @Bind(R.id.ivDetailsInfo)
    ImageView info;
    @Bind(R.id.rbDetailsRating)
    RatingBar rating;
    @Bind(R.id.tvDetailsVoteAverage)
    TextView voteAverage;
    @Bind(R.id.tvDetailsVoteCount)
    TextView voteCount;
    @Bind(R.id.ivDetailsBackdropBlur)
    ImageView backdropBlur;

    public DetailsFragment() {}

    public void setMovieId(int movieId) {
        if (movieId > 0) {
            this.movieId = movieId;
        }
    }

    public void setTvId(int tvId) {
        if (tvId > 0) {
            this.tvId = tvId;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movieId = getArguments().getInt(AppString.MOVIE_ID);
            tvId = getArguments().getInt(AppString.TV_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (getResources().getDisplayMetrics().widthPixels * 160 / getResources().getDisplayMetrics().densityDpi < 900) {
            backdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (720.0 / 1280.0 * displaymetrics.widthPixels)));
        }
        else {
            backdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ((720.0 / 1280.0 * displaymetrics.widthPixels) * 3/5)));
        }

        if (movieId > 0) {
            if (isOnline()) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd")
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://api.themoviedb.org")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                MovieAPI movieAPI = retrofit.create(MovieAPI.class);

                Call<Movie> call = movieAPI.loadMovieById(movieId);
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Response<Movie> response, Retrofit retrofit) {
                        Detailable detailable = response.body();
                        if (detailable != null) {
                            SetContent(detailable);

                            Realm realm = Realm.getInstance(getContext());
                            realm.beginTransaction();
                            RealmMovie realmMovie = new RealmMovie((Movie) detailable);
                            realm.copyToRealmOrUpdate(realmMovie);
                            realm.commitTransaction();
                            realm.close();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                Realm realm = Realm.getInstance(getContext());
                RealmResults<RealmMovie> realmResults = realm.where(RealmMovie.class).equalTo("id", movieId).findAll();
                if (realmResults.size() == 0) {
                    RealmResults<RealmMovieBasic> realmResultsBasic = realm.where(RealmMovieBasic.class).equalTo("id", movieId).findAll();
                    if (realmResultsBasic.size() == 0) {
                        //TODO: Ovjde neku refresh stranicu napraviti
                    }
                    else {
                        SetContent(new Movie(realmResultsBasic.get(0)));
                    }
                }
                else {
                    Movie movie = new Movie(realmResults.get(0));
                    SetContent(movie);
                }

                getActivity().findViewById(R.id.rlDetailsVideo).setVisibility(View.GONE);
                getActivity().findViewById(R.id.vwDetailsVideoLine).setVisibility(View.GONE);
                basicText.setLines(10);
            }
        } else if (tvId > 0) {
            getActivity().findViewById(R.id.rlDetailsVideo).setVisibility(View.GONE);
            getActivity().findViewById(R.id.vwDetailsVideoLine).setVisibility(View.GONE);
            basicText.setLines(10);

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            TvAPI tvAPI = retrofit.create(TvAPI.class);

            Call<Tv> call = tvAPI.loadTvById(tvId);
            call.enqueue(new Callback<Tv>() {
                @Override
                public void onResponse(Response<Tv> response, Retrofit retrofit) {
                    Detailable detailable = response.body();
                    if (detailable != null) {
                        SetContent(detailable);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        SetFonts();
    }

    private void SetContent(Detailable detailable) {
        Glide.with(getContext())
                .load("https://image.tmdb.org/t/p/w1280" + detailable.getBackdropPath())
                .into(backdrop);

        Glide.with(getContext())
                .load("https://image.tmdb.org/t/p/w1280" + detailable.getBackdropPath())
                .bitmapTransform(new BlurTransformation(getContext(), 25))
                .into(backdropBlur);

        title.setText(detailable.getTitle());
        year.setText(detailable.getYear());
        subtitle.setText(detailable.getSubtitle());

        ImageView poster = (ImageView) getActivity().findViewById(R.id.ivDetailsPoster);
        Glide.with(getContext())
                .load("https://image.tmdb.org/t/p/w500" + detailable.getPosterPath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .into(poster);

        basicText.setText(detailable.getBasicText());
        basicText.post(new Runnable() {
            @Override
            public void run() {
                Layout l = basicText.getLayout();
                if (l != null) {
                    int lines = l.getLineCount();
                    if (lines > 0)
                        if (l.getEllipsisCount(lines - 1) == 0) {
                            info.setColorFilter(R.color.lightgray);
                        }
                }
            }
        });

        if (getActivity() instanceof DetailsActivity) {
            ((DetailsActivity) getActivity()).setTvOverview(basicText);
            ((DetailsActivity) getActivity()).setOverview(detailable.getBasicText());
        }
        else if (getActivity() instanceof MovieListActivity) {
            ((MovieListActivity) getActivity()).setTvOverview(basicText);
            ((MovieListActivity) getActivity()).setOverview(detailable.getBasicText());
        }

        rating.setRating(detailable.getVoteAverage() / 2);
        voteAverage.setText(String.valueOf(round(detailable.getVoteAverage(), 1)));
        voteCount.setText(Integer.toString(detailable.getVoteCount()));

        RecyclerView cast = (RecyclerView) getActivity().findViewById(R.id.rvDetailsCast);
        cast.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cast.setLayoutManager(layoutManager);
        RecyclerView.Adapter castAdapter = new ActorAdapter(getContext(), detailable.getActorList(), new ActorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Actor actor) {
                RelativeLayout detailsContainer = (RelativeLayout) getActivity().findViewById(R.id.rlDetailsContainer);
                if (detailsContainer != null) {
                    ActorFragment fragment = new ActorFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppString.ACTOR_ID, actor.getId());
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.rlDetailsContainer, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(getContext(), ActorActivity.class);
                    intent.putExtra("actorId", actor.getId());
                    startActivity(intent);
                }
            }
        });
        cast.setAdapter(castAdapter);
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    private void SetFonts() {
        title.setTypeface(FontHelper.getFont(getContext(), FontHelper.AVENIR_REGULAR));
        year.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        subtitle.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
