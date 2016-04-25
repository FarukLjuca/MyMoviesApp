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
import com.atlantbh.mymoviesapp.model.realm.RealmTvCredits;
import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RealmTvCreditsAdapter extends RecyclerView.Adapter<RealmTvCreditsAdapter.ViewHolder> {
    private Context context;
    private List<RealmTvCredits> realmTvCredits;
    private OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView poster;
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.ivPosterTv);
            title = (TextView) view.findViewById(R.id.tvTitleTv);
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

    public RealmTvCreditsAdapter(Context context, List<RealmTvCredits> realmTvCredits, OnItemClickListener listener) {
        this.context = context;
        this.realmTvCredits = realmTvCredits;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_list_card_tv, parent, false);
        TextView title = (TextView) v.findViewById(R.id.tvTitleTv);
        title.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(realmTvCredits.get(position).getTitle());
        Glide.with(getContext())
                .load("https://image.tmdb.org/t/p/w185/" + realmTvCredits.get(position).getPosterPath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
                .into(holder.poster);
        //holder.bind(tvCredits.getCreditsTvCast().get(position), listener);
    }

    @Override
    public int getItemCount() {
        return realmTvCredits.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Credits credits);
    }
}
