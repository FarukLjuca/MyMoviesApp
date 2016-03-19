package com.atlantbh.mymoviesapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.Movie;

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
    ListView reviews;

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
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }
}
