package com.atlantbh.mymoviesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.activities.MovieListActivity;
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
    private Activity activity;

    // Binding to views
    @Bind(R.id.ivMoviePoster)
    ImageView imageView;
    @Bind(R.id.rbMovieRating)
    RatingBar ratingBar;
    @Bind(R.id.tvMovieRating)
    TextView ratingText;
    @Bind(R.id.tvTitle)
    TextView title;
    @Bind(R.id.tvOverview)
    TextView overview;

    public Context getContext() {
        return context;
    }

    public MovieList getMovieList() {
        return movieList;
    }

    public MovieAdapter(Activity activity, MovieList movieList) {
        this.movieList = movieList;
        this.activity = activity;
        this.context = activity;
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
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.movie_card, parent, false);
        }
        else {
            view = convertView;
        }
        ButterKnife.bind(this, view);

        Movie currentMovie = getMovieList().getMovies().get(position);

        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w342" + currentMovie.getPosterPath())
                .into(imageView);
        ratingBar.setRating(currentMovie.getVoteAverage() / 2);
        ratingText.setText(Float.toString(currentMovie.getVoteAverage()));
        title.setText(currentMovie.getOriginalTitle());
        overview.setText(currentMovie.getOverview());

        return view;
    }

    public void addItems(MovieList movieList) {
        this.movieList.getMovies().addAll(movieList.getMovies());
        notifyDataSetChanged();
    }
}
