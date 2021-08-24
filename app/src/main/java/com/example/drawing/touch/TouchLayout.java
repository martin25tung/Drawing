package com.example.drawing.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

class TouchLayout extends ViewGroup {

  public TouchLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean shouldDelayChildPressedState() {
    return false;
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    int delta = (int) ev.getY(); // 縱向移動距離
    if (Math.abs(delta) > SLOP) {
      return false;
    } else {
      return true;
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {

  }
}
