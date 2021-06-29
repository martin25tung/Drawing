package com.example.drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.Nullable;

public class PorterDuffView extends View {

  private Context context;

  //屏幕宽高
  private int screenW;

  //绘制的图片宽高
  private int width = 200;
  private int height = 200;

  //上层SRC的Bitmap和下层Dst的Bitmap
  private Bitmap srcBitmap, dstBitmap;

  public PorterDuffView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);

    this.context = context;

    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(dm);

    screenW = dm.widthPixels;

    //实例化两个Bitmap
    srcBitmap = makeSrc(width, height);
    dstBitmap = makeDst(width, height);
  }

  @Override
  public void onMeasure(int width,int height ) {

    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    manager.getDefaultDisplay().getMetrics(dm);

    screenW = dm.widthPixels;

    setMeasuredDimension(screenW,8000);
  }


  @Override
  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setFilterBitmap(false);
    paint.setStyle(Paint.Style.FILL);
    paint.setTextSize(48.0f);

    for ( PorterDuff.Mode mode : PorterDuff.Mode.class.getEnumConstants()) {

      canvas.drawText(mode.name(), 10, 50, paint);
      canvas.drawBitmap(srcBitmap, (screenW / 3 - width) / 2, 100, paint);
      canvas.drawBitmap(dstBitmap, (screenW / 3 - width) / 2 + screenW / 3, 100, paint);

      int sc = canvas.saveLayer(0, 0, screenW, 300, null, Canvas.ALL_SAVE_FLAG);

      canvas.drawBitmap(dstBitmap, (screenW / 3 - width) / 2 + screenW / 3 * 2,
          100, paint);     //绘制

      //设置Paint的Xfermode
      paint.setXfermode(new PorterDuffXfermode(mode));
      canvas.drawBitmap(srcBitmap, (screenW / 3 - width) / 2 + screenW / 3 * 2,
          100, paint);
      paint.setXfermode(null);

      // 还原画布
      canvas.restoreToCount(sc);

      canvas.translate(0, 400);

    }
  }


  private Bitmap makeDst(int w, int h) {
    Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(bm);
    Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    p.setColor(Color.RED);
    c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
    return bm;
  }

  //定义一个绘制矩形的 Bitmap 的方法
  private Bitmap makeSrc(int w, int h) {
    Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(bm);
    Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    p.setColor(Color.BLUE);
    c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
    return bm;
  }
}
