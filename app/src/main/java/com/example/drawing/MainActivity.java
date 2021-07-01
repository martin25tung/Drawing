package com.example.drawing;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.drawing.animation.CircleView;

public class MainActivity extends AppCompatActivity {

  CircleView view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    view = findViewById(R.id.view);

    //view.animate()
    //    .translationX(Utils.dp2px(200))
    //    .translationY(100)
    //    .rotation(180)
    //    .setStartDelay(1000)  // 延遲一秒
    //    .start();
    // view.setTranslationX() 不斷地呼叫這個函數

    ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", Utils.dp2px(150)); // 用反射呼叫 setRadius 方法
    animator.setStartDelay(1000);
    animator.start();
  }
}