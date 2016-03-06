package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("media")
    private Movie movie;

    public Movie getMovie() { return movie; }
}