package com.atlantbh.mymoviesapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.adapters.MoviePagerAdapter;
import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.fragments.ActorFragment;
import com.atlantbh.mymoviesapp.fragments.DetailsFragment;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.MovieList;
import com.atlantbh.mymoviesapp.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.vpMovieList)
    ViewPager viewPager;
    @Bind(R.id.tlMovieTabs)
    TabLayout tabLayout;
    @Bind(R.id.tbMovieToolbar)
    Toolbar toolbar;
    @Bind(R.id.dlMovieDrawer)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private MoviePagerAdapter pagerAdapter;
    private DetailsFragment detailsFragment;
    private ActorFragment actorFragment;

    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = User.getInstance();
        user.getUserFromDatabase();

        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                if (navigationView != null) {
                    Menu menu = navigationView.getMenu();
                    MenuItem nav_logout = menu.findItem(R.id.nav_login);
                    if (User.isLoggedIn()) {
                        nav_logout.setTitle("Logout");
                    }
                    else {
                        nav_logout.setTitle("Login");
                    }
                }
            }
        };
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        pagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.itSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            loginClick();
        }
        else if (id == R.id.nav_favorites) {
            goToFavorites();
        }

        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void goToFavorites() {
        if (User.isLoggedIn() || !AppHelper.isOnline()) {
            Intent intent = new Intent(MovieListActivity.this, FavoritesActivity.class);
            startActivity(intent);
        }
        else {
            CoordinatorLayout coordinator = (CoordinatorLayout) findViewById(R.id.clMovieCoordinator);
            if (coordinator != null) {
                Snackbar snackbar = Snackbar.make(coordinator, R.string.you_need_to_be_logged_in, Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(Color.CYAN);
                snackbar.setAction(R.string.login, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
                snackbar.show();
            }
        }
    }

    private void loginClick() {
        if (!User.isLoggedIn()) {
            if (AppHelper.isOnline()) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            } else {
                CoordinatorLayout coordinator = (CoordinatorLayout) findViewById(R.id.clMovieCoordinator);
                if (coordinator != null) {
                    Snackbar snackbar = Snackbar.make(coordinator, R.string.check_your_internet_connection, Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.CYAN);
                    snackbar.setAction(R.string.refresh, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loginClick();
                        }
                    });
                    snackbar.show();
                }
            }
        }
        else {
            User.getInstance().logout();
        }
    }

    public void videoClick(View view) {
        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentByTag(AppString.detailsFragmentTag);
        if (detailsFragment != null) {
            detailsFragment.videoClick();
        }
    }

    public void detailableInfoClick(View view) {
        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentByTag(AppString.detailsFragmentTag);
        if (detailsFragment != null) {
            detailsFragment.detailableInfoClick();
        }
    }

    public void favoriteClick(View view) {
        detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentByTag(AppString.detailsFragmentTag);
        if (detailsFragment != null) {
            detailsFragment.favoriteClick();
        }
    }

    public void actorInfoClick(View view) {
        actorFragment = (ActorFragment) getSupportFragmentManager().findFragmentByTag(AppString.actorFragmentTag);
        if (actorFragment != null) {
            actorFragment.actorInfoClick();
        }
    }
}
