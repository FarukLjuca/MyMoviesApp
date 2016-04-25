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
import com.atlantbh.mymoviesapp.model.realm.RealmMovieCredits;
import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RealmMovieCreditsAdapter extends RecyclerView.Adapter<RealmMovieCreditsAdapter.ViewHolder> {
    private Context context;
    private List<RealmMovieCredits> realmMovieCredits;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView poster;
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.ivPoster);
            title = (TextView) view.findViewById(R.id.tvTitle);
        }

        public void bind(final int id, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(id);
                }
            });
        }
    }

    private Context getContext() { return context; }

    public RealmMovieCreditsAdapter(Context context, List<RealmMovieCredits> realmMovieCredits, OnItemClickListener listener) {
        this.context = context;
        this.realmMovieCredits = realmMovieCredits;
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
        holder.title.setText(realmMovieCredits.get(position).getTitle());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + realmMovieCredits.get(position).getPosterPath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
                .into(holder.poster);
        holder.bind(realmMovieCredits.get(position).getId(), listener);
    }

    @Override
    public int getItemCount() {
        return realmMovieCredits.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }
}
