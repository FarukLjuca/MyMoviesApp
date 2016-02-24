package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.adapters.MovieAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public abstract class MoviesFragment extends Fragment {
    private Context currentContext;
    private View currentView;

    private int index = 0;
    private int top = 0;
    private boolean isListView = true;

    private MovieAdapter movieAdapter;

    private String category;

    GridView gridView;
    ListView listView;

    public MoviesFragment() {}

    public abstract String getCategory();

    @Override
    public void onPause() {
        /*
        if(listView != null) {
            int index = listView.getFirstVisiblePosition();
            View v = listView.getChildAt(0);
            int top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());
        }
        else if (gridView != null) {
            int index = gridView.getFirstVisiblePosition();
            View v = gridView.getChildAt(0);
            int top = (v == null) ? 0 : (v.getTop() - gridView.getPaddingTop());
        }
        */

        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentContext = container.getContext();
        currentView = inflater.inflate(R.layout.fragment_movie_list_base, container, false);
        category = getCategory();
        listView = (ListView) currentView.findViewById(R.id.lvContainer);
        gridView = (GridView) currentView.findViewById(R.id.gvContainer);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<MovieList> call = movieAPI.loadMovieByCategory(category);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                final MovieList movieList = response.body();
                if (listView != null) {
                    movieAdapter = new MovieAdapter(getActivity(), movieList);
                    listView.setAdapter(movieAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(currentContext, MovieDetailsActivity.class);
                            Movie movie = movieList.getMovies().get(position);
                            intent.putExtra("movieId", movie.getId());
                            startActivity(intent);
                        }
                    });

                    listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                                movieList.LoadData(movieAdapter, category);
                                movieList.setIsLoading(false);
                            }
                        }
                    });

                    //listView.setSelectionFromTop(index, top);
                } else if (gridView != null) {
                    movieAdapter = new MovieAdapter(getActivity(), movieList);
                    gridView.setAdapter(movieAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(currentContext, MovieDetailsActivity.class);
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
                                movieList.LoadData(movieAdapter, category);
                                movieList.setIsLoading(false);
                            }
                        }
                    });

                    //gridView.setSelectionFromTop(index, top);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(currentContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return currentView;
    }
}
