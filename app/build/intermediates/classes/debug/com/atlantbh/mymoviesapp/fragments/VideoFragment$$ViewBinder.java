// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class VideoFragment$$ViewBinder<T extends com.atlantbh.mymoviesapp.fragments.VideoFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624102, "field 'ratingBar'");
    target.ratingBar = finder.castView(view, 2131624102, "field 'ratingBar'");
    view = finder.findRequiredView(source, 2131624103, "field 'rating'");
    target.rating = finder.castView(view, 2131624103, "field 'rating'");
    view = finder.findRequiredView(source, 2131624104, "field 'rateMovie'");
    target.rateMovie = finder.castView(view, 2131624104, "field 'rateMovie'");
    view = finder.findRequiredView(source, 2131624106, "field 'title'");
    target.title = finder.castView(view, 2131624106, "field 'title'");
    view = finder.findRequiredView(source, 2131624107, "field 'basicText'");
    target.basicText = finder.castView(view, 2131624107, "field 'basicText'");
    view = finder.findRequiredView(source, 2131624110, "field 'actors'");
    target.actors = finder.castView(view, 2131624110, "field 'actors'");
    view = finder.findRequiredView(source, 2131624112, "field 'reviews'");
    target.reviews = finder.castView(view, 2131624112, "field 'reviews'");
  }

  @Override public void unbind(T target) {
    target.ratingBar = null;
    target.rating = null;
    target.rateMovie = null;
    target.title = null;
    target.basicText = null;
    target.actors = null;
    target.reviews = null;
  }
}
