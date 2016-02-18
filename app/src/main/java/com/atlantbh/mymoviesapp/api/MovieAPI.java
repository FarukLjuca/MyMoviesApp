package com.atlantbh.mymoviesapp.api;


import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MovieAPI {
    @GET("/3/discover/movie?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<MovieList> loadMovies();

    @GET("/3/discover/movie?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<MovieList> loadMoviesByPage(@Query("page") int page);

    @GET("/3/movie/{movieId}?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<Movie> loadMovieById(@Path("movieId") long id);
}
