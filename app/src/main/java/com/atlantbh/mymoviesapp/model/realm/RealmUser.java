package com.atlantbh.mymoviesapp.model.realm;

import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.Tv;
import com.atlantbh.mymoviesapp.model.User;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmUser extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String requestToken;
    private Date requestTokenExpiration;
    private String sessionId;
    private RealmList<RealmMovieFavorites> movieFavorites;
    private RealmList<RealmTvFavorites> tvFavorites;

    public RealmUser() {}

    public RealmUser(User user) {
        id = user.getId();
        name = user.getName();
        requestToken = user.getRequestToken().getRequestToken();
        requestTokenExpiration = user.getRequestToken().getExpiresAt();
        sessionId = User.getSession().getSessionId();

        movieFavorites = new RealmList<>();
        for (Movie movie : user.getMovieFavorites().getMovieList()) {
            movieFavorites.add(new RealmMovieFavorites(movie));
        }

        tvFavorites = new RealmList<>();
        for (Tv tv : user.getTvFavorites().getTvList()) {
            tvFavorites.add(new RealmTvFavorites(tv));
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

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

    public Date getRequestTokenExpiration() {
        return requestTokenExpiration;
    }

    public void setRequestTokenExpiration(Date requestTokenExpiration) {
        this.requestTokenExpiration = requestTokenExpiration;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public RealmList<RealmMovieFavorites> getMovieFavorites() {
        return movieFavorites;
    }

    public void setMovieFavorites(RealmList<RealmMovieFavorites> movieFavorites) {
        this.movieFavorites = movieFavorites;
    }

    public RealmList<RealmTvFavorites> getTvFavorites() {
        return tvFavorites;
    }

    public void setTvFavorites(RealmList<RealmTvFavorites> tvFavorites) {
        this.tvFavorites = tvFavorites;
    }
}
