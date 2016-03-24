// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.fragments;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ActorFragment$$ViewBinder<T extends com.atlantbh.mymoviesapp.fragments.ActorFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624090, "field 'actorBackdrop'");
    target.actorBackdrop = finder.castView(view, 2131624090, "field 'actorBackdrop'");
    view = finder.findRequiredView(source, 2131624092, "field 'actorPoster'");
    target.actorPoster = finder.castView(view, 2131624092, "field 'actorPoster'");
    view = finder.findRequiredView(source, 2131624093, "field 'actorName'");
    target.actorName = finder.castView(view, 2131624093, "field 'actorName'");
    view = finder.findRequiredView(source, 2131624094, "field 'actorSubtitle'");
    target.actorSubtitle = finder.castView(view, 2131624094, "field 'actorSubtitle'");
    view = finder.findRequiredView(source, 2131624097, "field 'actorBiography'");
    target.actorBiography = finder.castView(view, 2131624097, "field 'actorBiography'");
    view = finder.findRequiredView(source, 2131624098, "field 'actorMovies'");
    target.actorMovies = finder.castView(view, 2131624098, "field 'actorMovies'");
    view = finder.findRequiredView(source, 2131624099, "field 'actorTv'");
    target.actorTv = finder.castView(view, 2131624099, "field 'actorTv'");
    view = finder.findRequiredView(source, 2131624096, "field 'actorInfo'");
    target.actorInfo = finder.castView(view, 2131624096, "field 'actorInfo'");
  }

  @Override public void unbind(T target) {
    target.actorBackdrop = null;
    target.actorPoster = null;
    target.actorName = null;
    target.actorSubtitle = null;
    target.actorBiography = null;
    target.actorMovies = null;
    target.actorTv = null;
    target.actorInfo = null;
  }
}
