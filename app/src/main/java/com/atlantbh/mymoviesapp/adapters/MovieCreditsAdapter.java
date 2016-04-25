package com.atlantbh.mymoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.credits.Credits;
import com.atlantbh.mymoviesapp.model.credits.MovieCredits;
import com.atlantbh.mymoviesapp.model.realm.RealmMovieCredits;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieCreditsAdapter extends RecyclerView.Adapter<MovieCreditsAdapter.ViewHolder> {
    private Context context;
    private MovieCredits movieCredits;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView poster;
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.ivPoster);
            title = (TextView) view.findViewById(R.id.tvTitle);
        }

        public void bind(final Credits credits, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(credits);
                }
            });
        }
    }

    private Context getContext() { return context; }

    public MovieCreditsAdapter(Context context, MovieCredits movieCredits, OnItemClickListener listener) {
        this.context = context;
        this.movieCredits = movieCredits;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_list_card, parent, false);
        TextView title = (TextView) v.findViewById(R.id.tvTitle);
        title.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = movieCredits.getCreditsMovieCast().get(position).getTitle();
        if (movieCredits.getCreditsMovieCast().get(position).getReleaseDate() != null) {
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy");
            title += " (" + simpleDate.format(movieCredits.getCreditsMovieCast().get(position).getReleaseDate()) + ")";
        }
        holder.title.setText(title);
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + movieCredits.getCreditsMovieCast().get(position).getPosterPath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
                .into(holder.poster);
        holder.bind(movieCredits.getCreditsMovieCast().get(position), listener);
    }

    @Override
    public int getItemCount() {
        return movieCredits.getCreditsMovieCast().size();
    }

    public interface OnItemClickListener {
        void onItemClick(Credits credits);
    }
}
