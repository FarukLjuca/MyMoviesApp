package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("id")
    private int _id;

    @SerializedName("name")
    private String _name;

    public int getId() { return _id; }
    public String getName() { return _name; }
}
