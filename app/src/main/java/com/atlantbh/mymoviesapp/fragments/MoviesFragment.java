package com.atlantbh.mymoviesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.activities.DetailsActivity;
import com.atlantbh.mymoviesapp.adapters.MovieAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;
import com.atlantbh.mymoviesapp.model.realm.RealmMovie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public abstract class MoviesFragment extends Fragment {
    public static final int CATEGORY_POPULAR = 0;
    public static final int CATEGORY_NOW_PLAYING = 1;
    public static final int CATEGORY_TOP_RATED = 2;

    private static List<RealmMovie> optimizedMovieList;

    private static int pass = 0;

    private Context currentContext;
    private View currentView;

    private MovieAdapter movieAdapter;

    private int category;

    GridView gridView;
    ListView listView;
    RelativeLayout detailsContainer;

    public MoviesFragment() {
    }

    public abstract int getCategory();

    public String getCategoryString() {
        String result = "";

        switch (getCategory()) {
            case CATEGORY_POPULAR:
                result = "popular";
                break;
            case CATEGORY_NOW_PLAYING:
                result = "now_playing";
                break;
            case CATEGORY_TOP_RATED:
                result = "top_rated";
                break;
        }

        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentContext = container.getContext();
        currentView = inflater.inflate(R.layout.fragment_movie_list_base, container, false);
        listView = (ListView) currentView.findViewById(R.id.lvContainer);
        gridView = (GridView) currentView.findViewById(R.id.gvContainer);
        detailsContainer = (RelativeLayout) currentView.findViewById(R.id.rlDetailsContainer);

        return currentView;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) currentContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        category = getCategory();

        if (isOnline()) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            MovieAPI movieAPI = retrofit.create(MovieAPI.class);

            Call<MovieList> call = movieAPI.loadMoviesByPage(getCategoryString(), 1);
            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                    final MovieList movieList = response.body();
                    optimizedMovieList = new ArrayList<>();

                    for (Movie movie : movieList.getMovies()) {
                        int index = -1;
                        for (int i = 0; i < optimizedMovieList.size(); i++) {
                            if (optimizedMovieList.get(i).getId() == movie.getId()) {
                                index = i;
                                break;
                            }
                        }

                        if (index == -1) {
                            RealmMovie realmMovie = new RealmMovie(movie);
                            realmMovie.setCategory(getCategory());
                            optimizedMovieList.add(realmMovie);
                        } else {
                            optimizedMovieList.get(index).setCategory(Movie.mergeCategories(optimizedMovieList.get(index).getCategory(), getCategory()));
                        }
                    }

                    pass++;

                    setAdapterViews(savedInstanceState, movieList);

                    if (pass == 3) {
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Realm realm = Realm.getInstance(getContext());

                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(optimizedMovieList);
                                realm.commitTransaction();
                                realm.close();

                                Log.v("IsMainThread", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                            }
                        };
                        Thread thread = new Thread(runnable);

                        thread.start();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(currentContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Realm realm = Realm.getInstance(getContext());

            MovieList movieList = null;
            if (optimizedMovieList == null) {
                RealmResults<RealmMovie> realmResult = realm.where(RealmMovie.class).findAll();
                optimizedMovieList = new ArrayList<>(realmResult);
            }

            if (getCategory() == CATEGORY_POPULAR) {
                movieList = new MovieList();
                for (RealmMovie movie : optimizedMovieList) {
                    if (Movie.isPopular(movie.getCategory())) {
                        movieList.addMovie(new Movie(movie));
                    }
                }
            } else if (getCategory() == CATEGORY_NOW_PLAYING) {
                movieList = new MovieList();
                for (RealmMovie movie : optimizedMovieList) {
                    if (Movie.isPopular(movie.getCategory())) {
                        movieList.addMovie(new Movie(movie));
                    }
                }
            } else if (getCategory() == CATEGORY_TOP_RATED) {
                movieList = new MovieList();
                for (RealmMovie movie : optimizedMovieList) {
                    if (Movie.isPopular(movie.getCategory())) {
                        movieList.addMovie(new Movie(movie));
                    }
                }
            }

            setAdapterViews(savedInstanceState, movieList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (listView != null) {
            int index = listView.getLastVisiblePosition() - 1;
            View v = listView.getChildAt(0);
            int top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());
            savedInstanceState.putInt("index", index);
            savedInstanceState.putInt("top", top);
        } else if (gridView != null) {
            int index = gridView.getLastVisiblePosition() - 3;
            View v = gridView.getChildAt(0);
            int top = (v == null) ? 0 : (v.getTop() - gridView.getPaddingTop());
            savedInstanceState.putInt("index", index);
            savedInstanceState.putInt("top", top);
        }
    }

    private void setAdapterViews(Bundle savedInstanceState, final MovieList movieList) {
        if (listView != null) {
            movieAdapter = new MovieAdapter(getActivity(), movieList);
            listView.setAdapter(movieAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (detailsContainer != null) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(DetailsFragment.MOVIE_ID, (int) id);
                        DetailsFragment fragment = new DetailsFragment();
                        fragment.setArguments(arguments);
                        getFragmentManager().beginTransaction()
                                .add(R.id.rlDetailsContainer, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(currentContext, DetailsActivity.class);
                        Movie movie = movieList.getMovies().get(position);
                        intent.putExtra("movieId", movie.getId());
                        startActivity(intent);
                    }
                }
            });

            listView.setOnScrollListener(new ListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (listView.getAdapter() == null)
                        return;

                    if (listView.getAdapter().getCount() == 0)
                        return;

                    int l = visibleItemCount + firstVisibleItem;
                    if (l >= totalItemCount && !movieList.getIsLoading()) {
                        movieList.setIsLoading(true);
                        movieList.LoadData(movieAdapter, getCategoryString());
                        movieList.setIsLoading(false);
                    }
                }
            });

            if (savedInstanceState != null) {
                int position = savedInstanceState.getInt("index", 0);
                if (position != 0) {
                    movieList.LoadDataToPage(movieAdapter, getCategoryString(), position / 20 + 1, listView, position);
                }
            }

        } else if (gridView != null) {
            movieAdapter = new MovieAdapter(getActivity(), movieList);
            gridView.setAdapter(movieAdapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(currentContext, DetailsActivity.class);
                    Movie movie = movieList.getMovies().get(position);
                    intent.putExtra("movieId", movie.getId());
                    startActivity(intent);
                }
            });

            gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (gridView.getAdapter() == null)
                        return;

                    if (gridView.getAdapter().getCount() == 0)
                        return;

                    int l = visibleItemCount + firstVisibleItem;
                    if (l >= totalItemCount && !movieList.getIsLoading()) {
                        movieList.setIsLoading(true);
                        movieList.LoadData(movieAdapter, getCategoryString());
                        movieList.setIsLoading(false);
                    }
                }
            });

            if (savedInstanceState != null) {
                int position = savedInstanceState.getInt("index", 0);
                if (position != 0) {
                    movieList.LoadDataToPage(movieAdapter, getCategoryString(), position / 20 + 1, gridView, position);
                }
            }
        }
    }
}
