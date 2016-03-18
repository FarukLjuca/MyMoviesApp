package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ActorList {
    @SerializedName("cast")
    private List<Actor> actors;

    public ActorList () {
        actors = new ArrayList<>();
    }

    public List<Actor> getActors()
    {
        return actors;
    }

    public Actor get (int position) { return actors.get(position); }

    public void addActor (Actor actor) {
        actors.add(actor);
    }
}
