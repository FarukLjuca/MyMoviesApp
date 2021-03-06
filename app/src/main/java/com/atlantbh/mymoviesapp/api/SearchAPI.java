package com.atlantbh.mymoviesapp.api;

import com.atlantbh.mymoviesapp.model.SearchResult;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface SearchAPI {
    @GET("/3/search/multi?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<SearchResult> getSearchResult(@Query("query") String query);
}
