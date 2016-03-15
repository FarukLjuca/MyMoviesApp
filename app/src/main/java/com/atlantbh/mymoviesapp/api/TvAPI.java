package com.atlantbh.mymoviesapp.api;

import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.Tv;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface TvAPI {
    @GET("/3/tv/{tvId}?api_key=7ba2567f9f865330c7dbaae861f9e566&append_to_response=credits")
    Call<Tv> loadTvById(@Path("tvId") int id);
}
