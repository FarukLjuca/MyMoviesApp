package com.atlantbh.mymoviesapp.model.realm;

import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.Genre;
import com.atlantbh.mymoviesapp.model.Movie;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmMovieBasic extends RealmObject {
    @PrimaryKey
    private int id;
    private String posterPath;
    private String overview;
    private String title;
    private Float voteAverage;

    private int indexPopular;
    private int indexNowPlaying;
    private int indexTopRated;
    private int category;

    public RealmMovieBasic() {}

    public RealmMovieBasic(Movie movie) {
        id = movie.getId();
        posterPath = movie.getPosterPath();
        overview = movie.getOverview();
        title = movie.getTitle();
        voteAverage = movie.getVoteAverage();
        setId(movie.getId());

        setIndexPopular(-1);
        setIndexNowPlaying(-1);
        setIndexTopRated(-1);
        setCategory(-1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
