package com.example.drawing.layout;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.Nullable;

public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {
    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //@Override public void layout(int l, int t, int r, int b) {
    //    super.layout(l, t, r + 200, b + 200);   // 在這裡做計算，會導致父View 不知道你實際的尺寸，所以比須在 onMeasure 裡面計算
    //}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int size = Math.max(measuredWidth, measuredHeight);

        setMeasuredDimension(size, size); // 保存测得的尺寸
    }
}
