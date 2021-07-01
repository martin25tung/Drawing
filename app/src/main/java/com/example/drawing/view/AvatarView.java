package com.example.drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.R;
import com.example.drawing.Utils;

public class AvatarView extends View {

  private static final float WIDTH = Utils.dp2px(300);
  private static final float PADDING = Utils.dp2px(50);
  private static final float EDGE_WIDTH = Utils.dp2px(10);

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN); // 將scr畫在目標上
  Bitmap bitmap;
  RectF savedArea = new RectF();

  public AvatarView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  {
    bitmap = getAvatar((int) WIDTH);
    setLayerType(LAYER_TYPE_HARDWARE, null);  // 可以用來下面的 saveLayer
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    savedArea.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint);
    int saved = canvas.saveLayer(savedArea, paint); // 離屏緩衝，先把我要的區域存起來，其他的不管。
    canvas.drawOval(PADDING + EDGE_WIDTH, PADDING + EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, paint);
    paint.setXfermode(xfermode);
    canvas.drawBitmap(bitmap, PADDING, PADDING, paint);
    paint.setXfermode(null);
    canvas.restoreToCount(saved);
  }

  Bitmap getAvatar(int width) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    // 只取圖片的寬高比
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
    options.inJustDecodeBounds = false;
    options.inDensity = options.outWidth;
    options.inTargetDensity = width;
    // 這邊才是實際取出圖片。先取圖片的實際寬高，再取圖片。這樣性能比較好。
    return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
  }

}
