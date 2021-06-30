package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

public class CustomView extends View {

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

  public CustomView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.clipRect(100, 100, 300, 300);
    canvas.drawBitmap(Utils.getAvatar(getResources(), 400),0, 0, paint);

  }
}
