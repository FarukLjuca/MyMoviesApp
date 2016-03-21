// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailsFragment$$ViewBinder<T extends com.atlantbh.mymoviesapp.fragments.DetailsFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558554, "field 'backdrop'");
    target.backdrop = finder.castView(view, 2131558554, "field 'backdrop'");
    view = finder.findRequiredView(source, 2131558558, "field 'title'");
    target.title = finder.castView(view, 2131558558, "field 'title'");
    view = finder.findRequiredView(source, 2131558559, "field 'year'");
    target.year = finder.castView(view, 2131558559, "field 'year'");
    view = finder.findRequiredView(source, 2131558560, "field 'subtitle'");
    target.subtitle = finder.castView(view, 2131558560, "field 'subtitle'");
    view = finder.findRequiredView(source, 2131558564, "field 'basicText'");
    target.basicText = finder.castView(view, 2131558564, "field 'basicText'");
    view = finder.findRequiredView(source, 2131558563, "field 'info'");
    target.info = finder.castView(view, 2131558563, "field 'info'");
    view = finder.findRequiredView(source, 2131558572, "field 'rating'");
    target.rating = finder.castView(view, 2131558572, "field 'rating'");
    view = finder.findRequiredView(source, 2131558573, "field 'voteAverage'");
    target.voteAverage = finder.castView(view, 2131558573, "field 'voteAverage'");
    view = finder.findRequiredView(source, 2131558574, "field 'voteCount'");
    target.voteCount = finder.castView(view, 2131558574, "field 'voteCount'");
    view = finder.findRequiredView(source, 2131558555, "field 'backdropBlur'");
    target.backdropBlur = finder.castView(view, 2131558555, "field 'backdropBlur'");
    view = finder.findRequiredView(source, 2131558565, "field 'video'");
    target.video = finder.castView(view, 2131558565, "field 'video'");
    view = finder.findRequiredView(source, 2131558568, "field 'videoLine'");
    target.videoLine = view;
    view = finder.findRequiredView(source, 2131558578, "field 'cast'");
    target.cast = finder.castView(view, 2131558578, "field 'cast'");
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
    target.video = null;
    target.videoLine = null;
    target.cast = null;
  }
}
