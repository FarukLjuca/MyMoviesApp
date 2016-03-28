// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SearchActivity$$ViewBinder<T extends com.atlantbh.mymoviesapp.activities.SearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624066, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131624066, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131624068, "field 'listView'");
    target.listView = finder.castView(view, 2131624068, "field 'listView'");
    view = finder.findRequiredView(source, 2131624067, "field 'results'");
    target.results = finder.castView(view, 2131624067, "field 'results'");
  }

  @Override public void unbind(T target) {
    target.toolbar = null;
    target.listView = null;
    target.results = null;
  }
}
