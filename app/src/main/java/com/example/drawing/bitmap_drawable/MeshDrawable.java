package com.example.drawing.bitmap_drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

class MeshDrawable extends Drawable {

  private static final int INTERVAL = (int) Utils.dp2px(80);
  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

  {
    paint.setColor(Color.RED);
    paint.setStrokeWidth(Utils.dp2px(2));
  }

  @Override public void draw(@NonNull Canvas canvas) {
    // 繪製規則
    for (int i = 0; i < getBounds().right; i += INTERVAL) {
      for (int j = 0; j < getBounds().bottom; j += INTERVAL) {
        canvas.drawLine(getBounds().left, j, getBounds().right, j, paint);
        canvas.drawLine(i, getBounds().top, i, getBounds().bottom, paint);
      }
    }
  }

  @Override public void setAlpha(int alpha) {
    // 設置透明度
    paint.setAlpha(alpha);
  }

  @Override public int getAlpha() {
    return paint.getAlpha();
  }

  @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {
    paint.setColorFilter(colorFilter);
  }

  @Override public int getOpacity() {
    return paint.getAlpha() == 0 ? PixelFormat.TRANSPARENT:
        paint.getAlpha() == 0xff ? PixelFormat.OPAQUE : PixelFormat.TRANSLUCENT;
  }
}
