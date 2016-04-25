package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ImageList {
    @SerializedName("results")
    private List<Image> images;

    public List<Image> getImages() { return images; }

    public void addImage(String backdropPath) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(new Image(backdropPath));
    }
}
