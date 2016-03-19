package com.atlantbh.mymoviesapp.helpers;

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
}
