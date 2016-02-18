package com.atlantbh.mymoviesapp.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.atlantbh.mymoviesapp.R;

public abstract class MovieListBaseFragment extends Fragment {
    private String category;
    private View view;

    public MovieListBaseFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_list_base, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.test);
            category = getCategory();
            textView.setText(category);
        }
        else {
            Toast.makeText(getActivity(), "view is null", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract String getCategory();
}
