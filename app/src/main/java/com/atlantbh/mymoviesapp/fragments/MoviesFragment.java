package com.atlantbh.mymoviesapp.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.atlantbh.mymoviesapp.activities.VideoActivity;
import com.atlantbh.mymoviesapp.adapters.MovieAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieBasic;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public abstract class MoviesFragment extends Fragment {
    public static final int CATEGORY_POPULAR = 0;
    public static final int CATEGORY_NOW_PLAYING = 1;
    public static final int CATEGORY_TOP_RATED = 2;

    private static List<RealmMovieBasic> optimizedMovieList = new ArrayList<>();

    private static int pass = 0;

    private Context currentContext;
    private View currentView;

    private MovieAdapter movieAdapter;

    protected GridView gridView;
    protected ListView listView;
    protected RelativeLayout detailsContainer;

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

        return currentView;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.srMoviesRefresh);
        refresh(savedInstanceState, refreshLayout);
    }

    public void refresh(final Bundle savedInstanceState, final SwipeRefreshLayout refreshLayout) {
        if (AppHelper.isOnline()) {
            Retrofit retrofit = AppHelper.getRetrofit();
            final MovieAPI movieAPI = retrofit.create(MovieAPI.class);

            Call<MovieList> call = movieAPI.loadMoviesByPage(getCategoryString(), 1);
            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                    final MovieList movieList = response.body();

                    for (int i = 0; i < movieList.getMovies().size(); i++) {
                        int index = -1;
                        for (int j = 0; j < optimizedMovieList.size(); j++) {
                            if (optimizedMovieList.get(j).getId() == movieList.getMovies().get(i).getId()) {
                                index = i;
                                break;
                            }
                        }

                        if (index == -1) {
                            RealmMovieBasic realmMovie = new RealmMovieBasic(movieList.getMovies().get(i));
                            realmMovie.setCategory(getCategory());

                            switch (getCategory()) {
                                case Movie.POPULAR:
                                    realmMovie.setIndexPopular(i);
                                    break;
                                case Movie.NOW_PAYING:
                                    realmMovie.setIndexNowPlaying(i);
                                    break;
                                case Movie.TOP_RATED:
                                    realmMovie.setIndexTopRated(i);
                                    break;
                            }

                            optimizedMovieList.add(realmMovie);
                        } else {
                            optimizedMovieList.get(index).setCategory(Movie.mergeCategories(optimizedMovieList.get(index).getCategory(), getCategory()));
                            switch (getCategory()) {
                                case Movie.POPULAR:
                                    optimizedMovieList.get(index).setIndexPopular(i);
                                    break;
                                case Movie.NOW_PAYING:
                                    optimizedMovieList.get(index).setIndexNowPlaying(i);
                                    break;
                                case Movie.TOP_RATED:
                                    optimizedMovieList.get(index).setIndexTopRated(i);
                                    break;
                            }
                        }
                    }

                    pass++;

                    setAdapterViews(savedInstanceState, movieList, refreshLayout);

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
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(currentContext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Realm realm = Realm.getInstance(getActivity());

            RealmResults<RealmMovieBasic> movieList = null;

            if (getCategory() == CATEGORY_POPULAR)
                movieList = realm.where(RealmMovieBasic.class).notEqualTo("indexPopular", -1).findAllSorted("indexPopular");
            else if (getCategory() == CATEGORY_NOW_PLAYING)
                movieList = realm.where(RealmMovieBasic.class).notEqualTo("indexNowPlaying", -1).findAllSorted("indexNowPlaying");
            else if (getCategory() == CATEGORY_TOP_RATED)
                movieList = realm.where(RealmMovieBasic.class).notEqualTo("indexTopRated", -1).findAllSorted("indexTopRated");
            realm.close();

            setAdapterViews(savedInstanceState, new MovieList(movieList), refreshLayout);

            if (refreshLayout != null) {
                refreshLayout.setRefreshing(false);
            }
        }

        if (listView != null) {
            listView.setSelection(0);
        }
        else if (gridView != null) {
            gridView.setSelection(0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (listView != null) {
            savedInstanceState.putInt("index", listView.getLastVisiblePosition() - 1);
        } else if (gridView != null) {
            savedInstanceState.putInt("index", gridView.getLastVisiblePosition() - 3);
        }
    }

    private void setAdapterViews(Bundle savedInstanceState, final MovieList movieList, final SwipeRefreshLayout refreshLayout) {
        if (listView != null) {
            movieAdapter = new MovieAdapter(getActivity(), movieList);
            listView.setAdapter(movieAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    detailsContainer = (RelativeLayout) getActivity().findViewById(R.id.rlDetailsContainer);
                    Movie movie = movieList.getMovies().get(position);
                    if (detailsContainer != null) {
                        DetailsFragment fragment = new DetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(AppString.MOVIE_ID, movie.getId());
                        fragment.setArguments(bundle);

                        // get Support Fragment Manager
                        android.support.v4.app.FragmentManager manager = getFragmentManager();
                        if (manager != null) {
                            // get size of BackStack
                            int backStackSize = manager.getBackStackEntryCount();

                            // if size is 0, no fragments have been added, add fragment regularly
                            if (backStackSize == 0) {
                                manager.beginTransaction()
                                        .addToBackStack(String.valueOf(movie.getId()))
                                        .replace(R.id.rlDetailsContainer, fragment, AppString.detailsFragmentTag)
                                        .commit();
                            }
                            // if size is greater than 0, check if last BackStack Record name is same as current name,
                            // if it is, do nothing, if it is not, add fragment regularly
                            else if (backStackSize > 0) {
                                android.support.v4.app.FragmentManager.BackStackEntry backStackLastRecord = manager.getBackStackEntryAt(backStackSize - 1);
                                if (backStackLastRecord != null) {
                                    String backStackName = backStackLastRecord.getName();
                                    String currentName = String.valueOf(movie.getId());
                                    if (!backStackName.equals(currentName)) {
                                        manager.beginTransaction()
                                                .addToBackStack(String.valueOf(movie.getId()))
                                                .replace(R.id.rlDetailsContainer, fragment, AppString.detailsFragmentTag)
                                                .commit();
                                    }
                                }
                            }
                        }
                    } else {
                        Intent intent = new Intent(currentContext, DetailsActivity.class);
                        intent.putExtra(AppString.MOVIE_ID, movie.getId());
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
                    onMovieScroll(firstVisibleItem, visibleItemCount, totalItemCount, movieList);
                    boolean enable = false;
                    if (listView != null && listView.getChildCount() > 0) {
                        // check if the first item of the list is visible
                        boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                        // check if the top of the first item is visible
                        boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                        // enabling or disabling the refresh layout
                        enable = firstItemVisible && topOfFirstItemVisible && detailsContainer == null;
                    }
                    if (refreshLayout != null) {
                        refreshLayout.setEnabled(enable);
                    }
                }
            });

            if (savedInstanceState != null) {
                int position = savedInstanceState.getInt("index", 0);
                if (position != 0) {
                    movieList.LoadDataToPage(movieAdapter, getCategoryString(), position / 20, listView, position);
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
                    intent.putExtra(AppString.MOVIE_ID, movie.getId());
                    startActivity(intent);
                }
            });

            gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (gridView.getAdapter() != null && gridView.getAdapter().getCount() > 0) {
                        int l = visibleItemCount + firstVisibleItem;
                        if (l >= totalItemCount && !movieList.getIsLoading()) {
                            movieList.setIsLoading(true);
                            movieList.LoadData(getContext(), movieAdapter, getCategoryString());
                        }
                    }

                    boolean enable = false;
                    if (gridView != null && gridView.getChildCount() > 0) {
                        // check if the first item of the list is visible
                        boolean firstItemVisible = gridView.getFirstVisiblePosition() == 0;
                        // check if the top of the first item is visible
                        boolean topOfFirstItemVisible = gridView.getChildAt(0).getTop() == 0;
                        // enabling or disabling the refresh layout
                        enable = firstItemVisible && topOfFirstItemVisible && detailsContainer == null;
                    }
                    refreshLayout.setEnabled(enable);
                }
            });

            if (savedInstanceState != null) {
                int position = savedInstanceState.getInt("index", 0);
                if (position != 0) {
                    movieList.LoadDataToPage(movieAdapter, getCategoryString(), position / 20, gridView, position);
                }
            }
        }

        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
    }

    private void onMovieScroll(final int firstVisibleItem, final int visibleItemCount, final int totalItemCount, final MovieList movieList) {
        if (listView.getAdapter() == null)
            return;

        if (listView.getAdapter().getCount() == 0)
            return;

        int l = visibleItemCount + firstVisibleItem;
        if (l >= totalItemCount && !movieList.getIsLoading()) {
            if (AppHelper.isOnline()) {
                movieList.setIsLoading(true);
                movieList.LoadData(getContext(), movieAdapter, getCategoryString());
            } else {
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.clMovieCoordinator), R.string.check_your_internet_connection, Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.CYAN);
                snackbar.setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onMovieScroll(firstVisibleItem, visibleItemCount, totalItemCount, movieList);
                    }
                });
                snackbar.show();
            }

        }

    }
}
