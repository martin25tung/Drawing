package com.example.drawing.scalableImageView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import com.example.drawing.Utils;

public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

  private static final float IMAGE_WIDTH = Utils.dp2px(300);
  private static final float OVER_SCALE_FACTOR = 1.5f;

  Bitmap bitmap;
  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

  float originalOffsetX;
  float originalOffsetY;
  float offsetX;
  float offsetY;
  float smallScale;
  float bigScale;
  boolean big;
  float scaleFraction; // 0 ~ 1f
  ObjectAnimator scaleAnimator;
  GestureDetectorCompat detector;
  OverScroller scroller;
  HenFlingRunner henFlingRunner = new HenFlingRunner();

  public ScalableImageView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);

    bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    detector = new GestureDetectorCompat(context, this);
    scroller = new OverScroller(context);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    // 置中
    originalOffsetX = ((float)getWidth() - bitmap.getWidth()) / 2;
    originalOffsetY = ((float)getHeight() - bitmap.getHeight()) / 2;

    // 圖片寬高比 調整
    if ((float)bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
      smallScale = (float) getWidth() / bitmap.getWidth();
      bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
    } else {
      smallScale = (float) getHeight() / bitmap.getHeight();
      bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction); // 加上偏移量 讓偏移跟縮放一起
    float scale = smallScale + (bigScale - smallScale) * scaleFraction; // ( 差值 ＊ 完成率 ) + 起始值
    canvas.scale(scale, scale, getWidth() / 2f, getHeight() / 2f);
    canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
  }

  private float getScaleFraction() {
    return scaleFraction;
  }

  private void setScaleFraction(float scaleFraction) {
    this.scaleFraction = scaleFraction;
    invalidate();
  }

  private ObjectAnimator getScaleAnimator() {
    if (scaleAnimator == null) {
      scaleAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
    }
    scaleAnimator.setFloatValues(smallScale, bigScale);
    return scaleAnimator;
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    return detector.onTouchEvent(event);
  }

  @Override public boolean onDown(MotionEvent e) {
    return true; // 這必須返回 true
  }

  @Override public void onShowPress(MotionEvent e) {

  }

  @Override public boolean onSingleTapUp(MotionEvent e) {
    return false; // 單擊
  }

  @Override
  public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
    if (big) {
      offsetX -= distanceX;
      offsetY -= distanceY;
      // 需要加上左右邊界修正
      fixOffsets();
      invalidate();
    }

    return false;
  }

  private void fixOffsets() {
    offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
    offsetX = Math.max(offsetX, - (bitmap.getWidth() * bigScale - getWidth()) / 2);
    offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
    offsetY = Math.max(offsetY, - (bitmap.getHeight() * bigScale - getHeight()) / 2);
  }

  @Override public void onLongPress(MotionEvent e) {

  }

  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    if (big) {
      scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
          - (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
          (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
          - (int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
          (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);
      postOnAnimation(henFlingRunner);
    }
    return false;
  }

  @Override public boolean onSingleTapConfirmed(MotionEvent e) {
    return false;
  }

  @Override public boolean onDoubleTap(MotionEvent e) {
    big = !big;
    if (big) {
      // 讓在點擊的時候，能夠停留在原點放大
      offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2) * bigScale / smallScale;
      offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2) * bigScale / smallScale;
      fixOffsets();
      getScaleAnimator().start();
    } else {
      getScaleAnimator().reverse();
    }
    return false;
  }

  @Override public boolean onDoubleTapEvent(MotionEvent e) {
    return false;
  }

  class HenFlingRunner implements Runnable {

    @Override
    public void run() {
      if (scroller.computeScrollOffset()) {
        offsetX = scroller.getCurrX();
        offsetY = scroller.getCurrY();
        invalidate();
        postOnAnimation(this);
      }
    }
  }

}
