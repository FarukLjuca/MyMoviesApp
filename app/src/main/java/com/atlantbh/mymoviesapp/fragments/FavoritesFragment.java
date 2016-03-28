package com.atlantbh.mymoviesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.activities.DetailsActivity;
import com.atlantbh.mymoviesapp.activities.VideoActivity;
import com.atlantbh.mymoviesapp.adapters.FavoritesAdapter;
import com.atlantbh.mymoviesapp.api.MovieAPI;
import com.atlantbh.mymoviesapp.api.TvAPI;
import com.atlantbh.mymoviesapp.api.UserAPI;
import com.atlantbh.mymoviesapp.helpers.AppHelper;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieFavorites;
import com.atlantbh.mymoviesapp.model.Tv;
import com.atlantbh.mymoviesapp.model.TvFavorites;
import com.atlantbh.mymoviesapp.model.User;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FavoritesFragment extends Fragment {
    private ExpandableListView listView;
    private Context context;
    private SwipeRefreshLayout refreshLayout;
    private FavoritesAdapter favoritesAdapter;

    public FavoritesFragment() {}

    public Context getContext() {
        return context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.srFavoritesRefresh);
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (favoritesAdapter != null) {
                        favoritesAdapter.refresh(refreshLayout);
                    }
                }
            });
            refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }

        setAdapter();
    }

    private void setAdapter() {
        User user = User. getInstance();
        final MovieFavorites movieFavorites = user.getMovieFavorites();
        final TvFavorites tvFavorites = user.getTvFavorites();

        favoritesAdapter = new FavoritesAdapter(getContext(), movieFavorites, tvFavorites);
        listView = (ExpandableListView) getActivity().findViewById(R.id.lvFavoritesContainer);
        listView.setAdapter(favoritesAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                RelativeLayout detailsContainer = (RelativeLayout) getActivity().findViewById(R.id.rlDetailsContainer);

                int movieId = 0;
                int tvId = 0;

                if (groupPosition == 0) {
                    movieId = movieFavorites.getMovieList().get(childPosition).getId();
                } else if (groupPosition == 1) {
                    tvId = tvFavorites.getTvList().get(childPosition).getId();
                }

                if (detailsContainer != null) {
                    DetailsFragment fragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppString.MOVIE_ID, movieId);
                    bundle.putInt(AppString.TV_ID, tvId);
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.rlDetailsContainer, fragment, AppString.detailsFragmentTag)
                            .commit();
                } else {
                    Intent intent = new Intent(getContext(), DetailsActivity.class);
                    intent.putExtra(AppString.MOVIE_ID, movieId);
                    intent.putExtra(AppString.TV_ID, tvId);
                    startActivity(intent);
                }

                return true;
            }
        });
    }
}
