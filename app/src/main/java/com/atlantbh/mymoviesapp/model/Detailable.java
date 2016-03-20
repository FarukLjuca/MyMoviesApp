package com.atlantbh.mymoviesapp.model;

public interface Detailable {
    int getId();
    String getBackdropPath();
    String getTitle();
    String getYear();
    String getSubtitle();
    String getPosterPath();
    String getBasicText();
    Float getVoteAverage();
    int getVoteCount();
    ActorList getActorList();
    boolean getFavorite();
    void setFavorite(boolean favorite);
}
