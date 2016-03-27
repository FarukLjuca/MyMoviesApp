package com.atlantbh.mymoviesapp.fragments;

import android.content.Context;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.activities.ActorActivity;
import com.atlantbh.mymoviesapp.activities.LoginActivity;
import com.atlantbh.mymoviesapp.activities.VideoActivity;
import com.atlantbh.mymoviesapp.adapters.ActorAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.api.TvAPI;
import com.atlantbh.mymoviesapp.api.UserAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.Detailable;
import com.atlantbh.mymoviesapp.model.FavoritePost;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.Rating;
import com.atlantbh.mymoviesapp.model.RatingList;
import com.atlantbh.mymoviesapp.model.RatingValue;
import com.atlantbh.mymoviesapp.model.Tv;
import com.atlantbh.mymoviesapp.model.User;
import com.atlantbh.mymoviesapp.model.realm.RealmMovie;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieBasic;
import com.bumptech.glide.Glide;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailsFragment extends Fragment {
    private int movieId;
    private int tvId;
    private RatingList ratingList;
    private int counter = 1;
    private SwipeRefreshLayout refreshLayout;

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
    @Bind(R.id.ivDetailsBackdropBlur)
    ImageView backdropBlur;
    @Bind(R.id.rlDetailsVideo)
    RelativeLayout video;
    @Bind(R.id.vwDetailsVideoLine)
    View videoLine;
    @Bind(R.id.rvDetailsCast)
    RecyclerView cast;
    @Bind(R.id.rlDetailsRatingButton)
    RelativeLayout rateButton;
    @Bind(R.id.ivDetailsFavorite)
    ImageView favorite;
    @Bind(R.id.tvDetailsRating)
    TextView yourRating;

    public DetailsFragment() {
    }

    public void setMovieId(int movieId) {
        if (movieId > 0) {
            this.movieId = movieId;
        }
    }

    public void setTvId(int tvId) {
        if (tvId > 0) {
            this.tvId = tvId;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movieId = getArguments().getInt(AppString.MOVIE_ID);
            tvId = getArguments().getInt(AppString.TV_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRatingView();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (getResources().getDisplayMetrics().widthPixels * 160 / getResources().getDisplayMetrics().densityDpi < 900) {
            backdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (720.0 / 1280.0 * displaymetrics.widthPixels)));
        } else {
            backdrop.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ((720.0 / 1280.0 * displaymetrics.widthPixels) * 3 / 5)));
        }

        refreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.srDetailsRefresh);
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
        User user = User.getInstance();
        user.getFavorites();
        if (movieId > 0) {
            if (AppHelper.isOnline()) {
                Retrofit retrofit = AppHelper.getRetrofit();
                MovieAPI movieAPI = retrofit.create(MovieAPI.class);

                Call<Movie> call = movieAPI.loadMovieById(movieId);
                call.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Response<Movie> response, Retrofit retrofit) {
                        Detailable detailable = response.body();
                        if (detailable != null) {
                            SetContent(detailable);

                            Realm realm = Realm.getInstance(getContext());
                            realm.beginTransaction();
                            RealmMovie realmMovie = new RealmMovie((Movie) detailable);
                            realm.copyToRealmOrUpdate(realmMovie);
                            realm.commitTransaction();
                            realm.close();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Realm realm = Realm.getInstance(getContext());
                RealmResults<RealmMovie> realmResults = realm.where(RealmMovie.class).equalTo("id", movieId).findAll();
                if (realmResults.size() == 0) {
                    RealmResults<RealmMovieBasic> realmResultsBasic = realm.where(RealmMovieBasic.class).equalTo("id", movieId).findAll();
                    if (realmResultsBasic.size() > 0) {
                        SetContent(new Movie(realmResultsBasic.get(0)));
                    }
                } else {
                    Movie movie = new Movie(realmResults.get(0));
                    SetContent(movie);
                }
                realm.close();

                video.setVisibility(View.GONE);
                videoLine.setVisibility(View.GONE);
                basicText.setLines(10);
            }
        } else if (tvId > 0) {
            video.setVisibility(View.GONE);
            videoLine.setVisibility(View.GONE);
            basicText.setLines(10);

            Retrofit retrofit = AppHelper.getRetrofit();
            TvAPI tvAPI = retrofit.create(TvAPI.class);

            Call<Tv> call = tvAPI.loadTvById(tvId);
            call.enqueue(new Callback<Tv>() {
                @Override
                public void onResponse(Response<Tv> response, Retrofit retrofit) {
                    final Detailable detailable = response.body();
                    if (detailable != null) {
                        if (AppHelper.isOnline()) {
                            SetContent(detailable);
                        } else {
                            CoordinatorLayout coordinator = (CoordinatorLayout) getActivity().findViewById(R.id.clMovieCoordinator);
                            if (coordinator == null) {
                                coordinator = (CoordinatorLayout) getActivity().findViewById(R.id.clDetailsCoordinator);
                            }
                            Snackbar snackbar = Snackbar.make(coordinator, R.string.check_your_internet_connection, Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(Color.CYAN);
                            snackbar.setAction(R.string.refresh, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SetContent(detailable);
                                }
                            });
                            snackbar.show();
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        setRatingView();
    }

    private void SetContent(Detailable detailable) {
        Glide.with(getContext())
                .load("https://image.tmdb.org/t/p/w1280" + detailable.getBackdropPath())
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop);

        Glide.with(getContext())
                .load("https://image.tmdb.org/t/p/w1280" + detailable.getBackdropPath())
                .bitmapTransform(new BlurTransformation(getContext(), 25))
                .into(backdropBlur);

        if (User.isLoggedIn()) {
            User user = User.getInstance();
            boolean isFavorite = user.isFavorite(isMovie() ? movieId : tvId);
            if (!isFavorite) {
                favorite.setImageResource(R.drawable.ic_favorite_border_white_48dp);
            } else {
                favorite.setImageResource(R.drawable.ic_favorite_white_48dp);
            }
        }

        title.setText(detailable.getTitle());
        year.setText(detailable.getYear());
        subtitle.setText(detailable.getSubtitle());

        ImageView poster = (ImageView) getActivity().findViewById(R.id.ivDetailsPoster);
        Glide.with(getContext())
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

        rating.setRating(detailable.getVoteAverage() / 2);
        voteAverage.setText(String.valueOf(round(detailable.getVoteAverage(), 1)));
        voteCount.setText(Integer.toString(detailable.getVoteCount()));

        cast.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cast.setLayoutManager(layoutManager);
        RecyclerView.Adapter castAdapter = new ActorAdapter(getContext(), detailable.getActorList(), new ActorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Actor actor) {
                actorClick(actor);
            }
        });
        cast.setAdapter(castAdapter);

        refreshLayout.setRefreshing(false);
    }

    public void actorClick(final Actor actor) {
        if (AppHelper.isOnline()) {
            RelativeLayout detailsContainer = (RelativeLayout) getActivity().findViewById(R.id.rlDetailsContainer);
            if (detailsContainer != null) {
                ActorFragment fragment = new ActorFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(AppString.ACTOR_ID, actor.getId());
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.rlDetailsContainer, fragment, AppString.actorFragmentTag)
                        .commit();
            } else {
                Intent intent = new Intent(getContext(), ActorActivity.class);
                intent.putExtra(AppString.ACTOR_ID, actor.getId());
                startActivity(intent);
            }
        } else {
            CoordinatorLayout coordinatorLayout;
            if (getActivity().findViewById(R.id.clDetailsCoordinator) != null) {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clDetailsCoordinator);
            } else {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clMovieCoordinator);
            }
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Check your internet connection", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction(R.string.refresh, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actorClick(actor);
                }
            });
            snackbar.show();
        }
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    private void SetFonts() {
        title.setTypeface(FontHelper.getFont(getContext(), FontHelper.AVENIR_REGULAR));
        year.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        subtitle.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
    }

    public void favoriteClick() {
        if (AppHelper.isOnline()) {
            favorite();
        } else {
            CoordinatorLayout coordinatorLayout;
            if (getActivity().findViewById(R.id.clDetailsCoordinator) != null) {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clDetailsCoordinator);
            } else {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clMovieCoordinator);
            }
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.check_your_internet_connection, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.CYAN);
            snackbar.setAction(R.string.refresh, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favoriteClick();
                }
            });
            snackbar.show();
        }
    }

    private void favorite() {
        if (User.isLoggedIn()) {
            if (favorite != null) {
                final User user = User.getInstance();

                Retrofit retrofit = AppHelper.getRetrofit();
                UserAPI userAPI = retrofit.create(UserAPI.class);

                final boolean isFavorite = user.isFavorite(isMovie() ? movieId : tvId);
                FavoritePost body = new FavoritePost(isMovie() ? "movie" : "tv", isMovie() ? movieId : tvId, !isFavorite);

                Call<com.atlantbh.mymoviesapp.model.Response> call = userAPI.setFavorite(user.getId(), User.getSession().getSessionId(), body);
                call.enqueue(new Callback<com.atlantbh.mymoviesapp.model.Response>() {
                    @Override
                    public void onResponse(Response<com.atlantbh.mymoviesapp.model.Response> response, Retrofit retrofit) {
                        user.getFavorites();
                        if (isFavorite) {
                            favorite.setImageResource(R.drawable.ic_favorite_border_white_48dp);
                        } else {
                            favorite.setImageResource(R.drawable.ic_favorite_white_48dp);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        } else {
            CoordinatorLayout coordinatorLayout;
            if (getActivity().findViewById(R.id.clDetailsCoordinator) != null) {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clDetailsCoordinator);
            } else {
                coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.clMovieCoordinator);
            }
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.you_need_to_be_logged_in, Snackbar.LENGTH_LONG);
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

    public void videoClick() {
        Intent intent = new Intent(getContext(), VideoActivity.class);
        intent.putExtra(AppString.MOVIE_ID, movieId);
        startActivity(intent);
    }

    public void detailableInfoClick() {
        Layout l = basicText.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0)
                if (l.getEllipsisCount(lines - 1) > 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getString(R.string.detailedOverview))
                            .setMessage(basicText.getText())
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
        }
    }

    public void rateMovie(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rateView = inflater.inflate(R.layout.rate_view, null);
        builder.setTitle("Rate a movie")
                .setView(rateView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Retrofit retrofit = AppHelper.getRetrofit();
                        UserAPI userAPI = retrofit.create(UserAPI.class);

                        RatingBar ratingBar = (RatingBar) rateView.findViewById(R.id.rbRate);
                        final RatingValue ratingValue = new RatingValue();
                        ratingValue.setValue(ratingBar.getRating());

                        Call<com.atlantbh.mymoviesapp.model.Response> call = null;

                        if (movieId > 0) {
                            call = userAPI.setRatingMovie(movieId, User.getSession().getSessionId(), ratingValue);

                        } else if (tvId > 0) {
                            call = userAPI.setRatingMovie(tvId, User.getSession().getSessionId(), ratingValue);
                        }

                        if (call != null) {
                            call.enqueue(new Callback<com.atlantbh.mymoviesapp.model.Response>() {
                                @Override
                                public void onResponse(Response<com.atlantbh.mymoviesapp.model.Response> response, Retrofit retrofit) {
                                    yourRating.setText(String.valueOf(ratingValue.getValue()));
                                    yourRating.setTextSize(24);
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setRatingView() {
        Retrofit retrofit = AppHelper.getRetrofit();
        UserAPI userAPI = retrofit.create(UserAPI.class);

        if (movieId > 0) {
            Call<RatingList> call = userAPI.getRatedMovies(User.getSession().getSessionId(), 1);
            call.enqueue(new Callback<RatingList>() {
                @Override
                public void onResponse(Response<RatingList> response, Retrofit retrofit) {
                    if (response.body() != null) {
                        ratingList = response.body();
                        if (ratingList.getPage() < ratingList.getTotalPages()) {
                            for (int i = 2; i < ratingList.getTotalPages(); i++) {
                                UserAPI userAPI = retrofit.create(UserAPI.class);

                                Call<RatingList> call = userAPI.getRatedMovies(User.getSession().getSessionId(), i);
                                call.enqueue(new Callback<RatingList>() {
                                    @Override
                                    public void onResponse(Response<RatingList> response, Retrofit retrofit) {
                                        ratingList.getRatingList().addAll(response.body().getRatingList());
                                        counter++;
                                        if (counter == ratingList.getTotalPages()) {
                                            for (Rating rating : ratingList.getRatingList()) {
                                                if (rating.getId() == movieId) {
                                                    yourRating.setText(String.valueOf(rating.getRating()));
                                                    yourRating.setTextSize(24);
                                                    break;
                                                }
                                            }
                                            counter = 1;
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {

                                    }
                                });
                            }
                        } else {
                            for (Rating rating : ratingList.getRatingList()) {
                                if (rating.getId() == movieId) {
                                    yourRating.setText(String.valueOf(rating.getRating()));
                                    yourRating.setTextSize(24);
                                    break;
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
        else if (tvId > 0) {
            Call<RatingList> call = userAPI.getRatedTvs(User.getSession().getSessionId(), 1);
            call.enqueue(new Callback<RatingList>() {
                @Override
                public void onResponse(Response<RatingList> response, Retrofit retrofit) {
                    ratingList = response.body();
                    if (ratingList.getPage() < ratingList.getTotalPages()) {
                        for (int i = 2; i < ratingList.getTotalPages(); i++) {
                            UserAPI userAPI = retrofit.create(UserAPI.class);

                            Call<RatingList> call = userAPI.getRatedTvs(User.getSession().getSessionId(), i);
                            call.enqueue(new Callback<RatingList>() {
                                @Override
                                public void onResponse(Response<RatingList> response, Retrofit retrofit) {
                                    ratingList.getRatingList().addAll(response.body().getRatingList());
                                    counter++;
                                    if (counter == ratingList.getTotalPages()) {
                                        for (Rating rating : ratingList.getRatingList()) {
                                            if (rating.getId() == tvId) {
                                                yourRating.setText(String.valueOf(rating.getRating()));
                                                break;
                                            }
                                        }
                                        counter = 1;
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                        }
                    }
                    else {
                        for (Rating rating : ratingList.getRatingList()) {
                            if (rating.getId() == tvId) {
                                yourRating.setText(String.valueOf(rating.getRating()));
                                yourRating.setTextSize(24);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    private boolean isMovie() {
        return movieId > 0;
    }
}
