package com.example.drawing.multi_touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

public class MultiTouchView1 extends View {

  private static final float IMAGE_WIDTH = Utils.dp2px(200);

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  Bitmap bitmap;

  float downX;
  float downY;
  float offsetX;
  float offsetY;
  float originalOffsetX;
  float originalOffsetY;
  int trackingPointerId;

  public MultiTouchView1(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);

    bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getActionMasked()) {
      // 單點觸摸
      case MotionEvent.ACTION_DOWN:
        trackingPointerId = event.getPointerId(0);
        downX = event.getX();
        downY = event.getY();
        originalOffsetX = offsetX;
        originalOffsetY = offsetY;
        break;
      case MotionEvent.ACTION_MOVE:
        int index = event.findPointerIndex(trackingPointerId);
        offsetX = originalOffsetX + event.getX(index) - downX;
        offsetY = originalOffsetY + event.getY(index) - downY;
        invalidate();
        break;
      case MotionEvent.ACTION_POINTER_DOWN:
        int actionIndex = event.getActionIndex();
        trackingPointerId = event.getPointerId(actionIndex); // 拿到剛按下的手指id
        downX = event.getX(actionIndex);
        downY = event.getY(actionIndex);
        originalOffsetX = offsetX;
        originalOffsetY = offsetY;
        break;
      case MotionEvent.ACTION_POINTER_UP:
        actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);
        if (pointerId == trackingPointerId) { // 先檢查是否是正在追蹤的手指id
          int newIndex;
          if (actionIndex == event.getPointerCount() - 1) { // 如果index 已經是最新的 就 -2 否則 -1
            newIndex = event.getPointerCount() - 2;
          } else {
            newIndex = event.getPointerCount() - 1;
          }
          trackingPointerId = event.getPointerId(newIndex);
          downX = event.getX(actionIndex);
          downY = event.getY(actionIndex);
          originalOffsetX = offsetX;
          originalOffsetY = offsetY;
        }
        break;
    }
    return true;
  }

  @Override protected void onDraw(Canvas canvas) {
    canvas.drawBitmap(bitmap, 0, 0, paint);
  }
}
