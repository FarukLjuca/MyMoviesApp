package com.atlantbh.mymoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Tv implements Detailable {
    @SerializedName("id")
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("genres")
    private List<Genre> genres;
    @SerializedName("first_air_date")
    private Date firstAirDate;
    @SerializedName("vote_average")
    private Float voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("credits")
    private ActorList actorList;

    public int getId() { return id; }
    public String getBackdropPath() { return backdropPath; }
    public String getPosterPath() { return posterPath; }
    public String getBasicText() { return overview; }
    public Float getVoteAverage() { return voteAverage; }
    public int getVoteCount() { return voteCount; }
    public ActorList getActorList() { return actorList; }
    public String getTitle() { return name; }
    public String getYear() {
        String result = "";
        if (firstAirDate != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            result = "(" + format.format(firstAirDate) + ")";
        }
        return result;
    }
    public String getSubtitle() {
        String result = "";

        result += status;
        result += " |";

        for (int i = 0; i < genres.size(); i++) {
            result += " " + genres.get(i).getName();
        }
        return result;
    }
}