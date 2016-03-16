package com.atlantbh.mymoviesapp.model.realm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmActor extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String posterPath;

    public RealmActor() {}

    public RealmActor(int id, String name, String profilePath) {
        setId(id);
        setName(name);
        setPosterPath(posterPath);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
