package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

public class TestView extends View {

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 抗鋸齒

  public TestView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }


  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawLine(100, 100, 200, 200, paint);  // 畫線
    canvas.drawCircle(getWidth() / 2, getHeight() / 2, Utils.dp2px(150), paint); //在中心點畫圓
  }
}
