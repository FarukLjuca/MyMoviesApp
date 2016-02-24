package com.atlantbh.mymoviesapp.helpers;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class FontHelper {
    private static Map<String, Typeface> fonts;

    public static Typeface getFont(Context context, String fontTitle) {
        if (fonts == null) {
            fonts = new HashMap<>();
            fonts.put("AvenirRegular", Typeface.createFromAsset(context.getAssets(), "fonts/AvenirNextLTPro-Regular.otf"));
            fonts.put("RobotoRegular", Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));
            fonts.put("RobotoMedium", Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf"));
        }

        return fonts.get(fontTitle);
    }
}
