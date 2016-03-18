// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActorFragment$$ViewBinder<T extends com.atlantbh.mymoviesapp.fragments.ActorFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624064, "field 'actorBackdrop'");
    target.actorBackdrop = finder.castView(view, 2131624064, "field 'actorBackdrop'");
    view = finder.findRequiredView(source, 2131624066, "field 'actorPoster'");
    target.actorPoster = finder.castView(view, 2131624066, "field 'actorPoster'");
    view = finder.findRequiredView(source, 2131624067, "field 'actorName'");
    target.actorName = finder.castView(view, 2131624067, "field 'actorName'");
    view = finder.findRequiredView(source, 2131624068, "field 'actorSubtitile'");
    target.actorSubtitile = finder.castView(view, 2131624068, "field 'actorSubtitile'");
    view = finder.findRequiredView(source, 2131624071, "field 'actorBiography'");
    target.actorBiography = finder.castView(view, 2131624071, "field 'actorBiography'");
    view = finder.findRequiredView(source, 2131624072, "field 'actorMovies'");
    target.actorMovies = finder.castView(view, 2131624072, "field 'actorMovies'");
    view = finder.findRequiredView(source, 2131624073, "field 'actorTv'");
    target.actorTv = finder.castView(view, 2131624073, "field 'actorTv'");
    view = finder.findRequiredView(source, 2131624070, "field 'actorInfo'");
    target.actorInfo = finder.castView(view, 2131624070, "field 'actorInfo'");
  }

  @Override public void unbind(T target) {
    target.actorBackdrop = null;
    target.actorPoster = null;
    target.actorName = null;
    target.actorSubtitile = null;
    target.actorBiography = null;
    target.actorMovies = null;
    target.actorTv = null;
    target.actorInfo = null;
  }
}
