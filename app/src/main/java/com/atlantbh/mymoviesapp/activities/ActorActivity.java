package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.fragments.ActorFragment;
import com.atlantbh.mymoviesapp.helpers.AppString;

import butterknife.Bind;

public class ActorActivity extends AppCompatActivity {
    private int actorId;
    private ActorFragment actorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tbActorToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        actorId = getIntent().getIntExtra(AppString.ACTOR_ID, -1);

        actorFragment = (ActorFragment) getSupportFragmentManager().findFragmentById(R.id.frActorDetailsContent);
        if (actorFragment != null) {
            actorFragment.setActorId(actorId);
        }
    }

    public void actorInfoClick(View view) {
        actorFragment.actorInfoClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
