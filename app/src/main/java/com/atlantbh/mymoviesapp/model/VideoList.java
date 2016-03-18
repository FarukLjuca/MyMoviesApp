package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoList {
    @SerializedName("results")
    private List<Video> videos;

    public Video getFirst() {
        return getVideos().get(0);
    }

    public List<Video> getVideos() { return videos; }
}
