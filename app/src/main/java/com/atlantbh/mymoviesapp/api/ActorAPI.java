package com.atlantbh.mymoviesapp.api;

import com.atlantbh.mymoviesapp.model.ActorList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ActorAPI {
    @GET("/3/movie/{movieId}/casts?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<ActorList> loadActorsByMovieId(@Path("movieId") long movieId);
}
