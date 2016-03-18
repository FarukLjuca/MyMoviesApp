package com.atlantbh.mymoviesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.credits.Credits;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
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
    TextView actorSubtitile;
    @Bind(R.id.tvActorBiography)
    TextView actorBiography;
    @Bind(R.id.rvActorMovies)
    RecyclerView actorMovies;
    @Bind(R.id.rvActorTv)
    RecyclerView actorTv;
    @Bind(R.id.ivActorInfo)
    ImageView actorInfo;

    private int actorId;
    private OnFragmentInteractionListener mListener;

    public void setActorId(int actorId) {
        if (actorId != -1) {
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
        return inflater.inflate(R.layout.fragment_actor_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        actorBackdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (720.0 / 1280.0 * displaymetrics.widthPixels)));

        if (actorId == -1) {
            Toast.makeText(getContext(), "Actor Id nije poslan, dakle bundle nije poslan", Toast.LENGTH_SHORT).show();
        } else {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            ActorAPI actorAPI = retrofit.create(ActorAPI.class);

            Call<Actor> call = actorAPI.loadActorById(actorId);
            call.enqueue(new Callback<Actor>() {
                @Override
                public void onResponse(Response<Actor> response, Retrofit retrofit) {
                    Actor actor = response.body();

                    if (actor.getImageList().getImages().size() > 0) {
                        Glide.with(getContext())
                                .load("https://image.tmdb.org/t/p/w1280" + actor.getImageList().getImages().get(0).getMovie().getBackdropPath())
                                .into(actorBackdrop);
                    }
                    else {
                        Glide.with(getContext())
                                .load(R.drawable.actor_placeholder_curved)
                                .into(actorBackdrop);
                    }

                    Glide.with(getContext())
                            .load("https://image.tmdb.org/t/p/w500" + actor.getProfilePath())
                            .placeholder(R.drawable.actor_placeholder_curved)
                            .into(actorPoster);

                    actorName.setText(actor.getName());
                    actorSubtitile.setText(actor.getFrequentJobs());
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

                    if (getActivity() instanceof ActorActivity) {
                        ((ActorActivity) getActivity()).setActorBiography(actor.getBiography());
                        ((ActorActivity) getActivity()).setTvActorBiography(actorBiography);
                    }

                    actorMovies.setHasFixedSize(true);
                    RecyclerView.LayoutManager movieCreditsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    actorMovies.setLayoutManager(movieCreditsLayoutManager);
                    RecyclerView.Adapter movieCreditsAdapter = new MovieCreditsAdapter(getContext(), actor.getMovieCredits(), new MovieCreditsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Credits credits) {
                            Intent intent = new Intent(getContext(), DetailsActivity.class);
                            intent.putExtra(AppString.MOVIE_ID, credits.getId());
                            startActivity(intent);
                        }
                    });
                    actorMovies.setAdapter(movieCreditsAdapter);

                    actorTv.setHasFixedSize(true);
                    RecyclerView.LayoutManager tvCreditsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    actorTv.setLayoutManager(tvCreditsLayoutManager);
                    RecyclerView.Adapter tvCreditsAdapter = new TvCreditsAdapter(getContext(), actor.getTvCredits(), new TvCreditsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Credits credits) {
                            Intent intent = new Intent(getContext(), DetailsActivity.class);
                            intent.putExtra(AppString.TV_ID, credits.getId());
                            startActivity(intent);
                        }
                    });
                    actorTv.setAdapter(tvCreditsAdapter);
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

            SetFonts();
        }
    }

    private void SetFonts() {
        actorName.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        actorSubtitile.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        actorBiography.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

    }
}
