package com.atlantbh.mymoviesapp.api;

import com.atlantbh.mymoviesapp.model.RequestToken;
import com.atlantbh.mymoviesapp.model.Session;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserAPI {
    @GET("/3/authentication/token/new?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<RequestToken> getRequestToken();

    @GET("3/authentication/token/validate_with_login?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<RequestToken> getUserPermissions(@Query("request_token") String requestToken, @Query("username") String username, @Query("password") String password);

    @GET("3/authentication/session/new?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<Session> getSession(@Query("request_token") String requestToken);
}
