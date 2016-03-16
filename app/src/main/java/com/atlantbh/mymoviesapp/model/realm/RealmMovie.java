package com.atlantbh.mymoviesapp.model.realm;

import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.Genre;
import com.atlantbh.mymoviesapp.model.Movie;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmMovie extends RealmObject {
    @PrimaryKey
    private int id;
    private int indexPopular;
    private int indexNowPlaying;
    private int indexTopRated;
    private int category;
    private String posterPath;
    private String overview;
    private String title;
    private Float voteAverage;
    private String backdropPath;
    private int voteCount;
    private Date releaseDate;
    private RealmList<RealmGenre> genres;
    private RealmList<RealmActor> actors;
    private int runtime;

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public RealmMovie() {}

    public RealmMovie(Movie movie) {
        id = movie.getId();
        posterPath = movie.getPosterPath();
        overview = movie.getOverview();
        title = movie.getTitle();
        voteAverage = movie.getVoteAverage();

        setBackdropPath(movie.getBackdropPath());
        setVoteCount(movie.getVoteCount());
        setReleaseDate(movie.getReleaseDate());
        setRuntime(movie.getRuntime());

        genres = new RealmList<>();
        if (movie.getGenres() != null) {
            for (Genre genre : movie.getGenres()) {
                genres.add(new RealmGenre(genre.getId(), genre.getName()));
            }
        }

        actors = new RealmList<>();
        if (movie.getActorList() != null && movie.getActorList().getActors() != null) {
            for (Actor actor : movie.getActorList().getActors()) {
                actors.add(new RealmActor(actor.getId(), actor.getName(), actor.getProfilePath()));
            }
        }

        setIndexPopular(-1);
        setIndexNowPlaying(-1);
        setIndexTopRated(-1);
        setCategory(-1);
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public RealmList<RealmGenre> getGenres() {
        return genres;
    }

    public void setGenres(RealmList<RealmGenre> genres) {
        this.genres = genres;
    }

    public RealmList<RealmActor> getActors() {
        return actors;
    }

    public void setActors(RealmList<RealmActor> actors) {
        this.actors = actors;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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

    public int getRuntime() {
        return runtime;
    }
}
