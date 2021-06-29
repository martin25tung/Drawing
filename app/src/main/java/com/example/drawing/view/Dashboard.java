package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

public class Dashboard extends View {
  private static final int ANGLE = 120;
  private static final float RADIUS = Utils.dp2px(150);
  private static final float LENGTH = Utils.dp2px(100);
  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  Path dash = new Path();
  PathDashPathEffect effect;

  public Dashboard(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  {
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(Utils.dp2px(2));
    dash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CW); // 刻度

    Path arc = new Path();  // 用來計算弧的長度
    arc.addArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS , getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE);
    PathMeasure pathMeasure = new PathMeasure(arc, false);
    effect = new PathDashPathEffect(dash, (pathMeasure.getLength() - Utils.dp2px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);// advance：刻度間距離, phase：第一個刻度要空多長
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // 角度往下開始算，順時鐘
    // 画线
    canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS , getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE, false, paint);

    // 画刻度
    paint.setPathEffect(effect);
    canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS , getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE, false, paint);
    paint.setPathEffect(null);

    // 画指针
    canvas.drawLine(getWidth() / 2, getHeight() / 2,
        (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getWidth() / 2,
        (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + getHeight() / 2,
        paint); // x：餘弦定理 y：正弦
  }

  int getAngleFromMark(int mark) {
    return (int) (90 + (float) ANGLE / 2 + (360 - (float) ANGLE) / 20 * mark);
  }
}
