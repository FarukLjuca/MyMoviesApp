package com.atlantbh.mymoviesapp.api;


import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MovieAPI {
    @GET("/3/movie/{category}?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<MovieList> loadMovieByCategory(@Path("category") String category);

    @GET("/3/movie/{category}?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<MovieList> loadMoviesByPage(@Path("category") String category, @Query("page") int page);

    @GET("/3/movie/{movieId}?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<Movie> loadMovieById(@Path("movieId") long id);
}
