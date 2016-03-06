package com.atlantbh.mymoviesapp.model.credits;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreditsTvCrew extends CreditsCrew {
    @SerializedName("name")
    private String name;
    @SerializedName("first_air_date")
    private Date firstAirDate;

    public String getName() { return name; }
    public Date getFirstAirDate() { return firstAirDate; }
}
