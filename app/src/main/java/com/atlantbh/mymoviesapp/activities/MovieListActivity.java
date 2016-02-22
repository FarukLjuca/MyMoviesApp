package com.atlantbh.mymoviesapp.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.adapters.MovieListPagerAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.adapters.MovieAdapter;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;
import com.atlantbh.mymoviesapp.R;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieListActivity extends AppCompatActivity {
    private MovieAdapter movieAdapter;
    private MovieListPagerAdapter pagerAdapter;
    private ViewPager vpMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        pagerAdapter = new MovieListPagerAdapter(getSupportFragmentManager());
        vpMovieList = (ViewPager) findViewById(R.id.vpMovieList);
        vpMovieList.setAdapter(pagerAdapter);
        TabLayout tlMovieTabs = (TabLayout) findViewById(R.id.tlMovieTabs);
        tlMovieTabs.setupWithViewPager(vpMovieList);

        /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<MovieList> call = movieAPI.loadMovies();
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Response<MovieList> response, Retrofit retrofit) {
                final MovieList movieList = response.body();
                if (findViewById(R.id.lvContainer) != null) {
                    final ListView listView = (ListView) findViewById(R.id.lvContainer);
                    movieAdapter = new MovieAdapter(getApplicationContext(), movieList);
                    listView.setAdapter(movieAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
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
                            if (l >= totalItemCount && !isLoading) {
                                // It is time to add new data. We call the listener
                                isLoading = true;
                                loadData();
                                isLoading = false;
                            }
                        }
                    });
                }
                else if (findViewById(R.id.gvContainer) != null) {
                    final GridView listView = (GridView) findViewById(R.id.gvContainer);
                    movieAdapter = new MovieAdapter(getApplicationContext(), movieList);
                    listView.setAdapter(movieAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
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
                            if (l >= totalItemCount && !isLoading) {
                                // It is time to add new data. We call the listener
                                isLoading = true;
                                loadData();
                                isLoading = false;
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast t;
        switch (item.getItemId()) {
            case R.id.action_settings:
                t = Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT);
                t.show();
                return true;

            case R.id.itSearch:
                t = Toast.makeText(this, R.string.search, Toast.LENGTH_SHORT);
                t.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
