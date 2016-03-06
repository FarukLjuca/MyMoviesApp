// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MovieDetailsActivity$$ViewBinder<T extends com.atlantbh.mymoviesapp.activities.MovieDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492980, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131492980, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131492982, "field 'backdrop'");
    target.backdrop = finder.castView(view, 2131492982, "field 'backdrop'");
    view = finder.findRequiredView(source, 2131492986, "field 'title'");
    target.title = finder.castView(view, 2131492986, "field 'title'");
    view = finder.findRequiredView(source, 2131492987, "field 'releaseYear'");
    target.releaseYear = finder.castView(view, 2131492987, "field 'releaseYear'");
    view = finder.findRequiredView(source, 2131492988, "field 'runtimeAndGenres'");
    target.runtimeAndGenres = finder.castView(view, 2131492988, "field 'runtimeAndGenres'");
    view = finder.findRequiredView(source, 2131492992, "field 'overview'");
    target.overview = finder.castView(view, 2131492992, "field 'overview'");
    view = finder.findRequiredView(source, 2131492991, "field 'info'");
    target.info = finder.castView(view, 2131492991, "field 'info'");
    view = finder.findRequiredView(source, 2131492999, "field 'detailsRating'");
    target.detailsRating = finder.castView(view, 2131492999, "field 'detailsRating'");
    view = finder.findRequiredView(source, 2131493000, "field 'voteAverage'");
    target.voteAverage = finder.castView(view, 2131493000, "field 'voteAverage'");
    view = finder.findRequiredView(source, 2131493001, "field 'voteCount'");
    target.voteCount = finder.castView(view, 2131493001, "field 'voteCount'");
  }

  @Override public void unbind(T target) {
    target.toolbar = null;
    target.backdrop = null;
    target.title = null;
    target.releaseYear = null;
    target.runtimeAndGenres = null;
    target.overview = null;
    target.info = null;
    target.detailsRating = null;
    target.voteAverage = null;
    target.voteCount = null;
  }
}
