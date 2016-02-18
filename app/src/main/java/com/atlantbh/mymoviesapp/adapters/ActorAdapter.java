package com.atlantbh.mymoviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.model.ActorList;
import com.atlantbh.mymoviesapp.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> {
    private Context context;
    private ActorList actors;

    public static class ActorViewHolder extends RecyclerView.ViewHolder {
        public ImageView actorImage;
        public TextView actorName;

        public ActorViewHolder(View v) {
            super(v);
            actorImage = (ImageView) v.findViewById(R.id.ivActorImage);
            actorName = (TextView) v.findViewById(R.id.tvActorName);
        }
    }

    public ActorAdapter(Context context, ActorList actors) {
        this.context = context;
        this.actors = actors;
    }

    @Override
    public ActorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_card, parent, false);
        return new ActorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ActorViewHolder holder, int position) {
        holder.actorName.setText(actors.get(position).getName());
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + actors.get(position).getProfilePath())
                .placeholder(R.drawable.actor_placeholder_curved)
                .transform(new RoundedCornersTransformation(10, 0))
                .into(holder.actorImage);
    }

    @Override
    public int getItemCount() {
        return actors.getActors().size();
    }
}
