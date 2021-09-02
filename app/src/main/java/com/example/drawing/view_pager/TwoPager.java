package com.example.drawing.view_pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

public class TwoPager extends ViewGroup {

  float downX;
  float downY;
  float downScrollX;
  boolean scrolling;
  float minVelocity;
  float maxVelocity;
  OverScroller overScroller;
  ViewConfiguration viewConfiguration;
  VelocityTracker velocityTracker = VelocityTracker.obtain();

  public TwoPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    overScroller = new OverScroller(context);
    viewConfiguration = ViewConfiguration.get(context);
    maxVelocity = viewConfiguration.getScaledMaximumFlingVelocity(); // 拿到最大快滑速度
    minVelocity = viewConfiguration.getScaledMinimumFlingVelocity(); // 拿到最小快滑速度
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    measureChildren(widthMeasureSpec, heightMeasureSpec);
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
      velocityTracker.clear();
    }
    velocityTracker.addMovement(ev);

    boolean result = false;
    switch (ev.getActionMasked()) {
      case MotionEvent.ACTION_DOWN:
        scrolling = false;
        downX = ev.getX();
        downY = ev.getY();
        downScrollX = getScrollX();
        break;
      case MotionEvent.ACTION_MOVE: // 當滑動一定的距離後處理
        float dx = downX - ev.getX();
        if (!scrolling) {
          if (Math.abs(dx) > viewConfiguration.getScaledPagingTouchSlop()) {
            scrolling = true;
            getParent().requestDisallowInterceptTouchEvent(true); // 叫父View 不要攔截我，會自動重置
            result = true;
          }
        }
        break;
    }
    return result;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
      velocityTracker.clear(); // 事件重置
    }
    velocityTracker.addMovement(event); // 一但有新的事件，把事件存下來。

    switch (event.getActionMasked()) {
      case MotionEvent.ACTION_DOWN: // 當使用者點擊到沒有子View 的地方，但是我們希望回饋的動作要一致，所以這裡的程式碼跟 onInterceptTouchEvent 一樣。
        downX = event.getX();
        downY = event.getY();
        downScrollX = getScrollX();
        break;
      case MotionEvent.ACTION_MOVE:
        float dx = downX - event.getX() + downScrollX;
        if (dx > getWidth()) { // 翻頁判斷，假如在第一頁，無法再向左滑動。
          dx = getWidth();
        } else if (dx < 0) {
          dx = 0;
        }
        scrollTo((int) (dx), 0); // 沒有動畫
        break;
      case MotionEvent.ACTION_UP:
        velocityTracker.computeCurrentVelocity(1000, maxVelocity); // 速度追蹤器
        float vx = velocityTracker.getXVelocity(); // 橫向速度
        int scrollX = getScrollX();
        int targetPage;
        /*
          scrollX 是 子View 最左邊的值
        */
        if (Math.abs(vx) < minVelocity) { // 需判斷是不是快滑
          targetPage = scrollX > getWidth() / 2 ? 1 : 0;
        } else {
          targetPage = vx < 0 ? 1 : 0; // 判斷要落在哪一頁，速度
        }
        int scrollDistance = targetPage == 1 ? (getWidth() - scrollX) : - scrollX;
        overScroller.startScroll(getScrollX(), 0, scrollDistance, 0); // 滑動的過程的效果，快快滑，慢慢到達。
        postInvalidateOnAnimation(); // 用這個方法，computeScroll 也會自動被調用。啟動動畫
        break;
    }
    return true;
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int childLeft = 0;
    int childTop = 0;
    int childRight = getWidth();
    int childBottom = getHeight();
    for (int i = 0; i < getChildCount(); i++) { // 遍歷每一個子View
      View child = getChildAt(i);
      child.layout(childLeft, childTop, childRight, childBottom);
      childLeft += getWidth();
      childRight += getWidth();
    }
  }

  @Override
  public void computeScroll() {
    if (overScroller.computeScrollOffset()) { // 通過這個，計算我實際的位置
      scrollTo(overScroller.getCurrX(), overScroller.getCurrY()); // 更新偏移
      postInvalidateOnAnimation(); // 循環動畫
    }
  }
}
