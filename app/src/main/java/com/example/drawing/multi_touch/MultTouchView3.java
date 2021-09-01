package com.example.drawing.multi_touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

public class MultTouchView3 extends View {

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

  SparseArray<Path> paths = new SparseArray<>();  // 用來存多點位置

  public MultTouchView3(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  {
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(Utils.dp2px(4));
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setStrokeJoin(Paint.Join.ROUND);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getActionMasked()) {
      case ACTION_DOWN:
      case ACTION_POINTER_DOWN:
        int actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);
        Path path = new Path();
        path.moveTo(event.getX(actionIndex), event.getY(actionIndex));  // 移動到那個位置
        paths.append(pointerId, path);
        invalidate();
        break;
      case ACTION_MOVE:
        for (int i = 0; i < event.getPointerCount(); i++) {
          pointerId = event.getPointerId(i);
          path = paths.get(pointerId);
          path.lineTo(event.getX(i), event.getY(i));  // 畫線到那個位置
        }
        invalidate();
        break;
      case ACTION_UP:
      case ACTION_POINTER_UP:
        pointerId = event.getPointerId(event.getActionIndex());
        paths.remove(pointerId);
        invalidate();
        break;
    }
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    for (int i = 0; i <paths.size(); i++) {
      Path path = paths.valueAt(i);
      canvas.drawPath(path, paint);
    }
  }

}
