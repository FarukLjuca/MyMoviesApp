// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailsFragment$$ViewBinder<T extends com.atlantbh.mymoviesapp.fragments.DetailsFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624075, "field 'backdrop'");
    target.backdrop = finder.castView(view, 2131624075, "field 'backdrop'");
    view = finder.findRequiredView(source, 2131624079, "field 'title'");
    target.title = finder.castView(view, 2131624079, "field 'title'");
    view = finder.findRequiredView(source, 2131624080, "field 'year'");
    target.year = finder.castView(view, 2131624080, "field 'year'");
    view = finder.findRequiredView(source, 2131624081, "field 'subtitle'");
    target.subtitle = finder.castView(view, 2131624081, "field 'subtitle'");
    view = finder.findRequiredView(source, 2131624085, "field 'basicText'");
    target.basicText = finder.castView(view, 2131624085, "field 'basicText'");
    view = finder.findRequiredView(source, 2131624084, "field 'info'");
    target.info = finder.castView(view, 2131624084, "field 'info'");
    view = finder.findRequiredView(source, 2131624093, "field 'rating'");
    target.rating = finder.castView(view, 2131624093, "field 'rating'");
    view = finder.findRequiredView(source, 2131624094, "field 'voteAverage'");
    target.voteAverage = finder.castView(view, 2131624094, "field 'voteAverage'");
    view = finder.findRequiredView(source, 2131624095, "field 'voteCount'");
    target.voteCount = finder.castView(view, 2131624095, "field 'voteCount'");
    view = finder.findRequiredView(source, 2131624076, "field 'backdropBlur'");
    target.backdropBlur = finder.castView(view, 2131624076, "field 'backdropBlur'");
  }

  @Override public void unbind(T target) {
    target.backdrop = null;
    target.title = null;
    target.year = null;
    target.subtitle = null;
    target.basicText = null;
    target.info = null;
    target.rating = null;
    target.voteAverage = null;
    target.voteCount = null;
    target.backdropBlur = null;
  }
}