package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class Actor {
    @SerializedName("profile_path")
    private String profilePath;

    @SerializedName("name")
    private String name;

    public String getProfilePath() { return profilePath; }
    public String getName() { return name; }
}
