package com.atlantbh.mymoviesapp.model;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.MyApplication;
import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.adapters.FavoritesAdapter;
import com.atlantbh.mymoviesapp.api.TvAPI;
import com.atlantbh.mymoviesapp.api.UserAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.model.realm.RealmMovie;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieFavorites;
import com.atlantbh.mymoviesapp.model.realm.RealmTvFavorites;
import com.atlantbh.mymoviesapp.model.realm.RealmUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;
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
    private boolean tvLoadingDone = false;

    private User() {}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public static Session getSession() {
        Session result;
        if (session != null) {
            result = session;
        }
        else {
            result = new Session();
        }
        return result;
    }

    public void logout() {
        id = 0;
        name = "";
        session = null;
        requestToken = null;
        movieFavorites = null;
        tvFavorites = null;

        Realm realm = Realm.getInstance(MyApplication.getContext());

        realm.beginTransaction();
        realm.where(RealmUser.class).findAll().clear();
        realm.where(RealmMovieFavorites.class).findAll().clear();
        realm.where(RealmTvFavorites.class).findAll().clear();
        realm.commitTransaction();
        realm.close();
    }

    public void getUserFromDatabase() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(MyApplication.getContext()).build();
        Realm realm = null;

        try {
            realm = Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfiguration);
                realm = Realm.getInstance(realmConfiguration);
            } catch (Exception ex){}
        }

        if (realm != null) {
            realm.beginTransaction();
            RealmResults<RealmUser> realmResults = realm.where(RealmUser.class).findAll();
            if (realmResults.size() > 0) {
                RealmUser user = realmResults.get(0);
                id = user.getId();
                name = user.getName();
                session = new Session();
                session.setSessionId(user.getSessionId());
                requestToken = new RequestToken();
                requestToken.setRequestToken(user.getRequestToken());
                requestToken.setExpiresAt(user.getRequestTokenExpiration());

                movieFavorites = new MovieFavorites();
                List<Movie> movieList = new ArrayList<>();
                for (RealmMovieFavorites movie : user.getMovieFavorites()) {
                    movieList.add(new Movie(movie));
                }
                movieFavorites.setMovieList(movieList);

                tvFavorites = new TvFavorites();
                List<Tv> tvList = new ArrayList<>();
                for (RealmTvFavorites tv : user.getTvFavorites()) {
                    tvList.add(new Tv(tv));
                }
                tvFavorites.setTvList(tvList);
            }

            realm.commitTransaction();
        }
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
        getFavorites(null, null);
    }

    public void getFavorites(final SwipeRefreshLayout refreshLayout, final FavoritesAdapter favoritesAdapter) {
        Retrofit retrofit = AppHelper.getRetrofit();
        final UserAPI userAPI = retrofit.create(UserAPI.class);

        Call<User> call = userAPI.getGeneralUserInfo(User.getSession().getSessionId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.body() != null) {
                    User responseUser = response.body();
                    User user = User.getInstance();
                    user.setId(responseUser.getId());
                    user.setName(responseUser.getName());

                    Call<MovieFavorites> moviesCall = userAPI.getMovieFavorites(user.getId(), User.getSession().getSessionId());
                    moviesCall.enqueue(new Callback<MovieFavorites>() {
                        @Override
                        public void onResponse(Response<MovieFavorites> response, Retrofit retrofit) {
                            movieFavorites = response.body();
                            if (tvLoadingDone) {
                                saveToDatabase();
                                tvLoadingDone = false;
                                if (refreshLayout != null) {
                                    refreshLayout.setRefreshing(false);
                                    favoritesAdapter.setMovieFavorites(movieFavorites);
                                    favoritesAdapter.setTvFavorites(tvFavorites);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            refreshLayout.setRefreshing(false);
                        }
                    });

                    Call<TvFavorites> tvCall = userAPI.getTvFavorites(user.getId(), User.getSession().getSessionId());
                    tvCall.enqueue(new Callback<TvFavorites>() {
                        @Override
                        public void onResponse(Response<TvFavorites> response, Retrofit retrofit) {
                            if (response.body() != null) {
                                tvFavorites = response.body();

                                for (Tv tv : tvFavorites.getTvList()) {
                                    index++;

                                    TvAPI tvAPI = retrofit.create(TvAPI.class);
                                    final Call<Tv> innerCall = tvAPI.loadTvById(tv.getId());
                                    innerCall.enqueue(new Callback<Tv>() {
                                        @Override
                                        public void onResponse(Response<Tv> response, Retrofit retrofit) {
                                            Tv innerTv = response.body();

                                            if (innerTv != null) {
                                                for (Tv loopTv : tvFavorites.getTvList()) {
                                                    if (loopTv.getId() == innerTv.getId()) {
                                                        loopTv.setBasicText(innerTv.getBasicText());
                                                        break;
                                                    }
                                                }
                                                sum++;
                                                if (sum >= index) {
                                                    index = 0;
                                                    sum = 0;
                                                    tvLoadingDone = true;
                                                    if (movieFavorites != null) {
                                                        saveToDatabase();
                                                        tvLoadingDone = false;
                                                        if (refreshLayout != null) {
                                                            refreshLayout.setRefreshing(false);
                                                            favoritesAdapter.setMovieFavorites(movieFavorites);
                                                            favoritesAdapter.setTvFavorites(tvFavorites);
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            refreshLayout.setRefreshing(false);
                                        }
                                    });
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

    public MovieFavorites getMovieFavorites() {
        return movieFavorites;
    }

    public TvFavorites getTvFavorites() {
        return tvFavorites;
    }

    public boolean isFavorite(int id) {
        boolean isIn = false;

        if (movieFavorites != null && tvFavorites != null) {
            for (Movie movie : movieFavorites.getMovieList()) {
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
        }

        return isIn;
    }

    private void saveToDatabase() {
        if (movieFavorites != null && tvFavorites != null) {
            RealmUser realmUser = new RealmUser(this);

            Realm realm = Realm.getInstance(MyApplication.getContext());

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(realmUser);
            realm.commitTransaction();
            realm.close();
        }
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }
}
