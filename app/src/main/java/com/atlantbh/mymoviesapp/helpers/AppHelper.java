package com.atlantbh.mymoviesapp.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.atlantbh.mymoviesapp.MyApplication;
import com.atlantbh.mymoviesapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class AppHelper {
    private static SimpleDateFormat simpleDateFormat;

    public static SimpleDateFormat getSimpleDateFormat () {
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy");
        }
        return simpleDateFormat;
    }

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    private static ConnectivityManager cm;

    public static boolean isOnline() {
        if (cm == null) {
            cm = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
