package com.atlantbh.mymoviesapp.model;

import com.atlantbh.mymoviesapp.model.credits.MovieCredits;
import com.atlantbh.mymoviesapp.model.credits.TvCredits;
import com.atlantbh.mymoviesapp.model.realm.RealmActor;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieCredits;
import com.atlantbh.mymoviesapp.model.realm.RealmTvCredits;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profilePath;
    @SerializedName("biography")
    private String biography;
    @SerializedName("tagged_images")
    private ImageList imageList;
    @SerializedName("movie_credits")
    private MovieCredits movieCredits;
    @SerializedName("tv_credits")
    private TvCredits tvCredits;
    private String jobs;
    private List<RealmMovieCredits> realmMovieCredits;
    private List<RealmTvCredits> realmTvCredits;

    public Actor(RealmActor actor) {
        id = actor.getId();
        name = actor.getName();
        profilePath = actor.getProfilePath();
        biography = actor.getBiography();
        imageList = new ImageList();
        imageList.addImage(actor.getBackdropPath());
        jobs = actor.getJobs();
        movieCredits = new MovieCredits();
        realmMovieCredits = actor.getRealmMovieCredits();
        realmTvCredits = actor.getRealmTvCredits();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getBiography() {
        return biography;
    }

    public ImageList getImageList() {
        return imageList;
    }

    public MovieCredits getMovieCredits() {
        return movieCredits;
    }

    public TvCredits getTvCredits() {
        return tvCredits;
    }

    public String getFrequentJobs() {
        StringBuilder result = new StringBuilder();

        if (jobs == null) {
            ArrayList<String> jobTitles = new ArrayList<>();
            ArrayList<Integer> jobCount = new ArrayList<>();

            jobTitles.add("Actor");
            jobCount.add(movieCredits.getCreditsMovieCast().size() + tvCredits.getCreditsTvCast().size());


            for (int i = 0; i < movieCredits.getCreditsMovieCrew().size(); i++) {
                String currentJobTitle = movieCredits.getCreditsMovieCrew().get(i).getJob();
                if (jobTitles.contains(currentJobTitle)) {
                    jobCount.set(jobTitles.indexOf(currentJobTitle), jobCount.get(jobTitles.indexOf(currentJobTitle)) + 1);
                } else {
                    jobTitles.add(currentJobTitle);
                    jobCount.add(1);
                }
            }

            for (int i = 0; i < tvCredits.getCreditsTvCrew().size(); i++) {
                String currentJobTitle = tvCredits.getCreditsTvCrew().get(i).getJob();
                if (jobTitles.contains(currentJobTitle)) {
                    jobCount.set(jobTitles.indexOf(currentJobTitle), jobCount.get(jobTitles.indexOf(currentJobTitle)) + 1);
                } else {
                    jobTitles.add(currentJobTitle);
                    jobCount.add(1);
                }
            }

            boolean swapped = true;
            while (swapped) {
                swapped = false;
                for (int i = 0; i < jobCount.size() - 1; i++) {
                    if (jobCount.get(i) < jobCount.get(i + 1)) {
                        Integer tempInt = jobCount.get(i);
                        jobCount.set(i, jobCount.get(i + 1));
                        jobCount.set(i + 1, tempInt);

                        String tempString = jobTitles.get(i);
                        jobTitles.set(i, jobTitles.get(i + 1));
                        jobTitles.set(i + 1, tempString);

                        swapped = true;
                    }
                }
            }

            for (int i = 0; i < jobTitles.size(); i++) {
                if (i != 0) {
                    result.append(" - ");
                }
                result.append(jobTitles.get(i));
            }
        }
        else {
            result.append(jobs);
        }

        return result.toString();
    }

    public void copy(Actor actor) {
        id = actor.getId();
        name = actor.getName();
        profilePath = actor.getProfilePath();
        biography = actor.getBiography();
        imageList = actor.getImageList();
        movieCredits = actor.getMovieCredits();
        tvCredits = actor.getTvCredits();
    }

    public List<RealmMovieCredits> getRealmMovieCredits() {
        return realmMovieCredits;
    }

    public List<RealmTvCredits> getRealmTvCredits() {
        return realmTvCredits;
    }
}
