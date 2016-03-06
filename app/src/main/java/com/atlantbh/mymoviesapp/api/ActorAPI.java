package com.atlantbh.mymoviesapp.api;

import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.ActorList;
import com.atlantbh.mymoviesapp.model.ImageList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ActorAPI {
    @GET("/3/movie/{movieId}/casts?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<ActorList> loadActorsByMovieId(@Path("movieId") long movieId);

    @GET("/3/person/{actorId}?api_key=7ba2567f9f865330c7dbaae861f9e566&append_to_response=tagged_images,movie_credits,tv_credits")
    Call<Actor> loadActorById(@Path("actorId") long actorId);
}
