package com.atlantbh.mymoviesapp.model;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.api.UserAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class User {

    private static User instance;
    private RequestToken requestToken;
    private static Session session;

    private User() {
        if (requestToken == null || requestToken.getExpiresAt().after(new Date())) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            UserAPI userAPI = retrofit.create(UserAPI.class);

            Call<RequestToken> call = userAPI.getRequestToken();
            call.enqueue(new Callback<RequestToken>() {
                @Override
                public void onResponse(Response<RequestToken> response, Retrofit retrofit) {
                    requestToken = response.body();
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void login(String username, String password, final Activity activity, final TextView error) {
        Retrofit retrofit = AppHelper.getRetrofit();
        final UserAPI userAPI = retrofit.create(UserAPI.class);

        Call<RequestToken> call = userAPI.getUserPermissions(requestToken.getRequestToken(), username, password);
        call.enqueue(new Callback<RequestToken>() {
            @Override
            public void onResponse(Response<RequestToken> response, Retrofit retrofit) {
                RequestToken requestToken = response.body();
                if (requestToken != null && requestToken.getSuccess()) {
                    Call<Session> innerCall = userAPI.getSession(requestToken.getRequestToken());
                    innerCall.enqueue(new Callback<Session>() {
                        @Override
                        public void onResponse(Response<Session> response, Retrofit retrofit) {
                            Session session = response.body();
                            if (session.isSuccess()) {
                                Toast.makeText(activity, "You are now logged in.", Toast.LENGTH_SHORT).show();
                                User.session = session;
                                activity.finish();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
                else {
                    error.setText("Invalid username or password!");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                error.setText("Invalid username or password!");
            }
        });
    }

    public static boolean isLoggedIn() {
        return session != null && session.getSessionId() != "";
    }

    public static void favorite(int detailableId, boolean action, String contentType, ImageView imageView) {
        if (action) {
            imageView.setImageResource(R.drawable.ic_favorite_border_white_48dp);
        } else {
            imageView.setImageResource(R.drawable.ic_favorite_white_48dp);
        }
    }
}
