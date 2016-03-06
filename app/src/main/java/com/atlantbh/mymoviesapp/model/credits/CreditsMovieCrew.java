package com.atlantbh.mymoviesapp.model.credits;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreditsMovieCrew extends CreditsCrew {
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private Date releaseDate;

    public String getTitle() { return title; }
    public Date getReleaseDate() { return releaseDate; }
}
