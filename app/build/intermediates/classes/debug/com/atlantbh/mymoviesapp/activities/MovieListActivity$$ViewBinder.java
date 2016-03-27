// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MovieListActivity$$ViewBinder<T extends com.atlantbh.mymoviesapp.activities.MovieListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624075, "field 'viewPager'");
    target.viewPager = finder.castView(view, 2131624075, "field 'viewPager'");
    view = finder.findRequiredView(source, 2131624074, "field 'tabLayout'");
    target.tabLayout = finder.castView(view, 2131624074, "field 'tabLayout'");
    view = finder.findRequiredView(source, 2131624073, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131624073, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131624061, "field 'drawer'");
    target.drawer = finder.castView(view, 2131624061, "field 'drawer'");
    view = finder.findRequiredView(source, 2131624063, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131624063, "field 'navigationView'");
  }

  @Override public void unbind(T target) {
    target.viewPager = null;
    target.tabLayout = null;
    target.toolbar = null;
    target.drawer = null;
    target.navigationView = null;
  }
}
