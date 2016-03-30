package com.atlantbh.mymoviesapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atlantbh.mymoviesapp.R;
import com.bumptech.glide.Glide;

public class SimpleImageFragment extends Fragment {
    public SimpleImageFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_image, container, false);
        Glide.with(container.getContext())
                .load("http://blogs.psychcentral.com/relationships-balance/files/2013/02/movie_and_popcorn.jpg")
                .into((ImageView) view.findViewById(R.id.ivSimpleImage));
        return view;
    }
}
