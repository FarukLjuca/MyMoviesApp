package com.atlantbh.mymoviesapp.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmTvCredits extends RealmObject{
    @PrimaryKey
    private int id;
    private String posterPath;
    private String title;

    public RealmTvCredits() {}

    public RealmTvCredits(int id, String posterPath, String title) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
