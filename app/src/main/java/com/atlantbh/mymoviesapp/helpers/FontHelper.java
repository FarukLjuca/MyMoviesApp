package com.atlantbh.mymoviesapp.helpers;

import android.content.Context;
import android.graphics.Typeface;

import com.atlantbh.mymoviesapp.R;

import java.util.HashMap;
import java.util.Map;

public class FontHelper {
    public static final String AVENIR_REGULAR = "AvenirRegular";
    public static final String ROBOTO_REGULAR = "RobotoRegular";
    public static final String ROBOTO_MEDIUM = "RobotoMedium";

    private static Map<String, Typeface> fonts;

    public static Typeface getFont(Context context, String font) {
        if (fonts == null) {
            fonts = new HashMap<>();
            fonts.put(AVENIR_REGULAR,
                    Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextLTPro-Regular.otf"));
            fonts.put(ROBOTO_REGULAR,
                    Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));
            fonts.put(ROBOTO_MEDIUM,
                    Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf"));
        }

        return fonts.get(font);
    }
}
