package com.atlantbh.mymoviesapp.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

public class ReviewsListView extends ListView {
    public ReviewsListView(Context context) {
        super(context);
    }
    public ReviewsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ReviewsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
    }
}
