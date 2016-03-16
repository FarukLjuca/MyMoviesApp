package com.atlantbh.mymoviesapp.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmGenre extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;

    public RealmGenre() {}

    public RealmGenre(int id, String name) {
        setId(id);
        setName(name);
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
}
