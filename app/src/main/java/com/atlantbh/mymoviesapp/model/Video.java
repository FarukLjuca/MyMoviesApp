package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("key")
    private String key;

    public String getKey() { return key; }
}
