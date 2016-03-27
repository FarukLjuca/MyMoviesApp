package com.atlantbh.mymoviesapp.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.activities.ActorActivity;
import com.atlantbh.mymoviesapp.adapters.ActorAdapter;
import com.atlantbh.mymoviesapp.adapters.ReviewsAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.api.UserAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.helpers.NonScrollListView;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.RatingValue;
import com.atlantbh.mymoviesapp.model.User;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class VideoFragment extends Fragment {
    @Bind(R.id.rbVideoRating)
    RatingBar ratingBar;
    @Bind(R.id.tvVideoRating)
    TextView rating;
    @Bind(R.id.tvVideoRateMovie)
    TextView rateMovie;
    @Bind(R.id.tvVideoTitle)
    TextView title;
    @Bind(R.id.tvVideoBasicText)
    TextView basicText;
    @Bind(R.id.rvVideoActors)
    RecyclerView actors;
    @Bind(R.id.lvVideoReviews)
    NonScrollListView reviews;

    private int movieId;

    public VideoFragment() {}

    public void setMovieId(int movieId) {
        if (movieId != -1) {
            this.movieId = movieId;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movieId = getArguments().getInt(AppString.MOVIE_ID);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        if (movieId > 0) {
            Retrofit retrofit = AppHelper.getRetrofit();
            MovieAPI movieAPI = retrofit.create(MovieAPI.class);

            Call<Movie> call = movieAPI.loadMovieById(movieId);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Response<Movie> response, Retrofit retrofit) {
                    Movie movie = response.body();

                    ratingBar.setRating(movie.getVoteAverage() / 2);
                    rating.setText(String.valueOf(round(movie.getVoteAverage(), 1)));
                    title.setText(new StringBuilder().append(movie.getTitle()).append(" (Movie)"));
                    basicText.setText(movie.getOverview());

                    if (movie.getActorList().getActors().size() > 0) {
                        actors.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        actors.setLayoutManager(layoutManager);
                        RecyclerView.Adapter castAdapter = new ActorAdapter(getContext(), movie.getActorList(), new ActorAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Actor actor) {
                                actorClick(actor);
                            }
                        });
                        actors.setAdapter(castAdapter);
                    }
                    else {
                        actors.setVisibility(View.GONE);
                        ((TextView) getActivity().findViewById(R.id.tvVideoCast)).setVisibility(View.GONE);
                    }

                    if (movie.getReviewList().getReviewList().size() > 0) {
                        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getContext(), movie.getReviewList());
                        reviews.setAdapter(reviewsAdapter);
                    }
                    else {
                        reviews.setVisibility(View.GONE);
                        ((TextView) getActivity().findViewById(R.id.tvVideoReviews)).setVisibility(View.GONE);
                    }

                    title.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
                    rateMovie.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    public void actorClick(final Actor actor) {
        if (AppHelper.isOnline()) {
            RelativeLayout detailsContainer = (RelativeLayout) getActivity().findViewById(R.id.rlDetailsContainer);
            if (detailsContainer != null) {
                ActorFragment fragment = new ActorFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(AppString.ACTOR_ID, actor.getId());
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.rlDetailsContainer, fragment, AppString.actorFragmentTag)
                        .commit();
            } else {
                Intent intent = new Intent(getContext(), ActorActivity.class);
                intent.putExtra(AppString.ACTOR_ID, actor.getId());
                startActivity(intent);
            }
        }
        else {
            CoordinatorLayout coordinatorLayout;
            if (getActivity().findViewById(R.id.clVideoCoordinator) != null) {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clVideoCoordinator);
            }
            else {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clMovieCoordinator);
            }
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.check_your_internet_connection, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction(R.string.refresh, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actorClick(actor);
                }
            });
            snackbar.show();
        }
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public void rateMovieVideo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rateView = inflater.inflate(R.layout.rate_view, null);
        builder.setTitle("Rate a movie")
                .setView(rateView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Retrofit retrofit = AppHelper.getRetrofit();
                        UserAPI userAPI = retrofit.create(UserAPI.class);

                        RatingBar ratingBar = (RatingBar) rateView.findViewById(R.id.rbRate);
                        final RatingValue ratingValue = new RatingValue();
                        ratingValue.setValue(ratingBar.getRating());

                        Call<com.atlantbh.mymoviesapp.model.Response> call = null;
                        call = userAPI.setRatingMovie(movieId, User.getSession().getSessionId(), ratingValue);

                        call.enqueue(new Callback<com.atlantbh.mymoviesapp.model.Response>() {
                            @Override
                            public void onResponse(Response<com.atlantbh.mymoviesapp.model.Response> response, Retrofit retrofit) {
                                Toast.makeText(getContext(), "Successfully rated...", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
