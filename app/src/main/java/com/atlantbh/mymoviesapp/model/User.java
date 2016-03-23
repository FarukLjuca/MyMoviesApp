package com.atlantbh.mymoviesapp.model;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.api.TvAPI;
import com.atlantbh.mymoviesapp.api.UserAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class User {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    private static User instance;
    private RequestToken requestToken;
    private static Session session;

    private TvFavorites tvFavorites;
    private MovieFavorites movieFavorites;
    private int sum = 0;
    private int index = 0;

    private User() {}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public static Session getSession() {
        return session;
    }

    public void login(final String username, final String password, final Activity activity, final TextView error) {

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
                                        getFavorites();
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

                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getFavorites() {
        Retrofit retrofit = AppHelper.getRetrofit();
        final UserAPI userAPI = retrofit.create(UserAPI.class);

        Call<User> call = userAPI.getGeneralUserInfo(User.getSession().getSessionId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                User responseUser = response.body();
                User user = User.getInstance();
                user.setId(responseUser.getId());
                user.setName(responseUser.getName());

                Call<MovieFavorites> moviesCall = userAPI.getMovieFavorites(user.getId(), User.getSession().getSessionId());
                moviesCall.enqueue(new Callback<MovieFavorites>() {
                    @Override
                    public void onResponse(Response<MovieFavorites> response, Retrofit retrofit) {
                        movieFavorites = response.body();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

                Call<TvFavorites> tvCall = userAPI.getTvFavorites(user.getId(), User.getSession().getSessionId());
                tvCall.enqueue(new Callback<TvFavorites>() {
                    @Override
                    public void onResponse(Response<TvFavorites> response, Retrofit retrofit) {
                        tvFavorites = response.body();

                        for (Tv tv : tvFavorites.getTvList()) {
                            index++;

                            TvAPI tvAPI = retrofit.create(TvAPI.class);
                            final Call<Tv> innerCall = tvAPI.loadTvById(tv.getId());
                            innerCall.enqueue(new Callback<Tv>() {
                                @Override
                                public void onResponse(Response<Tv> response, Retrofit retrofit) {
                                    Tv innerTv = response.body();
                                    if (sum < index) {
                                        sum++;
                                        for (Tv loopTv : tvFavorites.getTvList()) {
                                            if (loopTv.getId() == innerTv.getId()) {
                                                loopTv.setBasicText(innerTv.getBasicText());
                                                break;
                                            }
                                        }
                                    }
                                    else {
                                        if (movieFavorites != null) {
                                            index = 0;
                                            sum = 0;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public MovieFavorites getMovieFavorites() {
        return movieFavorites;
    }

    public TvFavorites getTvFavorites() {
        return tvFavorites;
    }

    public boolean isFavorite(int id) {
        boolean isIn = false;

        for(Movie movie : movieFavorites.getMovieList()) {
            if (movie.getId() == id) {
                isIn = true;
                break;
            }
        }

        if (!isIn) {
            for (Tv tv : tvFavorites.getTvList()) {
                if (tv.getId() == id) {
                    isIn = true;
                    break;
                }
            }
        }

        return isIn;
    }
}
