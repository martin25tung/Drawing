package com.example.drawing;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {
    public static float dp2px(float dp) {
        // Resources 只能拿系統的東西
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
