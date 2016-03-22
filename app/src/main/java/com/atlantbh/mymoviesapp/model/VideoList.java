package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoList {
    @SerializedName("results")
    private List<Video> videos;

    public Video getFirst() {
        if (getVideos() != null && getVideos().size() > 0) {
            return getVideos().get(0);
        }
        else {
            return null;
        }
    }

    public List<Video> getVideos() { return videos; }
}
