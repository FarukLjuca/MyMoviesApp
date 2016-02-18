package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoList {
    @SerializedName("results")
    private List<Video> videos;

    public List<Video> getVideos() { return videos; }
}
