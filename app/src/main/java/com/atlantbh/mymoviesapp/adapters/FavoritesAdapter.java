package com.atlantbh.mymoviesapp.adapters;

import android.content.Context;
import android.media.Rating;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.model.Detailable;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieFavorites;
import com.atlantbh.mymoviesapp.model.Tv;
import com.atlantbh.mymoviesapp.model.TvFavorites;
import com.atlantbh.mymoviesapp.model.User;
import com.bumptech.glide.Glide;

public class FavoritesAdapter extends BaseExpandableListAdapter {
    private Context context;
    private MovieFavorites movieFavorites;
    private TvFavorites tvFavorites;

    public FavoritesAdapter (Context context, MovieFavorites movieFavorites, TvFavorites tvFavorites) {
        this.context = context;
        this.movieFavorites = movieFavorites;
        this.tvFavorites = tvFavorites;
    }

    public void setMovieFavorites(MovieFavorites movieFavorites) {
        this.movieFavorites = movieFavorites;
    }

    public void setTvFavorites(TvFavorites tvFavorites) {
        this.tvFavorites = tvFavorites;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int result = 0;

        if (groupPosition == 0 && movieFavorites != null && movieFavorites.getMovieList() != null) {
            result = movieFavorites.getMovieList().size();
        }
        else if (groupPosition == 1 && tvFavorites != null && tvFavorites.getTvList() != null) {
            result = tvFavorites.getTvList().size();
        }

        return result;
    }

    @Override
    public Object getGroup(int groupPosition) {
        Object result = null;

        if (groupPosition == 0) {
            result = movieFavorites.getMovieList();
        }
        else if (groupPosition == 1) {
            result = tvFavorites.getTvList();
        }

        return result;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Object result = null;

        if (groupPosition == 0) {
            result = movieFavorites.getMovieList().get(childPosition);
        }
        else if (groupPosition == 1) {
            result = tvFavorites.getTvList().get(childPosition);
        }

        return result;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        long result = 0;

        if (groupPosition == 0 && childPosition < movieFavorites.getMovieList().size()) {
            result = movieFavorites.getMovieList().get(childPosition).getId();
        }
        else if (groupPosition == 1 && childPosition < tvFavorites.getTvList().size()) {
            result = tvFavorites.getTvList().get(childPosition).getId();
        }

        return result;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.favorite_group_card, parent, false);
        }

        TextView groupTitle = (TextView) view.findViewById(R.id.tvFavoriteGroupTitle);
        if (groupPosition == 0) {
            groupTitle.setText("   Movies   ");
        }
        else if (groupPosition == 1) {
            groupTitle.setText(" Tv Shows ");
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.favorite_card, parent, false);
        }

        Detailable detailable = null;
        if (groupPosition == 0 && childPosition < movieFavorites.getMovieList().size()) {
            detailable = movieFavorites.getMovieList().get(childPosition);
        }
        else if (groupPosition == 1 && childPosition < movieFavorites.getMovieList().size()) {
            detailable = tvFavorites.getTvList().get(childPosition);
        }

        if (detailable != null) {
            TextView title = (TextView) view.findViewById(R.id.tvFavoritesTitle);
            TextView basicText = (TextView) view.findViewById(R.id.tvFavoritesOverview);
            TextView ratingText = (TextView) view.findViewById(R.id.tvFavoritesRating);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rbFavoritesRating);
            ImageView poster = (ImageView) view.findViewById(R.id.ivFavoritesPoster);

            title.setText(detailable.getTitle());
            basicText.setText(detailable.getBasicText());
            ratingText.setText(String.valueOf(detailable.getVoteAverage()));
            ratingBar.setRating(detailable.getVoteAverage() / 2);
            Glide.with(getContext())
                    .load("https://image.tmdb.org/t/p/w342" + detailable.getPosterPath())
                    .placeholder(R.drawable.actor_placeholder_curved).into(poster);
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void refresh(SwipeRefreshLayout refreshLayout) {
        User user = User.getInstance();
        user.getFavorites(refreshLayout, this);
        notifyDataSetChanged();
    }
}
