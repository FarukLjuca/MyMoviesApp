package com.atlantbh.mymoviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.helpers.AppString;
import com.atlantbh.mymoviesapp.model.Search;
import com.bumptech.glide.Glide;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<Search> searchList;

    public SearchAdapter (Context context, List<Search> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        if (searchList != null) {
            return searchList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return searchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return searchList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_card, parent, false);
        }

        Search search = searchList.get(position);
        TextView text = (TextView) view.findViewById(R.id.tvSearchText);
        TextView mediaType = (TextView) view.findViewById(R.id.tvSearchMediaType);
        ImageView imageView = (ImageView) view.findViewById(R.id.ivSearchImage);

        switch (search.getMediaType()) {
            case AppString.MOVIE:
                text.setText(search.getTitle());
                mediaType.setText("(Movie)");
                Glide.with(getContext())
                        .load("https://image.tmdb.org/t/p/w342" + search.getPosterPath())
                        .placeholder(R.drawable.actor_placeholder_curved)
                        .into(imageView);
                break;
            case AppString.TV:
                text.setText(search.getName());
                mediaType.setText("(Tv Show)");
                Glide.with(getContext())
                        .load("https://image.tmdb.org/t/p/w342" + search.getPosterPath())
                        .placeholder(R.drawable.actor_placeholder_curved)
                        .into(imageView);
                break;
            case AppString.PERSON:
                text.setText(search.getName());
                mediaType.setText("(Actor)");
                Glide.with(getContext())
                        .load("https://image.tmdb.org/t/p/w342" + search.getProfilePath())
                        .placeholder(R.drawable.actor_placeholder_curved)
                        .into(imageView);
                break;
        }

        return view;
    }
}
