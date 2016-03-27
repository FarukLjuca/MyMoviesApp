package com.atlantbh.mymoviesapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.activities.ActorActivity;
import com.atlantbh.mymoviesapp.activities.DetailsActivity;
import com.atlantbh.mymoviesapp.adapters.MovieCreditsAdapter;
import com.atlantbh.mymoviesapp.adapters.TvCreditsAdapter;
import com.atlantbh.mymoviesapp.api.ActorAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.credits.Credits;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ActorFragment extends Fragment {
    @Bind(R.id.ivActorBackdrop)
    ImageView actorBackdrop;
    @Bind(R.id.ivActorPoster)
    ImageView actorPoster;
    @Bind(R.id.tvTitle)
    TextView actorName;
    @Bind(R.id.tvActorSubtitle)
    TextView actorSubtitle;
    @Bind(R.id.tvActorBiography)
    TextView actorBiography;
    @Bind(R.id.rvActorMovies)
    RecyclerView actorMovies;
    @Bind(R.id.rvActorTv)
    RecyclerView actorTv;
    @Bind(R.id.ivActorInfo)
    ImageView actorInfo;

    private int actorId;
    private SwipeRefreshLayout refreshLayout;

    public void setActorId(int actorId) {
        if (actorId > 0) {
            this.actorId = actorId;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            actorId = getArguments().getInt(AppString.ACTOR_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actor, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (getResources().getDisplayMetrics().widthPixels * 160 / getResources().getDisplayMetrics().densityDpi < 900) {
            actorBackdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (720.0 / 1280.0 * displaymetrics.widthPixels)));
        }
        else {
            actorBackdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ((720.0 / 1280.0 * displaymetrics.widthPixels) * 3/5)));
        }

        refreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.srActorRefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        refresh();

        SetFonts();
    }

    private void refresh() {
        if (actorId > 0) {
            Retrofit retrofit = AppHelper.getRetrofit();
            ActorAPI actorAPI = retrofit.create(ActorAPI.class);

            Call<Actor> call = actorAPI.loadActorById(actorId);
            call.enqueue(new Callback<Actor>() {
                @Override
                public void onResponse(Response<Actor> response, Retrofit retrofit) {
                    Actor actor = response.body();

                    if (actor.getImageList().getImages().size() > 0) {
                        Glide.with(getContext())
                                .load("https://image.tmdb.org/t/p/w1280" + actor.getImageList().getImages().get(0).getMovie().getBackdropPath())
                                .placeholder(R.drawable.backdrop_placeholder)
                                .into(actorBackdrop);
                    }
                    else {
                        Glide.with(getContext())
                                .load(R.drawable.backdrop_placeholder)
                                .into(actorBackdrop);
                    }

                    Glide.with(getContext())
                            .load("https://image.tmdb.org/t/p/w500" + actor.getProfilePath())
                            .placeholder(R.drawable.actor_placeholder_curved)
                            .into(actorPoster);

                    actorName.setText(actor.getName());
                    actorSubtitle.setText(actor.getFrequentJobs());
                    if (actor.getBiography() != null) {
                        actorBiography.setText(actor.getBiography().replace("\n", " "));
                    }
                    actorBiography.post(new Runnable() {
                        @Override
                        public void run() {
                            Layout l = actorBiography.getLayout();
                            if (l != null) {
                                int lines = l.getLineCount();
                                if (lines > 0)
                                    if (l.getEllipsisCount(lines-1) == 0) {
                                        actorInfo.setColorFilter(R.color.lightgray);
                                    }
                            }
                        }
                    });

                    actorMovies.setHasFixedSize(true);
                    RecyclerView.LayoutManager movieCreditsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    actorMovies.setLayoutManager(movieCreditsLayoutManager);
                    RecyclerView.Adapter movieCreditsAdapter = new MovieCreditsAdapter(getContext(), actor.getMovieCredits(), new MovieCreditsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Credits credits) {
                            detailableClick(credits.getId(), -1);
                        }
                    });
                    actorMovies.setAdapter(movieCreditsAdapter);

                    actorTv.setHasFixedSize(true);
                    RecyclerView.LayoutManager tvCreditsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    actorTv.setLayoutManager(tvCreditsLayoutManager);
                    RecyclerView.Adapter tvCreditsAdapter = new TvCreditsAdapter(getContext(), actor.getTvCredits(), new TvCreditsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Credits credits) {
                            detailableClick(-1, credits.getId());
                        }
                    });
                    actorTv.setAdapter(tvCreditsAdapter);
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        refreshLayout.setRefreshing(false);
    }

    private void SetFonts() {
        actorName.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        actorSubtitle.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        actorBiography.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
    }

    public void actorInfoClick() {
        Layout l = actorBiography.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getString(R.string.detaildedBiography))
                            .setMessage(actorBiography.getText())
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
        }
    }

    public void detailableClick(final int movieId, final int tvId) {
        if (AppHelper.isOnline()) {
            RelativeLayout detailsContainer = (RelativeLayout) getActivity().findViewById(R.id.rlDetailsContainer);
            if (detailsContainer != null) {
                DetailsFragment fragment = new DetailsFragment();
                Bundle bundle = new Bundle();
                if (movieId != -1) {
                    bundle.putInt(AppString.MOVIE_ID, movieId);
                }
                else if (tvId != -1) {
                    bundle.putInt(AppString.TV_ID, tvId);
                }
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.rlDetailsContainer, fragment, AppString.detailsFragmentTag)
                        .commit();
            } else {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                if (movieId != -1) {
                    intent.putExtra(AppString.MOVIE_ID, movieId);
                }
                else if (tvId != -1) {
                    intent.putExtra(AppString.TV_ID, tvId);
                }
                startActivity(intent);
            }
        }
        else {
            CoordinatorLayout coordinatorLayout;
            if (getActivity().findViewById(R.id.clDetailsCoordinator) != null) {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clDetailsCoordinator);
            }
            else {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clMovieCoordinator);
            }
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction(R.string.refresh, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailableClick(movieId, tvId);
                }
            });
            snackbar.show();
        }
    }
}
