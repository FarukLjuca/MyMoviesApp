package com.atlantbh.mymoviesapp.model;

import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.model.realm.RealmActor;
import com.atlantbh.mymoviesapp.model.realm.RealmGenre;
import com.atlantbh.mymoviesapp.model.realm.RealmMovie;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieBasic;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie implements Detailable {
    public static final int POPULAR = 0;
    public static final int NOW_PAYING = 1;
    public static final int TOP_RATED = 2;
    public static final int POPULAR_AND_NOW_PLAYING = 3;
    public static final int POPULAR_AND_TOP_RATED = 4;
    public static final int NOW_PLAYING_AND_TOP_RATED = 5;
    public static final int POPULAR_AND_NOW_PLAYING_AND_TOP_RATED = 6;

    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("title")
    private String title;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("release_date")
    private Date releaseDate;
    @SerializedName("vote_average")
    private Float voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("casts")
    private ActorList actorList;
    @SerializedName("videos")
    private VideoList videoList;

    public Movie(RealmMovieBasic movie) {
        setId(movie.getId());
        setOverview(movie.getOverview());
        setVoteAverage(movie.getVoteAverage());
        setTitle(movie.getTitle());
        setPosterPath(movie.getPosterPath());
    }

    public Movie(RealmMovie movie) {
        setId(movie.getId());
        setOverview(movie.getOverview());
        setVoteAverage(movie.getVoteAverage());
        setTitle(movie.getTitle());
        setPosterPath(movie.getPosterPath());
        setBackdropPath(movie.getBackdropPath());
        setRuntime(movie.getRuntime());
        setVoteCount(movie.getVoteCount());
        setReleaseDate(movie.getReleaseDate());
        setRuntime(movie.getRuntime());

        ActorList actorList = new ActorList();
        for (RealmActor actor : movie.getActors()) {
            actorList.addActor(new Actor(actor));
        }
        setActorList(actorList);

        List<Genre> genres = new ArrayList<>();
        for (RealmGenre genre : movie.getGenres()) {
            genres.add(new Genre(genre.getId(), genre.getName()));
        }
        setGenres(genres);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public ActorList getActorList() {
        return actorList;
    }

    public void setActorList(ActorList actorList) {
        this.actorList = actorList;
    }

    @Override
    public String getBasicText() {
        return getOverview();
    }

    public String getYear() {
        StringBuilder result = new StringBuilder();
        if (releaseDate != null) {
            result.append("(");
            result.append(AppHelper.getSimpleDateFormat().format(releaseDate));
            result.append(")");
        }
        return result.toString();
    }
    public String getSubtitle() {
        StringBuilder result = new StringBuilder();

        if (runtime != 0) {
            result.append(runtime / 60);
            result.append("h ");
            result.append(runtime - (runtime / 60) * 60);
            result.append("m |");
        }
        if (genres != null) {
            for (int i = 0; i < genres.size(); i++) {
                result.append(" ");
                result.append(genres.get(i).getName());
            }
        }
        return result.toString();
    }

    public static int mergeCategories(int oldCategory, int newCategory) {
        int result;
        if (oldCategory == POPULAR && newCategory == NOW_PAYING) {
            result = POPULAR_AND_NOW_PLAYING;
        }
        else if (oldCategory == POPULAR && newCategory == TOP_RATED) {
            result = POPULAR_AND_TOP_RATED;
        }
        else if (oldCategory == NOW_PAYING && newCategory == TOP_RATED) {
            result = NOW_PLAYING_AND_TOP_RATED;
        }
        else {
            result = POPULAR_AND_NOW_PLAYING_AND_TOP_RATED;
        }

        return result;
    }

    public VideoList getVideoList() {
        return videoList;
    }

    public void setVideoList(VideoList videoList) {
        this.videoList = videoList;
    }
}
