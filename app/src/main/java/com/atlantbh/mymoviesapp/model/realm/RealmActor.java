package com.atlantbh.mymoviesapp.model.realm;

import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.credits.Credits;
import com.atlantbh.mymoviesapp.model.credits.CreditsMovieCast;
import com.atlantbh.mymoviesapp.model.credits.CreditsTvCast;

import java.text.SimpleDateFormat;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmActor extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String profilePath;
    private String backdropPath;
    private String biography;
    private String jobs;
    private RealmList<RealmMovieCredits> realmMovieCredits;
    private RealmList<RealmTvCredits> realmTvCredits;

    public RealmActor() {}

    //Todo: This needs to dissapear
    public RealmActor(int id, String name, String profilePath) {
        setId(id);
        setName(name);
        setProfilePath(profilePath);
    }

    public RealmActor (Actor actor) {
        id = actor.getId();
        name = actor.getName();
        profilePath = actor.getProfilePath();
        if (actor.getImageList() != null) {
            backdropPath = actor.getImageList().getImages().get(0).getMovie().getBackdropPath();
        }
        biography = actor.getBiography();
        jobs = actor.getFrequentJobs();
        realmMovieCredits = new RealmList<>();
        realmTvCredits = new RealmList<>();

        for (CreditsMovieCast credit : actor.getMovieCredits().getCreditsMovieCast()) {
            String title = credit.getTitle();
            if (credit.getReleaseDate() != null) {
                SimpleDateFormat simpleDate = AppHelper.getSimpleDateFormat();
                title += " (" + simpleDate.format(credit.getReleaseDate()) + ")";
            }
            realmMovieCredits.add(new RealmMovieCredits(credit.getId(), credit.getPosterPath(), title));
        }

        for (CreditsTvCast credit : actor.getTvCredits().getCreditsTvCast()) {
            String title = credit.getName();
            if (credit.getFirstAirDate() != null) {
                SimpleDateFormat simpleDate = AppHelper.getSimpleDateFormat();
                title += " (" + simpleDate.format(credit.getFirstAirDate()) + ")";
            }
            realmTvCredits.add(new RealmTvCredits(credit.getId(), credit.getPosterPath(), title));
        }
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public RealmList<RealmMovieCredits> getRealmMovieCredits() {
        return realmMovieCredits;
    }

    public void setRealmMovieCredits(RealmList<RealmMovieCredits> realmMovieCredits) {
        this.realmMovieCredits = realmMovieCredits;
    }

    public RealmList<RealmTvCredits> getRealmTvCredits() {
        return realmTvCredits;
    }

    public void setRealmTvCredits(RealmList<RealmTvCredits> realmTvCredits) {
        this.realmTvCredits = realmTvCredits;
    }
}
