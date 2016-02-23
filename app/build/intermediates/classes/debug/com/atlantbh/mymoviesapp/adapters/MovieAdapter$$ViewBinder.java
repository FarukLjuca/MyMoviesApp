// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MovieAdapter$$ViewBinder<T extends com.atlantbh.mymoviesapp.adapters.MovieAdapter> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493009, "field 'imageView'");
    target.imageView = finder.castView(view, 2131493009, "field 'imageView'");
    view = finder.findRequiredView(source, 2131493010, "field 'ratingBar'");
    target.ratingBar = finder.castView(view, 2131493010, "field 'ratingBar'");
    view = finder.findRequiredView(source, 2131493011, "field 'ratingText'");
    target.ratingText = finder.castView(view, 2131493011, "field 'ratingText'");
    view = finder.findRequiredView(source, 2131493012, "field 'title'");
    target.title = finder.castView(view, 2131493012, "field 'title'");
    view = finder.findRequiredView(source, 2131493013, "field 'overview'");
    target.overview = finder.castView(view, 2131493013, "field 'overview'");
  }

  @Override public void unbind(T target) {
    target.imageView = null;
    target.ratingBar = null;
    target.ratingText = null;
    target.title = null;
    target.overview = null;
  }
}
