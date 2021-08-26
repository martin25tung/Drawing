package com.example.drawing;

import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.drawing.animation.ProvinceUtil;

public class MainActivity extends AppCompatActivity {

  View view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //view = findViewById(R.id.view);
    //
    //view.setOnClickListener(v -> {
    //
    //});

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
    /*float length = Utils.dp2px(300);
    Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
    Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length);
    Keyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length);
    Keyframe keyframe4 = Keyframe.ofFloat(1f, 1f * length);

    PropertyValuesHolder viewHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3, keyframe4);
    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, viewHolder);
    animator.setStartDelay(1000);
    animator.setDuration(2000);
    animator.start();*/

    /*view.animate()
        .translationX(Utils.dp2px(450))
        .setStartDelay(1000)
        .setDuration(2000)
        .setInterpolator(new AccelerateInterpolator())
        .start();*/

    /*Point targetPoint = new Point((int)Utils.dp2px(300), (int)Utils.dp2px(200));
    ObjectAnimator animator = ObjectAnimator.ofObject(view, "point", new PointEvaluator(), targetPoint);
    animator.setStartDelay(1000);
    animator.setDuration(2000);
    animator.start();*/

    /*ObjectAnimator animator = ObjectAnimator.ofObject(view, "province", new ProvinceEvaluator(), "澳门特别行政区");
    animator.setStartDelay(1000);
    animator.setDuration(5000);
    animator.start();*/
  }


  class ProvinceEvaluator implements TypeEvaluator<String> {

    @Override public String evaluate(float fraction, String startValue, String endValue) {
      // 北京市    上海市   fraction: 0.5f
      int startIndex = ProvinceUtil.provinces.indexOf(startValue);
      int endIndex = ProvinceUtil.provinces.indexOf(endValue);
      int index = (int) (startIndex + (endIndex - startIndex) * fraction);
      return ProvinceUtil.provinces.get(index);
    }
  }

  // 系統已經寫好了
  class FloatValueEvaluator implements TypeEvaluator<Float> {

    @Override public Float evaluate(float fraction, Float startValue, Float endValue) {
      // start: 0   end: 2    fraction: 0.2   return: 0 + (2 - 0) * 0.2
      return null;
    }
  }

  class PointEvaluator implements TypeEvaluator<Point> {

    @Override public Point evaluate(float fraction, Point startValue, Point endValue) {
      // (1, 1)   (5, 5)    fraction: 0.2   x: 1 + (5 -1) * 0.2 y: 1 + (5 -1) * 0.2
      float x = startValue.x + (endValue.x - startValue.x) * fraction;
      float y = startValue.y + (endValue.y - startValue.y) * fraction;
      return new Point( (int)x, (int)y);
    }
  }
}
