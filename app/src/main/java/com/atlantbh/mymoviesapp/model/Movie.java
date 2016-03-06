package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Movie implements Serializable {

    @SerializedName("id")
    private Integer _id;

    @SerializedName("poster_path")
    private String _posterPath;

    @SerializedName("backdrop_path")
    private String _backdropPath;

    @SerializedName("overview")
    private String _overview;

    @SerializedName("original_title")
    private String _originalTitle;

    @SerializedName("runtime")
    private int _runtime;

    @SerializedName("genres")
    private List<Genre> _genres;

    @SerializedName("release_date")
    private String _releaseDate;

    @SerializedName("vote_average")
    private float _voteAverage;

    @SerializedName("vote_count")
    private int _voteCount;

    public Integer getId() { return _id; }
    public String getBackdropPath() { return _backdropPath; }
    public String getPosterPath() { return _posterPath; }
    public String getOverview() { return _overview; }
    public String getOriginalTitle() { return _originalTitle; }
    public int getRuntime() { return _runtime; }
    public List<Genre> getGenres() { return _genres; }
    public Date getReleaseDate() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return format.parse(_releaseDate);
    }
    public float getVoteAverage() { return _voteAverage; }
    public int getVoteCount() { return _voteCount; }
}
