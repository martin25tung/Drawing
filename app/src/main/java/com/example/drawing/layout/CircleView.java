package com.example.drawing.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawing.Utils;

public class CircleView extends View {
    private static final int RADIUS = (int) Utils.dp2px(80);
    private static final int PADDING = (int) Utils.dp2px(30);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec); // 我自己算 所以不需要這行
        int width = (PADDING + RADIUS) * 2;
        int height = (PADDING + RADIUS) * 2;

        width = resolveSizeAndState(width, widthMeasureSpec, 0);
        height = resolveSizeAndState(height, widthMeasureSpec, 0);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.RED);
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint);
    }
}
