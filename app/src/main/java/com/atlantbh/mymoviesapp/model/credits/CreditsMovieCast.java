package com.atlantbh.mymoviesapp.model.credits;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreditsMovieCast extends CreditsCast {
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private Date releaseDate;

    public String getTitle() { return title; }
    public Date getReleaseDate() { return releaseDate; }
}
