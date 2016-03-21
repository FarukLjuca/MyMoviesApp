package com.atlantbh.mymoviesapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atlantbh.mymoviesapp.R;
import com.atlantbh.mymoviesapp.helpers.FontHelper;
import com.atlantbh.mymoviesapp.model.Review;
import com.atlantbh.mymoviesapp.model.ReviewList;

public class ReviewsAdapter extends BaseAdapter {
    private Context context;
    private ReviewList reviewList;

    public ReviewsAdapter (Context context, ReviewList reviewList) {
        this.reviewList = reviewList;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        if (reviewList != null && reviewList.getReviewList() != null) {
            return reviewList.getReviewList().size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return reviewList.getReviewList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.review_card, parent, false);
        }

        TextView author = (TextView) view.findViewById(R.id.tvReviewAuthor);
        TextView content = (TextView) view.findViewById(R.id.tvReviewContent);

        Review review = reviewList.getReviewList().get(position);
        author.setText(review.getAuthor());
        content.setText(review.getContent());

        author.setTypeface(FontHelper.getFont(getContext(), FontHelper.ROBOTO_MEDIUM));

        return view;
    }
}
