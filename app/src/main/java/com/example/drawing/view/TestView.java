package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class TestView extends View {

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 抗鋸齒
  Path path = new Path();

  public TestView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  // 每次 layout 過程結束後，尺寸改變後呼叫。不會被過度呼叫。
  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    path.reset();

    path.addRect(getWidth() / 2 - 150, getHeight() / 2 - 300, getWidth() / 2 + 150, getHeight() / 2, Path.Direction.CCW);
    path.addCircle(getWidth() / 2, getHeight() / 2, 150, Path.Direction.CW);
    // 繪製方向。
    // CW： Clock Work 順時針
    // CCW： Counter Clock Work 逆時針
    path.addCircle(getWidth() / 2, getHeight() / 2, 400, Path.Direction.CW);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    //canvas.drawLine(100, 100, 200, 200, paint);  // 畫線
    //canvas.drawCircle(getWidth() / 2, getHeight() / 2, Utils.dp2px(150), paint); //在中心點畫圓

    path.setFillType(Path.FillType.EVEN_ODD);
    // WINDING：同方向圓，全填充
    // EVENT_ODD：不考慮方向，偶數算外部
    // INVERSE_WINDING：WINDING 相反
    // INVERSE_EVEN_ODD：EVENT_ODD 相反
    canvas.drawPath(path, paint);
  }
}
