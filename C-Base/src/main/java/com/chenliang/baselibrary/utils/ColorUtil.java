package com.chenliang.baselibrary.utils;

import android.content.Context;
import android.os.Build;

public class ColorUtil {
    public static int getColor(Context context, int colorResId) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = context.getResources().getColor(colorResId, null);
        } else {
            result = context.getResources().getColor(colorResId);
        }
        return result;
    }
}
