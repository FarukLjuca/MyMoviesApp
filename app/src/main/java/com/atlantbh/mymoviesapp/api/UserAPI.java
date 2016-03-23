package com.atlantbh.mymoviesapp.api;

import com.atlantbh.mymoviesapp.model.FavoritePost;
import com.atlantbh.mymoviesapp.model.MovieFavorites;
import com.atlantbh.mymoviesapp.model.RequestToken;
import com.atlantbh.mymoviesapp.model.Response;
import com.atlantbh.mymoviesapp.model.Session;
import com.atlantbh.mymoviesapp.model.TvFavorites;
import com.atlantbh.mymoviesapp.model.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserAPI {
    @GET("/3/authentication/token/new?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<RequestToken> getRequestToken();

    @GET("3/authentication/token/validate_with_login?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<RequestToken> getUserPermissions(@Query("request_token") String requestToken, @Query("username") String username, @Query("password") String password);

    @GET("3/authentication/session/new?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<Session> getSession(@Query("request_token") String requestToken);

    @GET("3/account?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<User> getGeneralUserInfo(@Query("session_id") String sessionId);

    @GET("3/account/{id}/favorite/movies?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<MovieFavorites> getMovieFavorites(@Path("id") int userId, @Query("session_id") String sessionId);

    @GET("3/account/{id}/favorite/tv?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<TvFavorites> getTvFavorites(@Path("id") int userId, @Query("session_id") String sessionId);

    @POST("3/account/{id}/favorite?api_key=7ba2567f9f865330c7dbaae861f9e566")
    Call<Response> setFavorite(@Path("id") int userId, @Query("session_id") String sessionId, @Body FavoritePost favoritePost);
}
