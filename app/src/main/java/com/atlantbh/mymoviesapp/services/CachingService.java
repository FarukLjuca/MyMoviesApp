package com.atlantbh.mymoviesapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.atlantbh.mymoviesapp.MyApplication;
import com.atlantbh.mymoviesapp.api.ActorAPI;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;
import com.atlantbh.mymoviesapp.model.Tv;
import com.atlantbh.mymoviesapp.model.realm.RealmActor;
import com.atlantbh.mymoviesapp.model.realm.RealmMovie;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CachingService extends IntentService {
    private static List<Movie> movies;
    private static List<Tv> tvs;
    private static List<Actor> actors;

    // Flags that represent finish of categories loading
    private boolean popular = false;
    private boolean nowPlaying = false;
    private boolean topRated = false;

    // Counter and finish variable that take care of finishing job with movie details loading
    private int movieDetailsMax = 0;
    private int movieDetailsCounter = 0;

    // Array of flags that represents finish of loading of movies actors by movie
    private int[] movieActorsMax;
    private int[] movieActorsCounters;

    public CachingService() {
        super("CachingService");
        if (movies == null) {
            readMovies();
        }
    }

    public Context getContext() {
        return MyApplication.getContext();
    }

    public static void startFirst(Context context) {
        Intent intent = new Intent(context, CachingService.class);
        intent.setAction(AppString.ACTION_FIRST);
        context.startService(intent);
    }

    public static void startNext(Context context, int category, int page) {
        Intent intent = new Intent(context, CachingService.class);
        intent.setAction(AppString.ACTION_NEXT);
        intent.putExtra("category", category);
        intent.putExtra("page", page);
        context.startService(intent);
    }

    public static void startMovie(Context context, int movieId) {
        Intent intent = new Intent(context, CachingService.class);
        intent.setAction(AppString.ACTION_MOVIE);
        intent.putExtra("movieId", movieId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals(AppString.ACTION_FIRST)) {
                get60Movies();
            }
            /*
            // Here will go action for caching any other 20 movies
            else if (intent.getAction().equals(AppString.ACTION_NEXT)) {
                int category = intent.getIntExtra("category", -1);
                int page = intent.getIntExtra("page", -1);
                addMovies(category, page);
            }
            else if (intent.getAction().equals(AppString.ACTION_MOVIE)) {
                //Read movie, cache it by adding missing data and preserving stuff like index and favorite
                int movieId = intent.getIntExtra("movieId", -1);
                addMovie(movieId);
            }
            else if (intent.getAction().equals(AppString.ACTION_TV)) {

            }
            else if (intent.getAction().equals(AppString.ACTION_ACTOR)) {

            }
            */
            //here I will need actions for favorites and rating
        }
    }

    private void get60Movies() {
        get20Movies(AppString.CATEGORY_POPULAR);
        get20Movies(AppString.CATEGORY_NOW_PLAYING);
        get20Movies(AppString.CATEGORY_TOP_RATED);
    }

    private void get20Movies(final int category) {
        Retrofit retrofit = AppHelper.getRetrofit();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        Call<MovieList> call = movieAPI.loadMoviesByPage(getCategoryString(category), 1);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                MovieList movieList = response.body();
                if (movieList != null) {
                    addMovies(movieList.getMovies(), category);
                }
                else {
                    get20Movies(category);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                get20Movies(category);
            }
        });
    }

    private void addMovies(List<Movie> movieList, int category) {
        for (Movie movie : movieList) {
            int index = indexOf(movie, movies);
            if (index == -1) {
                movies.add(movie);
            }
            else {
                // Here I will later wory with indexes
            }
        }

        if (category == AppString.CATEGORY_POPULAR) popular = true;
        else if (category == AppString.CATEGORY_NOW_PLAYING) nowPlaying = true;
        else if (category == AppString.CATEGORY_TOP_RATED) topRated = true;

        if (popular && nowPlaying && topRated) {
            movieDetailsMax = movies.size();
            for (int i = 0; i < movies.size(); i++) {
                getMovieDetails(movies.get(i).getId(), i);
            }
        }
    }

    private void getMovieDetails(final int movieId, final int index) {
        Retrofit retrofit = AppHelper.getRetrofit();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        Call<Movie> call = movieAPI.loadMovieById(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Response<Movie> response, Retrofit retrofit) {
                Movie movie = response.body();
                if (movie != null) {
                    // These are movie specific parameters that need to be added, rest of them are already there
                    // because rest of them come with list reading of movies
                    movies.get(index).setActorList(movie.getActorList());
                    movies.get(index).setGenres(movie.getGenres());
                    movies.get(index).setRuntime(movie.getRuntime());

                    movieDetailsCounter++;

                    // This is the moment when all moves finished loading and populated exstra information
                    if (movieDetailsCounter == movieDetailsMax) {
                        Log.e("Message", "Finished with movie details.");
                        movieActorsMax = new int[movies.size()];
                        movieActorsCounters = new int[movies.size()];
                        for (int i = 0; i < movies.size(); i++) {
                            movieActorsMax[i] = movies.get(i).getActorList().getActors().size();
                            for (int j = 0; j < movies.get(i).getActorList().getActors().size(); j++) {
                                getActorDetails(movies.get(i).getActorList().getActors().get(j).getId(), i, j);
                            }
                        }
                    }
                }
                else {
                    getMovieDetails(movieId, index);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                getMovieDetails(movieId, index);
            }
        });
    }

    private void getActorDetails(final int actorId, final int indexMovie, final int indexActor) {
        Retrofit retrofit = AppHelper.getRetrofit();
        ActorAPI actorAPI = retrofit.create(ActorAPI.class);
        Call<Actor> call = actorAPI.loadActorById(actorId);
        call.enqueue(new Callback<Actor>() {
            @Override
            public void onResponse(Response<Actor> response, Retrofit retrofit) {
                Actor actor = response.body();
                if (actor != null) {
                    movies.get(indexMovie).getActorList().getActors().get(indexActor).copy(actor);

                    movieActorsCounters[indexMovie]++;

                    // If current list of actors is same, there is a chance that they all are
                    // Optimization incoming
                    if (movieActorsCounters[indexMovie] == movieActorsMax[indexMovie]) {
                        Log.e("Message", "Finished one Actor list.");
                        boolean allSame = true;
                        for (int i = 0; i < movieActorsMax.length; i++) {
                            if (movieActorsMax[i] != movieActorsCounters[i]) {
                                allSame = false;
                                break;
                            }
                        }
                        if (allSame) {
                            Log.e("Message", "All are same :).");
                            RealmList<RealmMovie> realmMovies = new RealmList<>();

                            for (Movie movie : movies) {
                                realmMovies.add(new RealmMovie(movie));
                            }

                            Realm realm = Realm.getInstance(getContext());
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(realmMovies);
                            realm.commitTransaction();
                            realm.close();
                        }
                    }
                }
                else {
                    getActorDetails(actorId, indexMovie, indexActor);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                getActorDetails(actorId, indexMovie, indexActor);
            }
        });
    }

    // Method reads all movies from database and puts them in private variable
    private void readMovies() {
        movies = new ArrayList<>();

        Realm realm = Realm.getInstance(getContext());
        RealmResults<RealmMovie> realmResults = realm.where(RealmMovie.class).findAll();
        realm.beginTransaction();

        boolean online = AppHelper.isOnline();
        for (int i = 0; i < realmResults.size(); i++) {
            // if app is online, indexes need to be refreshed, else, leave them as they are
            if (online) {
                realmResults.get(i).setIndexNowPlaying(-1);
                realmResults.get(i).setIndexTopRated(-1);
                realmResults.get(i).setIndexPopular(-1);
            }

            movies.add(new Movie(realmResults.get(i)));
        }

        realm.commitTransaction();
        realm.close();
    }

    public void addMovies(final int category, final int page) {
        Retrofit retrofit = AppHelper.getRetrofit();
        final MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<MovieList> call = movieAPI.loadMoviesByPage(getCategoryString(category), page);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                final MovieList movieList = response.body();
                if (movieList != null && movieList.getMovies() != null) {
                    for (int i = 0; i < movieList.getMovies().size(); i++) {
                        switch (category) {
                            case AppString.CATEGORY_POPULAR:
                                movieList.getMovies().get(i).setIndexPopular(i+1);
                                break;
                            case AppString.CATEGORY_NOW_PLAYING:
                                movieList.getMovies().get(i).setIndexNowPlaying(i+1);
                                break;
                            case AppString.CATEGORY_TOP_RATED:
                                movieList.getMovies().get(i).setIndexTopRated(i+1);
                                break;
                        }
                    }

                    addMoviesHelper(movieList.getMovies());

                        /*
                        if (pass == 3) {
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    Realm realm = Realm.getInstance(getContext());

                                    realm.beginTransaction();
                                    realm.copyToRealmOrUpdate(optimizedMovieList);
                                    realm.commitTransaction();
                                    realm.close();
                                }
                            };
                            Thread thread = new Thread(runnable);

                            thread.start();
                        }
                        */
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public String getCategoryString(int category) {
        String result = "";

        switch (category) {
            case AppString.CATEGORY_POPULAR:
                result = "popular";
                break;
            case AppString.CATEGORY_NOW_PLAYING:
                result = "now_playing";
                break;
            case AppString.CATEGORY_TOP_RATED:
                result = "top_rated";
                break;
        }

        return result;
    }

    public void addMoviesHelper (List<Movie> movies) {
        for (Movie movie : movies) {
            int index = indexOf(movie, this.movies);

            // if index is -1 it means movie is not in movies list and should be added
            if (index == -1) {
                if (movie.getIndexNowPlaying() > 0) movie.setIndexNowPlaying(-1);
                if (movie.getIndexPopular() > 0) movie.setIndexPopular(-1);
                if (movie.getIndexTopRated() > 0) movie.setIndexTopRated(-1);
                this.movies.add(movie);
            } else {
                // update indexes that are != -1
                if (movie.getIndexNowPlaying() > 0) {
                    this.movies.get(index).setIndexNowPlaying(movie.getIndexNowPlaying());
                }
                if (movie.getIndexPopular() > 0) {
                    this.movies.get(index).setIndexPopular(movie.getIndexPopular());
                }
                if (movie.getIndexTopRated() > 0) {
                    this.movies.get(index).setIndexTopRated(movie.getIndexTopRated());
                }
            }
        }

        List<RealmMovie> realmMovies = new ArrayList<>();
        // Queuing is from movies that are sent to function so that rest of them don't need to be refreshed
        for (Movie m : movies) {
            addMovie(m.getId());
            realmMovies.add(new RealmMovie(m));
        }

        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmMovies);
        realm.commitTransaction();
        realm.close();
    }

    public void addMovie(int movieId) {
        Retrofit retrofit = AppHelper.getRetrofit();
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        Call<Movie> call = movieAPI.loadMovieById(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Response<Movie> response, Retrofit retrofit) {
                Movie movie = response.body();
                if (movie != null) {
                    int index = indexOf(movie, movies);

                    // if index is -1 it means movie is not in movies list and should be added
                    // and here, it should always be different from -1
                    // except for situations when you are deep in app tree (actors known by movie clicked)
                    if (index == -1) {
                        movies.add(movie);
                    } else {
                        // These are movie specific parameters that need to be added, rest of them are already there
                        // because rest of them come with list reading of movies
                        movies.get(index).setActorList(movie.getActorList());
                        movies.get(index).setGenres(movie.getGenres());
                        movies.get(index).setRuntime(movie.getRuntime());
                    }

                    List<RealmMovie> realmMovies = new ArrayList<>();
                    for (Movie m : movies) {
                        realmMovies.add(new RealmMovie(m));
                    }

                    Realm realm = Realm.getInstance(getContext());
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(realmMovies);
                    realm.commitTransaction();
                    realm.close();

                    ActorAPI actorAPI = retrofit.create(ActorAPI.class);

                    for (Actor loopActor : movie.getActorList().getActors()) {
                        Call<Actor> actorCall = actorAPI.loadActorById(loopActor.getId());
                        actorCall.enqueue(new Callback<Actor>() {
                            @Override
                            public void onResponse(Response<Actor> response, Retrofit retrofit) {
                                Actor actor = response.body();
                                if (actor != null) {
                                    addActor(actor);
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void addActor(Actor actor) {
        int index = indexOf(actor, actors);

        if (index == -1) {
            this.actors.add(actor);
            List<RealmActor> realmActors = new ArrayList<>();
            for (Actor a : this.actors) {
                realmActors.add(new RealmActor(a));
            }

            Realm realm = Realm.getInstance(getContext());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(realmActors);
            realm.commitTransaction();
            realm.close();
        }
    }

    // Method that gets indes of element in list or returns -1 if there is no that element in list
    // Criteria is id
    private int indexOf(Movie element, List<Movie> list) {
        int index = -1;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == element.getId()) {
                index = i;
                break;
            }
        }

        return index;
    }

    private int indexOf(Actor element, List<Actor> list) {
        int index = -1;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == element.getId()) {
                index = i;
                break;
            }
        }

        return index;
    }
}
