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
import com.atlantbh.mymoviesapp.model.credits.TvCredits;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class TvCreditsAdapter extends RecyclerView.Adapter<TvCreditsAdapter.ViewHolder> {
    private Context context;
    private TvCredits tvCredits;
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

    public TvCreditsAdapter(Context context, TvCredits tvCredits, OnItemClickListener listener) {
        this.context = context;
        this.tvCredits = tvCredits;
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
        String title = tvCredits.getCreditsTvCast().get(position).getName();
        if (tvCredits.getCreditsTvCast().get(position).getFirstAirDate() != null) {
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy");
            title += " (" + simpleDate.format(tvCredits.getCreditsTvCast().get(position).getFirstAirDate()) + ")";
        }
        holder.title.setText(title);
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + tvCredits.getCreditsTvCast().get(position).getPosterPath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .transform(new RoundedCornersTransformation(10, 0))
                .into(holder.poster);
        holder.bind(tvCredits.getCreditsTvCast().get(position), listener);
    }

    @Override
    public int getItemCount() {
        return tvCredits.getCreditsTvCast().size();
    }

    public interface OnItemClickListener {
        void onItemClick(Credits credits);
    }
}
