package com.atlantbh.mymoviesapp.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.atlantbh.mymoviesapp.adapters.MoviePagerAdapter;
import com.atlantbh.mymoviesapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity {
    private MoviePagerAdapter pagerAdapter;

    @Bind(R.id.vpMovieList)
    ViewPager viewPager;
    @Bind(R.id.tlMovieTabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        pagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
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
            case R.id.action_settings:
                return true;

            case R.id.itSearch:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
