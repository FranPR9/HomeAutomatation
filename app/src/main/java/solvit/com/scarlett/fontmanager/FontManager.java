package solvit.com.scarlett.fontmanager;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by FranciscoPR on 26/01/16.
 */
public class FontManager {

    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

}