package com.atlantbh.mymoviesapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.activities.ActorDetailsActivity;
import com.atlantbh.mymoviesapp.activities.DetailsActivity;
import com.atlantbh.mymoviesapp.activities.MovieVideoActivity;
import com.atlantbh.mymoviesapp.adapters.ActorAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.api.TvAPI;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.Detailable;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.Tv;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailsFragment extends Fragment {
    public static final String MOVIE_ID = "movieId";
    public static final String TV_ID = "tvId";

    private static int movieId;
    private static int tvId;

    @Bind(R.id.ivDetailsBackdrop)
    ImageView backdrop;
    @Bind(R.id.tvDetailsTitle)
    TextView title;
    @Bind(R.id.tvDetailsYear)
    TextView year;
    @Bind(R.id.tvDetailsSubtitle)
    TextView subtitle;
    @Bind(R.id.tvDetailsBasicText)
    TextView basicText;
    @Bind(R.id.ivDetailsInfo)
    ImageView info;
    @Bind(R.id.rbDetailsRating)
    RatingBar rating;
    @Bind(R.id.tvDetailsVoteAverage)
    TextView voteAverage;
    @Bind(R.id.tvDetailsVoteCount)
    TextView voteCount;

    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {}

    public static DetailsFragment newInstance(int movieId, int tvId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movieId);
        args.putInt(TV_ID, tvId);
        fragment.setArguments(args);
        return fragment;
    }

    public static void setMovieId(int mId) { movieId = mId; }
    public static void setTvId(int tId) { tvId = tId; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getView());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        backdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (720.0 / 1280.0 * displaymetrics.widthPixels)));

        if (movieId == -1 && tvId == -1) {
            Toast.makeText(getContext(), "Movie ili Tv Id nije poslan, dakle bundle nije poslan", Toast.LENGTH_SHORT).show();
        } else if (movieId != -1) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            MovieAPI movieAPI = retrofit.create(MovieAPI.class);

            Call<Movie> call = movieAPI.loadMovieById(movieId);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Response<Movie> response, Retrofit retrofit) {
                    Detailable detailable = response.body();
                    SetContent(detailable);
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else if (tvId != -1) {
            getActivity().findViewById(R.id.rlDetailsVideo).setVisibility(View.GONE);
            getActivity().findViewById(R.id.vwDetailsVideoLine).setVisibility(View.GONE);
            basicText.setLines(10);

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            TvAPI tvAPI = retrofit.create(TvAPI.class);

            Call<Tv> call = tvAPI.loadTvById(tvId);
            call.enqueue(new Callback<Tv>() {
                @Override
                public void onResponse(Response<Tv> response, Retrofit retrofit) {
                    Detailable detailable = response.body();
                    SetContent(detailable);
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        SetFonts();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }

    private void SetContent(Detailable detailable) {
        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w1280" + detailable.getBackdropPath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .into(backdrop);

        List<Transformation> transformations = new ArrayList<>();
        transformations.add(new BlurTransformation(getContext(), 25));
        ImageView backdropBlur = (ImageView) getActivity().findViewById(R.id.ivDetailsBackdropBlur);
        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w1280" + detailable.getBackdropPath())
                .transform(transformations)
                .into(backdropBlur);

        title.setText(detailable.getTitle());
        year.setText(detailable.getYear());
        subtitle.setText(detailable.getSubtitle());

        ImageView poster = (ImageView) getActivity().findViewById(R.id.ivDetailsPoster);
        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w500" + detailable.getPosterPath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .into(poster);

        basicText.setText(detailable.getBasicText());
        basicText.post(new Runnable() {
            @Override
            public void run() {
                Layout l = basicText.getLayout();
                if (l != null) {
                    int lines = l.getLineCount();
                    if (lines > 0)
                        if (l.getEllipsisCount(lines - 1) == 0) {
                            info.setColorFilter(R.color.lightgray);
                        }
                }
            }
        });
        ((DetailsActivity) getActivity()).setTvOverview(basicText);
        ((DetailsActivity) getActivity()).setOverview(detailable.getBasicText());

        rating.setRating(detailable.getVoteAverage() / 2);
        voteAverage.setText(Float.toString(detailable.getVoteAverage()));
        voteCount.setText(detailable.getVoteCount());

        RecyclerView cast = (RecyclerView) getActivity().findViewById(R.id.rvDetailsCast);
        cast.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cast.setLayoutManager(layoutManager);
        RecyclerView.Adapter castAdapter = new ActorAdapter(getContext(), detailable.getActorList(), new ActorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Actor actor) {
                Intent intent = new Intent(getContext(), ActorDetailsActivity.class);
                intent.putExtra("actorId", actor.getId());
                startActivity(intent);
            }
        });
        cast.setAdapter(castAdapter);
    }

    private void SetFonts() {
        title.setTypeface(FontHelper.getFont(getContext(), FontHelper.AVENIR_REGULAR));
        year.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        subtitle.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
    }
}
