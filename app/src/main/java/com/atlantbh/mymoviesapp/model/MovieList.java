package com.atlantbh.mymoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
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
    private List<Movie> movies;
    private int page;
    private boolean isLoading;
    private boolean isLast;

    public List<Movie> getMovies() {
        return movies;
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

    public void LoadDataToPage(final MovieAdapter movieAdapter, String category, int page, final View view, final int position) {
        while (this.page <= page + 1) {
            if (this.page == page + 1) isLast = true;
            this.page++;

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
                    if (isLast) {
                        if (view instanceof ListView) {
                            ListView listView = (ListView) view;
                            listView.smoothScrollToPosition(position);
                        }
                        else if (view instanceof GridView) {
                            GridView gridView = (GridView) view;
                            gridView.smoothScrollToPosition(position);
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }
}
