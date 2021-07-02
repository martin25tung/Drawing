package com.example.drawing.bitmap_drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class DrawableView extends View {

  Drawable drawable;

  public DrawableView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  {
    drawable = new ColorDrawable(Color.RED);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    drawable.setBounds(100, 100, getWidth(), getHeight());  // 畫四個邊
    drawable.draw(canvas);

    //Bitmap bitmap = xxx;
    //Drawable convertedDrawable = new BitmapDrawable(getResources(), bitmap);
  }
}
