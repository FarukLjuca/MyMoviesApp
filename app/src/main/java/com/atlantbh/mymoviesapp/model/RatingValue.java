package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class RatingValue {
    @SerializedName("value")
    private float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
