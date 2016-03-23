// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailsFragment$$ViewBinder<T extends com.atlantbh.mymoviesapp.fragments.DetailsFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558562, "field 'backdrop'");
    target.backdrop = finder.castView(view, 2131558562, "field 'backdrop'");
    view = finder.findRequiredView(source, 2131558566, "field 'title'");
    target.title = finder.castView(view, 2131558566, "field 'title'");
    view = finder.findRequiredView(source, 2131558567, "field 'year'");
    target.year = finder.castView(view, 2131558567, "field 'year'");
    view = finder.findRequiredView(source, 2131558568, "field 'subtitle'");
    target.subtitle = finder.castView(view, 2131558568, "field 'subtitle'");
    view = finder.findRequiredView(source, 2131558572, "field 'basicText'");
    target.basicText = finder.castView(view, 2131558572, "field 'basicText'");
    view = finder.findRequiredView(source, 2131558571, "field 'info'");
    target.info = finder.castView(view, 2131558571, "field 'info'");
    view = finder.findRequiredView(source, 2131558580, "field 'rating'");
    target.rating = finder.castView(view, 2131558580, "field 'rating'");
    view = finder.findRequiredView(source, 2131558581, "field 'voteAverage'");
    target.voteAverage = finder.castView(view, 2131558581, "field 'voteAverage'");
    view = finder.findRequiredView(source, 2131558582, "field 'voteCount'");
    target.voteCount = finder.castView(view, 2131558582, "field 'voteCount'");
    view = finder.findRequiredView(source, 2131558563, "field 'backdropBlur'");
    target.backdropBlur = finder.castView(view, 2131558563, "field 'backdropBlur'");
    view = finder.findRequiredView(source, 2131558573, "field 'video'");
    target.video = finder.castView(view, 2131558573, "field 'video'");
    view = finder.findRequiredView(source, 2131558576, "field 'videoLine'");
    target.videoLine = view;
    view = finder.findRequiredView(source, 2131558587, "field 'cast'");
    target.cast = finder.castView(view, 2131558587, "field 'cast'");
    view = finder.findRequiredView(source, 2131558583, "field 'rateButton'");
    target.rateButton = finder.castView(view, 2131558583, "field 'rateButton'");
    view = finder.findRequiredView(source, 2131558564, "field 'favorite'");
    target.favorite = finder.castView(view, 2131558564, "field 'favorite'");
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
    target.rateButton = null;
    target.favorite = null;
  }
}
