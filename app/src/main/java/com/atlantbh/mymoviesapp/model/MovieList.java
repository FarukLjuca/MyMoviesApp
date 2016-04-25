package com.atlantbh.mymoviesapp.model;

import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.atlantbh.mymoviesapp.adapters.MovieAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieList {
    public static boolean isLoading;

    @SerializedName("results")
    private List<Movie> movies;

    private int page;
    private boolean isLast;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MovieList(List<Movie> movies) {
        setPage(1);
        isLoading = false;
        this.movies = movies;
    }

    public void LoadData(final MovieAdapter movieAdapter, final int category, final ProgressBar loading, final ListView list) {
        Retrofit retrofit = AppHelper.getRetrofit();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<MovieList> call = movieAPI.loadMoviesByPage(AppHelper.getCategoryString(category), page + 1);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                if (response.code() == 200) {
                    movieAdapter.addItems(response.body());
                    page++;
                    //CachingService.startNext(MyApplication.getContext(), category, page);
                    if (list != null) {
                        list.post(new Runnable() {
                            @Override
                            public void run() {
                                isLoading = false;
                                loading.setVisibility(View.GONE);
                            }
                        });
                    }
                }
                else {
                    LoadData(movieAdapter, category, loading, list);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void LoadDataToPage(final MovieAdapter movieAdapter, int category, int page, final View view, final int position) {
        while (this.page <= page + 1) {
            Retrofit retrofit = AppHelper.getRetrofit();
            MovieAPI movieAPI = retrofit.create(MovieAPI.class);

            this.page++;
            Call<MovieList> call = movieAPI.loadMoviesByPage(AppHelper.getCategoryString(category), this.page);
            if (this.page == page + 1) {
                isLast = true;
            }
            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                    movieAdapter.addItems(response.body());
                    if (isLast) {
                        if (view instanceof ListView) {
                            ListView listView = (ListView) view;
                            listView.smoothScrollToPosition(position);
                        } else if (view instanceof GridView) {
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
}
