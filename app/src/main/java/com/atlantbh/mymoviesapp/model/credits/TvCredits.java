package com.atlantbh.mymoviesapp.model.credits;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvCredits extends Credits {
    @SerializedName("cast")
    private List<CreditsTvCast> creditsTvCast;
    @SerializedName("crew")
    private List<CreditsTvCrew> creditsTvCrew;

    public List<CreditsTvCast> getCreditsTvCast() { return creditsTvCast; }
    public List<CreditsTvCrew> getCreditsTvCrew() { return creditsTvCrew; }
}
