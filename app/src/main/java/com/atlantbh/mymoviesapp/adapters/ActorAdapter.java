package com.atlantbh.mymoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Actor;
import com.atlantbh.mymoviesapp.model.ActorList;
import com.atlantbh.mymoviesapp.R;
import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {
    private Context context;
    private ActorList actors;
    private OnItemClickListener listener;

    private Context getContext() { return context; }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView actorImage;
        public TextView actorName;

        public ViewHolder(View view) {
            super(view);
            actorImage = (ImageView) view.findViewById(R.id.ivPoster);
            actorName = (TextView) view.findViewById(R.id.tvTitle);
        }

        public void bind(final Actor actor, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(actor);
                }
            });
        }
    }

    public ActorAdapter(Context context, ActorList actors, OnItemClickListener listener) {
        this.context = context;
        this.actors = actors;
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
        holder.actorName.setText(actors.get(position).getName());
        Glide.with(getContext())
                .load("https://image.tmdb.org/t/p/w185/" + actors.get(position).getProfilePath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 10, 0))
                .into(holder.actorImage);
        holder.bind(actors.get(position), listener);
    }

    @Override
    public int getItemCount() {
        int result = 0;
        if (actors != null && actors.getActors() != null) {
            result = actors.getActors().size();
        }
        return result;
    }

    public interface OnItemClickListener {
        void onItemClick(Actor actor);
    }
}
