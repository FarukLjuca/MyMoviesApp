// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActorDetailsActivity$$ViewBinder<T extends com.atlantbh.mymoviesapp.activities.ActorDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492969, "field 'mainToolbar'");
    target.mainToolbar = finder.castView(view, 2131492969, "field 'mainToolbar'");
    view = finder.findRequiredView(source, 2131492970, "field 'actorBackdrop'");
    target.actorBackdrop = finder.castView(view, 2131492970, "field 'actorBackdrop'");
    view = finder.findRequiredView(source, 2131492972, "field 'actorPoster'");
    target.actorPoster = finder.castView(view, 2131492972, "field 'actorPoster'");
    view = finder.findRequiredView(source, 2131492973, "field 'actorName'");
    target.actorName = finder.castView(view, 2131492973, "field 'actorName'");
    view = finder.findRequiredView(source, 2131492974, "field 'actorSubtitile'");
    target.actorSubtitile = finder.castView(view, 2131492974, "field 'actorSubtitile'");
    view = finder.findRequiredView(source, 2131492977, "field 'actorBiography'");
    target.actorBiography = finder.castView(view, 2131492977, "field 'actorBiography'");
    view = finder.findRequiredView(source, 2131492978, "field 'actorMovies'");
    target.actorMovies = finder.castView(view, 2131492978, "field 'actorMovies'");
    view = finder.findRequiredView(source, 2131492979, "field 'actorTvSeries'");
    target.actorTvSeries = finder.castView(view, 2131492979, "field 'actorTvSeries'");
  }

  @Override public void unbind(T target) {
    target.mainToolbar = null;
    target.actorBackdrop = null;
    target.actorPoster = null;
    target.actorName = null;
    target.actorSubtitile = null;
    target.actorBiography = null;
    target.actorMovies = null;
    target.actorTvSeries = null;
  }
}
