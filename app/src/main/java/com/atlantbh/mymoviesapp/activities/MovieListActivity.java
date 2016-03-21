package com.atlantbh.mymoviesapp.activities;

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

import com.atlantbh.mymoviesapp.adapters.MoviePagerAdapter;
import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.fragments.ActorFragment;
import com.atlantbh.mymoviesapp.fragments.DetailsFragment;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;

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

    private MoviePagerAdapter pagerAdapter;
    private DetailsFragment detailsFragment;
    private ActorFragment actorFragment;

    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itSearch:
                return true;

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

        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void loginClick() {
        if (AppHelper.isOnline()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        else {
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
