// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MovieListActivity$$ViewBinder<T extends com.atlantbh.mymoviesapp.activities.MovieListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624065, "field 'viewPager'");
    target.viewPager = finder.castView(view, 2131624065, "field 'viewPager'");
    view = finder.findRequiredView(source, 2131624064, "field 'tabLayout'");
    target.tabLayout = finder.castView(view, 2131624064, "field 'tabLayout'");
  }

  @Override public void unbind(T target) {
    target.viewPager = null;
    target.tabLayout = null;
  }
}
