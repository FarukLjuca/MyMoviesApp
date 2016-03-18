package com.atlantbh.mymoviesapp.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmActor extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String profilePath;

    public RealmActor() {}

    public RealmActor(int id, String name, String profilePath) {
        setId(id);
        setName(name);
        setProfilePath(profilePath);
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

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
