package com.atlantbh.mymoviesapp.api;

import com.atlantbh.mymoviesapp.model.VideoList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface VideoAPI {
    @GET("/3/movie/{movieId}/videos?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<VideoList> loadVideosByMovieId(@Path("movieId") long movieId);
}
