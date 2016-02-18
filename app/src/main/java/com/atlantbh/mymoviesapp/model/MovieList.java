package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {
    @SerializedName("results")
    private List<Movie> _movies;

    public List<Movie> getMovies() {
        return _movies;
    }
}
