package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActorList {
    @SerializedName("cast")
    private List<Actor> actors;

    public List<Actor> getActors() {
        return actors;
    }

    public Actor get (int position) { return actors.get(position); }
}
