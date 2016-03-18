package com.atlantbh.mymoviesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.fragments.ActorFragment;
import com.atlantbh.mymoviesapp.helpers.AppString;

public class ActorActivity extends AppCompatActivity implements ActorFragment.OnFragmentInteractionListener {
    private int actorId;
    private Toolbar toolbar;
    private TextView tvActorBiography;
    private String actorBiography;

    private Context getContext() {
        return ActorActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        toolbar = (Toolbar) findViewById(R.id.tbActorToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(false);
        }

        actorId = getIntent().getIntExtra(AppString.ACTOR_ID, -1);

        ActorFragment actorFragment = (ActorFragment) getSupportFragmentManager().findFragmentById(R.id.frActorDetailsContent);
        if (actorFragment != null) {
            actorFragment.setActorId(actorId);
        }
    }

    public void Info_Click(View view) {
        Layout l = tvActorBiography.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Detailed biography")
                            .setMessage(actorBiography)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
        }
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

    public void setTvActorBiography(TextView tvActorBiography) {
        this.tvActorBiography = tvActorBiography;
    }

    public void setActorBiography(String actorBiography) {
        this.actorBiography = actorBiography;
    }
}
