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

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  Camera camera = new Camera();

  public CameraView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  {
    camera.rotateX(45);
    camera.setLocation(0, 0, Utils.getZForCamera()); // z: 預設 -8 -8 = -8 * 72
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.save();
    canvas.translate(100 + 600 / 2, 100 + 600 / 2); // 移動完，再去做計算座標。為了方便計算，也可以不做移動，直接計算座標。
    canvas.rotate(-20);
    canvas.clipRect(-600, -600 , 600, 0);
    canvas.rotate(20);
    canvas.translate(-(100 + 600 / 2), -(100 + 600 / 2));
    canvas.drawBitmap(Utils.getAvatar(getResources(), 600), 100, 100, paint);
    canvas.restore();

    // 繪製下半部分
    // 反著寫
    canvas.translate(100 + 600 / 2, 100 + 600 / 2);
    canvas.rotate(-20);
    camera.applyToCanvas(canvas);
    canvas.clipRect(-600, 0, 600, 600);
    canvas.rotate(20);  // 旋轉效果
    canvas.translate(-(100 + 600 / 2), -(100 + 600 / 2));

    canvas.drawBitmap(Utils.getAvatar(getResources(), 600), 100, 100, paint);
  }
}
