package com.atlantbh.mymoviesapp.model;

import android.widget.Toast;

import com.atlantbh.mymoviesapp.adapters.MovieAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieList {
    @SerializedName("results")
    private List<Movie> _movies;
    private int page;
    private boolean isLoading;

    public List<Movie> getMovies() {
        return _movies;
    }

    public boolean getIsLoading() {
        return isLoading;
    }

    public int getPage() {
        return page;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setPage (int page) {
        this.page = page;
    }

    public MovieList() {
        setPage(1);
        setIsLoading(false);
    }

    public void LoadData(final MovieAdapter movieAdapter, String category) {
        page++;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<MovieList> call = movieAPI.loadMoviesByPage(category, page);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                MovieList movieList = response.body();
                movieAdapter.addItems(movieList);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
