package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.adapters.MovieAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public abstract class MovieListBaseFragment extends Fragment {
    private Context context;
    private MovieList movieList;
    private MovieAdapter movieAdapter;
    private String category;
    private View view;

    public MovieListBaseFragment () {
        movieList = new MovieList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_movie_list_base, container, false);
        category = getCategory();

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
                if (view.findViewById(R.id.lvContainer) != null) {
                    final ListView listView = (ListView) view.findViewById(R.id.lvContainer);
                    movieAdapter = new MovieAdapter(context, movieList);
                    listView.setAdapter(movieAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(context, MovieDetailsActivity.class);
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
                                // It is time to add new data. We call the listener
                                movieList.setIsLoading(true);
                                movieList.LoadData(movieAdapter, category);
                                movieList.setIsLoading(false);
                            }
                        }
                    });
                } else if (view.findViewById(R.id.gvContainer) != null) {
                    final GridView listView = (GridView) view.findViewById(R.id.gvContainer);
                    movieAdapter = new MovieAdapter(context, movieList);
                    listView.setAdapter(movieAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(context, MovieDetailsActivity.class);
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
                                // It is time to add new data. We call the listener
                                movieList.setIsLoading(true);
                                movieList.LoadData(movieAdapter, category);
                                movieList.setIsLoading(false);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public abstract String getCategory();
    public abstract MovieListBaseFragment newInstance();
}
