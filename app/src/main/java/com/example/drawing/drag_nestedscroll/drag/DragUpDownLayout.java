package com.example.drawing.drag_nestedscroll.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import com.example.drawing.R;

public class DragUpDownLayout extends FrameLayout {
  View view;
  ViewDragHelper dragHelper;
  ViewDragHelper.Callback dragListener = new DragListener();
  ViewConfiguration viewConfiguration;

  public DragUpDownLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    dragHelper = ViewDragHelper.create(this, dragListener);
    viewConfiguration = ViewConfiguration.get(context);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    view = findViewById(R.id.view);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return dragHelper.shouldInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    dragHelper.processTouchEvent(event);
    return true;
  }

  @Override
  public void computeScroll() {
    if (dragHelper.continueSettling(true)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  class DragListener extends ViewDragHelper.Callback {
    @Override
    public boolean tryCaptureView(@NonNull View child, int pointerId) {
      return child == view;
    }

    @Override
    public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
      return top;
    }

    @Override
    public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
      if (Math.abs(yvel) > viewConfiguration.getScaledMinimumFlingVelocity()) { // 判斷最小滑動速度
        if (yvel > 0) {
          dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
        } else {
          dragHelper.settleCapturedViewAt(0, 0);
        }
      } else {
        if (releasedChild.getTop() < getHeight() - releasedChild.getBottom()) { // 判斷區塊偏上還是下
          dragHelper.settleCapturedViewAt(0, 0);
        } else {
          dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
        }
      }
      postInvalidateOnAnimation();
    }
  }

}
