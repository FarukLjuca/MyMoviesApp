package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewList {
    @SerializedName("results")
    private List<Review> reviewList;

    public List<Review> getReviewList() {
        return reviewList;
    }
}
