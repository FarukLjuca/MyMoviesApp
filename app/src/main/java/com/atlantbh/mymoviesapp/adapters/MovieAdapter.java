package com.atlantbh.mymoviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.model.Movie;
import com.atlantbh.mymoviesapp.model.MovieList;
import com.atlantbh.mymoviesapp.R;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends BaseAdapter {
    private Context _context;
    private MovieList _movieList;

    public Context getContext() {
        return _context;
    }

    public MovieList getMovieList() {
        return _movieList;
    }

    public MovieAdapter(Context context, MovieList movieList) {
        _movieList = movieList;
        _context = context;
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

        Movie currentMovie = getMovieList().getMovies().get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.ivImage);
        Picasso.with(getContext())
                .load("https://image.tmdb.org/t/p/w185" + currentMovie.getPosterPath())
                .into(imageView);

        TextView title = (TextView) view.findViewById(R.id.tvTitle);
        title.setText(currentMovie.getOriginalTitle());

        TextView overview = (TextView) view.findViewById(R.id.tvOverview);
        overview.setText(currentMovie.getOverview());

        return view;
    }

    public void addItems(MovieList movieList) {
        _movieList.getMovies().addAll(movieList.getMovies());
        notifyDataSetChanged();
    }
}
