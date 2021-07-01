package com.example.drawing;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

public class Utils {
    public static float dp2px(float dp) {
        // Resources 只能拿系統的東西
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getAvatar(Resources resources, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只取圖片的寬高比
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        // 這邊才是實際取出圖片。先取圖片的實際寬高，再取圖片。這樣性能比較好。
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options);
    }

    public static float getZForCamera(){
        return -6 * Resources.getSystem().getDisplayMetrics().density;
    }
}
