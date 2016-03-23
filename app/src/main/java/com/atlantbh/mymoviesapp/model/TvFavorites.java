package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvFavorites {
    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<Tv> tvList;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Tv> getTvList() {
        return tvList;
    }

    public void setTvList(ArrayList<Tv> tvList) {
        this.tvList = tvList;
    }

    public void setTvList(List<Tv> tvList) {
        this.tvList = tvList;
    }
}
