package com.atlantbh.mymoviesapp.model.credits;

import com.google.gson.annotations.SerializedName;

public class Credits {
    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String posterPath;

    public int getId() { return id; }
    public String getPosterPath() { return posterPath; }
}
