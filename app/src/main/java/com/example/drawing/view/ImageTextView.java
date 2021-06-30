package com.example.drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.R;
import com.example.drawing.Utils;

public class ImageTextView extends View {
  private static final float IMAGE_WIDTH = Utils.dp2px(100);
  private static final float IMAGE_OFFSET = Utils.dp2px(80);

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  Bitmap bitmap;
  Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
  String text = "调用Dispatcher的execute方法，那Dispatcher是什么呢？从名字来看它是一个调度器，调度什么呢？就是所有网络请求，也就是RealCall对象。网络请求支持同步执行和异步执行，异步执行就需要线程池、并发阈值这些东西，如果超过阈值需要将超过的部分存储起来，这样一分析Dispatcher的功能就可以总结调用Dispatcher的execute方法，那Dispatcher是什么呢？从名字来看它是一个调度器，调度什么呢？就是所有网络请求，也就是RealCall对象。网络请求支持同步执行和异步执行，异步执行就需要线程池、并发阈值这些东西，如果超过阈值需要将超过的部分存储起来，这样一分析Dispatcher的功能就可以总结调用Dispatcher的execute方法，那Dispatcher是什么呢？从名字来看它是一个调度器，调度什么呢？就是所有网络请求，也就是RealCall对象。网络请求支持同步执行和异步执行，异步执行就需要线程池、并发阈值这些东西，如果超过阈值需要将超过的部分存储起来，这样一分析Dispatcher的功能就可以总结";
  float[] cutWidth = new float[1];

  public ImageTextView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  {
    bitmap = getAvatar((int) Utils.dp2px(100));
    paint.setTextSize(Utils.dp2px(14));
    paint.getFontMetrics(fontMetrics);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // new 對象 通常不要在onDraw裡面做。
    // 最簡單寫文字方式 StaticLayout
    //StaticLayout staticLayout = new StaticLayout("调用Dispatcher的execute方法，那Dispatcher是什么呢？从名字来看它是一个调度器，调度什么呢？就是所有网络请求，也就是RealCall对象。网络请求支持同步执行和异步执行，异步执行就需要线程池、并发阈值这些东西，如果超过阈值需要将超过的部分存储起来，这样一分析Dispatcher的功能就可以总结调用Dispatcher的execute方法，那Dispatcher是什么呢？从名字来看它是一个调度器，调度什么呢？就是所有网络请求，也就是RealCall对象。网络请求支持同步执行和异步执行，异步执行就需要线程池、并发阈值这些东西，如果超过阈值需要将超过的部分存储起来，这样一分析Dispatcher的功能就可以总结调用Dispatcher的execute方法，那Dispatcher是什么呢？从名字来看它是一个调度器，调度什么呢？就是所有网络请求，也就是RealCall对象。网络请求支持同步执行和异步执行，异步执行就需要线程池、并发阈值这些东西，如果超过阈值需要将超过的部分存储起来，这样一分析Dispatcher的功能就可以总结", textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
    //staticLayout.draw(canvas);

    // 當文字的頂部或是底部，在圖片的高度範圍內，文字的寬度就必須減掉圖片寬
    canvas.drawBitmap(bitmap, getWidth() - IMAGE_WIDTH, IMAGE_OFFSET, paint);
    int length = text.length();
    float verticalOffset = - fontMetrics.top;
    for (int start = 0; start < length; ) {
      int maxWidth;
      float textTop = verticalOffset + fontMetrics.top;
      float textBottom = verticalOffset + fontMetrics.bottom;
      if (textTop > IMAGE_OFFSET && textTop < IMAGE_OFFSET + IMAGE_WIDTH
          || textBottom > IMAGE_OFFSET && textBottom < IMAGE_OFFSET + IMAGE_WIDTH) {
        // 文字和图片在同一行
        maxWidth = (int) (getWidth() - IMAGE_WIDTH);
      } else {
        // 文字和图片不在同一行
        maxWidth = getWidth();
      }
      int count = paint.breakText(text, start, length, true, maxWidth,
          cutWidth); // measureForwards：正向繪製 ; 回傳斷行的位置，breakText 是關鍵方法
      canvas.drawText(text, start, start + count, 0, verticalOffset, paint);
      start += count;
      verticalOffset += paint.getFontSpacing();
    }
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
