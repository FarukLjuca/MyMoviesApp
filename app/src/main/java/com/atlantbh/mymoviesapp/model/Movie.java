package com.atlantbh.mymoviesapp.model;

import com.atlantbh.mymoviesapp.model.realm.RealmMovie;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
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

    public Movie(Integer id, String posterPath, String overview, String title, Float voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.title = title;
        this.voteAverage = voteAverage;
    }

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

    public Movie(RealmMovie movie) {
        setId(movie.getId());
        setOverview(movie.getOverview());
        setVoteAverage(movie.getVoteAverage());
        setTitle(movie.getTitle());
        setPosterPath(movie.getPosterPath());
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
        String result = "";
        if (releaseDate != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            result = "(" + format.format(releaseDate) + ")";
        }
        return result;
    }
    public String getSubtitle() {
        String result = "";

        result += runtime / 60;
        result += "h ";
        result += runtime - (runtime / 60) * 60;
        result += "m |";

        for (int i = 0; i < genres.size(); i++) {
            result += " " + genres.get(i).getName();
        }
        return result;
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

    public static boolean isPopular (int category) {
        return (category == POPULAR || category == POPULAR_AND_NOW_PLAYING || category == POPULAR_AND_TOP_RATED || category == POPULAR_AND_NOW_PLAYING_AND_TOP_RATED);
    }

    public static boolean isNowPlaying (int category) {
        return (category == NOW_PAYING || category == NOW_PLAYING_AND_TOP_RATED || category == POPULAR_AND_TOP_RATED || category == POPULAR_AND_NOW_PLAYING_AND_TOP_RATED);
    }

    public static boolean isTopRated (int category) {
        return (category == TOP_RATED || category == POPULAR_AND_TOP_RATED || category == NOW_PLAYING_AND_TOP_RATED || category == POPULAR_AND_NOW_PLAYING_AND_TOP_RATED);
    }
}
