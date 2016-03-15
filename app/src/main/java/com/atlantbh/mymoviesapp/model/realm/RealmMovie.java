package com.atlantbh.mymoviesapp.model.realm;

import com.atlantbh.mymoviesapp.model.Movie;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmMovie extends RealmObject {
    @PrimaryKey
    private int id;
    private int indexPopular;
    private int indexNowPlaying;
    private int indexTopRated;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    private int category;
    private String posterPath;
    private String overview;
    private String title;
    private Float voteAverage;

    public RealmMovie() {}

    public RealmMovie(Movie movie) {
        id = movie.getId();
        posterPath = movie.getPosterPath();
        overview = movie.getOverview();
        title = movie.getTitle();
        voteAverage = movie.getVoteAverage();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndexPopular() {
        return indexPopular;
    }

    public void setIndexPopular(int indexPopular) {
        this.indexPopular = indexPopular;
    }

    public int getIndexNowPlaying() {
        return indexNowPlaying;
    }

    public void setIndexNowPlaying(int indexNowPlaying) {
        this.indexNowPlaying = indexNowPlaying;
    }

    public int getIndexTopRated() {
        return indexTopRated;
    }

    public void setIndexTopRated(int indexTopRated) {
        this.indexTopRated = indexTopRated;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }
}
