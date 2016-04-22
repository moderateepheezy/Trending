package com.example.simpumind.trends.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by SimpuMind on 4/20/16.
 */
public class ThemeUtils {

    public static int DEFAULT_STATE = -1;

    public static int[][] createStatesArray(int... args) {
        int length = args.length;
        int[][] states = new int[length][];
        for (int i = 0; i < length; i++) {
            int state = args[i];
            if (state == DEFAULT_STATE) {
                states[i] = new int[]{};
            } else {
                states[i] = new int[]{state};
            }
        }
        return states;
    }

    public static int[] createColorsArray(Context context, int... args) {
        int length = args.length;
        int[] colors = new int[length];
        for (int i = 0; i < length; i++) {
            int attr = args[i];
            int color = ThemeUtils.getColor(context, attr);
            colors[i] = color;
        }
        return colors;
    }

    public static int getColor(Context context, int attrResId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrResId, typedValue, true);
        return typedValue.data;
    }

}

