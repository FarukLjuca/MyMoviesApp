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

    public int getRuntime() {
        return runtime;
    }
}
