package com.example.drawing;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  View view;

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

    //ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", Utils.dp2px(150)); // 用反射呼叫 setRadius 方法
    //animator.setStartDelay(1000);
    //animator.start();

    /*ObjectAnimator bottomFlipAnimator = ObjectAnimator.ofFloat(view, "bottomFlip", 45);
    bottomFlipAnimator.setDuration(1500);

    ObjectAnimator flipRotationAnimator = ObjectAnimator.ofFloat(view, "flipRotation", 270);
    flipRotationAnimator.setDuration(1500);

    ObjectAnimator topFlipAnimator = ObjectAnimator.ofFloat(view, "topFlip", -45);
    flipRotationAnimator.setDuration(1500);

    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator);
    animatorSet.setStartDelay(1000);
    animatorSet.start();*/

    /*PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip", 45);
    PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation", 270);
    PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("topFlip", -45);
    ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, bottomFlipHolder, flipRotationHolder, topFlipHolder);
    objectAnimator.setStartDelay(1000);
    objectAnimator.setDuration(2000);
    objectAnimator.start();*/

    // 客製化中間的流程  時間曲線
    float length = Utils.dp2px(300);
    Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
    Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length);
    Keyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length);
    Keyframe keyframe4 = Keyframe.ofFloat(1f, 1f * length);

    PropertyValuesHolder viewHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4);
    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, viewHolder);
    animator.setStartDelay(1000);
    animator.setDuration(2000);
    animator.start();
  }
}