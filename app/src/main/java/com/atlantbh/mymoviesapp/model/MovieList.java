package com.atlantbh.mymoviesapp.model;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.MyApplication;
import com.atlantbh.mymoviesapp.adapters.MovieAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.services.CachingService;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
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

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setPage (int page) {
        this.page = page;
    }

    /*
    public MovieList() {
        setPage(1);
        setIsLoading(false);
        if (movies == null) {
            movies = new ArrayList<>();
        }
    }
    */

    public MovieList(List<Movie> movies) {
        setPage(1);
        setIsLoading(false);
        this.movies = movies;
    }

    public void LoadData(final Context context, final MovieAdapter movieAdapter, final int category) {
        page++;

        Retrofit retrofit = AppHelper.getRetrofit();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<MovieList> call = movieAPI.loadMoviesByPage(getCategoryString(category), page);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                MovieList movieList = response.body();
                movieAdapter.addItems(movieList);
                movieAdapter.notifyDataSetChanged();
                isLoading = false;
                CachingService.startNext(MyApplication.getContext(), category, page);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void LoadDataToPage(final MovieAdapter movieAdapter, int category, int page, final View view, final int position) {
        while (this.page <= page + 1) {
            Retrofit retrofit = AppHelper.getRetrofit();
            MovieAPI movieAPI = retrofit.create(MovieAPI.class);

            this.page++;
            Call<MovieList> call = movieAPI.loadMoviesByPage(getCategoryString(category), this.page);
            if (this.page == page + 1) {
                isLast = true;
            }
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

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public String getCategoryString(int category) {
        String result = "";

        switch (category) {
            case AppString.CATEGORY_POPULAR:
                result = "popular";
                break;
            case AppString.CATEGORY_NOW_PLAYING:
                result = "now_playing";
                break;
            case AppString.CATEGORY_TOP_RATED:
                result = "top_rated";
                break;
        }

        return result;
    }

}
