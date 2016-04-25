package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("media")
    private Movie movie;

    public Image(String backdropPath) {
        if (movie == null) {
            movie = new Movie(backdropPath);
        }
        else {
            movie.setBackdropPath(backdropPath);
        }
    }

    public Movie getMovie() { return movie; }
}