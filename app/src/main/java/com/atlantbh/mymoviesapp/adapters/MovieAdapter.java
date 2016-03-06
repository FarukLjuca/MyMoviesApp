package com.atlantbh.mymoviesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.activities.MovieListActivity;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;
import com.atlantbh.mymoviesapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private MovieList movieList;


    public Context getContext() {
        return context;
    }

    public MovieList getMovieList() {
        return movieList;
    }

    public void setMovieList(MovieList movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public MovieAdapter(Context context, MovieList movieList) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return getMovieList().getMovies().size();
    }

    @Override
    public Object getItem(int position) {
        return getMovieList().getMovies().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.movie_card, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.ivMoviePoster);
            holder.overview = (TextView) view.findViewById(R.id.tvOverview);
            holder.ratingBar = (RatingBar) view.findViewById(R.id.rbMovieRating);
            holder.ratingText = (TextView) view.findViewById(R.id.tvMovieRating);
            holder.title = (TextView) view.findViewById(R.id.tvTitle);
            view.setTag(holder);
            SetFonts(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Movie currentMovie = getMovieList().getMovies().get(position);

        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w342" + currentMovie.getPosterPath())
                .placeholder(R.drawable.actor_placeholder_curved).into(holder.imageView);
        holder.ratingBar.setRating(currentMovie.getVoteAverage() / 2);
        holder.ratingText.setText(String.format("%.1f", currentMovie.getVoteAverage()));
        holder.title.setText(currentMovie.getOriginalTitle());
        holder.overview.setText(currentMovie.getOverview());

        return view;
    }

    public void addItems(MovieList movieList) {
        this.movieList.getMovies().addAll(movieList.getMovies());
        notifyDataSetChanged();
    }

    private void SetFonts(ViewHolder holder) {
        holder.ratingText.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
        holder.title.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        holder.overview.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_REGULAR));
    }

    class ViewHolder{
        ImageView imageView;
        RatingBar ratingBar;
        TextView ratingText;
        TextView title;
        TextView overview;
    }
}
