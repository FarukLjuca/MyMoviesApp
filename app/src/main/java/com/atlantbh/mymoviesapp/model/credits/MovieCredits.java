package com.atlantbh.mymoviesapp.model.credits;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCredits {
    @SerializedName("cast")
    private List<CreditsMovieCast> creditsMovieCast;
    @SerializedName("crew")
    private List<CreditsMovieCrew> creditsMovieCrew;

    public List<CreditsMovieCast> getCreditsMovieCast() { return creditsMovieCast; }
    public List<CreditsMovieCrew> getCreditsMovieCrew() { return creditsMovieCrew; }
}
