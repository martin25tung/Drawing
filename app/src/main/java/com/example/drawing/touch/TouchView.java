package com.example.drawing.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

class TouchView extends View {

  public TouchView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    // getActionMasked 取得事件，只有一個訊息，只表示你是什麼動作
    if (event.getActionMasked() == MotionEvent.ACTION_UP) {
      performClick();
    }

    //event.getAction() // 舊寫法，包含兩種訊息，動作與第幾根手指
    // event.getActionIndex() // 第幾根手指
    /*MotionEvent.ACTION_UP;
    MotionEvent.ACTION_DOWN;
    MotionEvent.ACTION_MOVE;
    MotionEvent.ACTION_CANCEL;
    MotionEvent.ACTION_POINTER_DOWN;
    MotionEvent.ACTION_POINTER_UP;*/


    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
      return true; // 表示在這處理，不再往下傳遞，包含後續的事件。例如 down 之後的 up
    } else {
      return false;
    }

  }
}
