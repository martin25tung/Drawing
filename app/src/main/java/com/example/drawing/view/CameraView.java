package com.example.drawing.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

public class CameraView extends View {

  private static final float PADDING = Utils.dp2px(100);
  private static final float IMAGE_WIDTH = Utils.dp2px(200);

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  Camera camera = new Camera();

  float topFlip = 0;
  float bottomFlip = 0;
  float flipRotation = 0;

  public CameraView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  {
    camera.setLocation(0, 0, Utils.getZForCamera()); // z: 預設 -8 -8 = -8 * 72
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.save();
    canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2); // 移動完，再去做計算座標。為了方便計算，也可以不做移動，直接計算座標。
    canvas.rotate(-flipRotation);
    camera.save();  // 先把 camera 資訊存起來
    camera.rotateX(topFlip);
    camera.applyToCanvas(canvas); // 提交 camera 給 canvas
    camera.restore(); // 將 camera 恢復原狀
    canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH , IMAGE_WIDTH, 0);
    canvas.rotate(flipRotation);
    canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2));
    canvas.drawBitmap(Utils.getAvatar(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);
    canvas.restore();

    // 繪製下半部分
    // 反著寫
    canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
    canvas.rotate(-flipRotation);
    camera.save();  // 先把 camera 資訊存起來
    camera.rotateX(bottomFlip);
    camera.applyToCanvas(canvas); // 提交 camera 給 canvas
    camera.restore(); // 將 camera 恢復原狀
    canvas.clipRect(-IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_WIDTH);
    canvas.rotate(flipRotation);  // 旋轉效果
    canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2));

    canvas.drawBitmap(Utils.getAvatar(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);
  }

  public float getTopFlip() {
    return topFlip;
  }

  public void setTopFlip(float topFlip) {
    this.topFlip = topFlip;
    invalidate();
  }

  public float getBottomFlip() {
    return bottomFlip;
  }

  public void setBottomFlip(float bottomFlip) {
    this.bottomFlip = bottomFlip;
    invalidate();
  }

  public float getFlipRotation() {
    return flipRotation;
  }

  public void setFlipRotation(float flipRotation) {
    this.flipRotation = flipRotation;
    invalidate();
  }
}
