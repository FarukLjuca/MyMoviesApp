// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MovieDetailsActivity$$ViewBinder<T extends com.atlantbh.mymoviesapp.activities.MovieDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492971, "field 'backdrop'");
    target.backdrop = finder.castView(view, 2131492971, "field 'backdrop'");
  }

  @Override public void unbind(T target) {
    target.backdrop = null;
  }
}
