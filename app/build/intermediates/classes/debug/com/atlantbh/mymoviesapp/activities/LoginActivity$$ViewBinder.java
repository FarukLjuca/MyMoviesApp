// Generated code from Butter Knife. Do not modify!
package com.atlantbh.mymoviesapp.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.atlantbh.mymoviesapp.activities.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624051, "field 'username'");
    target.username = finder.castView(view, 2131624051, "field 'username'");
    view = finder.findRequiredView(source, 2131624053, "field 'password'");
    target.password = finder.castView(view, 2131624053, "field 'password'");
    view = finder.findRequiredView(source, 2131624055, "field 'error'");
    target.error = finder.castView(view, 2131624055, "field 'error'");
  }

  @Override public void unbind(T target) {
    target.username = null;
    target.password = null;
    target.error = null;
  }
}
