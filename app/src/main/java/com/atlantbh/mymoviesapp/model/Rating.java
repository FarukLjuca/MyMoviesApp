package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("id")
    private int id;
    @SerializedName("rating")
    private float rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
