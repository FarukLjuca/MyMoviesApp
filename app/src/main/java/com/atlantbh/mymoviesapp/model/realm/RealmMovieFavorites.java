package com.atlantbh.mymoviesapp.model.realm;

import com.atlantbh.mymoviesapp.model.Movie;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmMovieFavorites extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private String basicText;
    private String posterPath;
    private Float voteAverage;

    public RealmMovieFavorites() {}

    public RealmMovieFavorites(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        basicText = movie.getBasicText();
        posterPath = movie.getPosterPath();
        voteAverage = movie.getVoteAverage();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBasicText() {
        return basicText;
    }

    public void setBasicText(String basicText) {
        this.basicText = basicText;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }
}
