package com.example.drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

public class SportsView extends View {
    private static final float RING_WIDTH = Utils.dp2px(20);
    private static final float RADIUS = Utils.dp2px(150);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect rect = new Rect();
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(100));
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Quicksand-Regular.ttf"));
        paint.getFontMetrics(fontMetrics);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        // 绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);    // 線圓頭
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, -90, 225, false, paint);

        // 绘制文字
        paint.setTextSize(Utils.dp2px(100));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        //  取得文字實際範圍，這個會根據實際範圍做變動
//        paint.getTextBounds("abab", 0, "abab".length(), rect);
        // float offest = (rect.top + rect.bottom) / 2;
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        // 文字標準範圍，不會變動。
        canvas.drawText("abab", getWidth() / 2, getHeight() / 2 - offset, paint);

        // 繪製文字：2
        //paint.setTextSize(Utils.dp2px(150));
        //paint.setTextAlign(Paint.Align.LEFT);
        //paint.getTextBounds("aaaa", 0, "aaaa".length(), rect);
        //canvas.drawText("aaaa", -rect.left, 200, paint);
        //
        //// 繪製文字：3
        //paint.setTextSize(Utils.dp2px(15));
        //paint.setTextAlign(Paint.Align.LEFT);
        //canvas.drawText("aaaa", 0, 200 + paint.getFontSpacing(), paint);
    }
}
