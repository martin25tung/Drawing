package com.example.drawing.bitmap_drawable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import com.example.drawing.R;
import com.example.drawing.Utils;

public class MaterialEditText extends AppCompatEditText {

  private static final float TEXT_SIZE = Utils.dp2px(12);
  private static final float TEXT_MARGIN = Utils.dp2px(8);
  private static final int TEXT_VERTICAL_OFFSET = (int) Utils.dp2px(22);
  private static final int TEXT_HORIZONTAL_OFFSET = (int) Utils.dp2px(5);
  private static final int TEXT_ANIMATION_OFFSET = (int) Utils.dp2px(16);

  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  boolean floatingLabelShown;
  float floatingLabelFraction;
  ObjectAnimator animator;
  boolean useFloatingLabel;
  Rect backgroundPadding = new Rect();

  public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    init(context, attrs);
  }

  {
    // 這裏比建構子更早執行
  }

  void init(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
    useFloatingLabel = typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true);
    typedArray.recycle(); // 需要回收

    paint.setTextSize(TEXT_SIZE);
    onUseFloatingLabelChanged();
    addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (useFloatingLabel) {
          if (floatingLabelShown && TextUtils.isEmpty(s)) {
            floatingLabelShown = false;
            getAnimator().reverse();
          } else if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
            floatingLabelShown = true;
            getAnimator().start();
          }
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  private ObjectAnimator getAnimator() {
    if (animator == null) {
      animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 0, 1);
    }
    return animator;
  }

  public float getFloatingLabelFraction() {
    return floatingLabelFraction;
  }

  public void setFloatingLabelFraction(float floatingLabelFraction) {
    this.floatingLabelFraction = floatingLabelFraction;
    invalidate();
  }

  private void onUseFloatingLabelChanged() {
    getBackground().getPadding(backgroundPadding);
    if (useFloatingLabel) {
      setPadding(getPaddingLeft(), (int) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(), getPaddingBottom());
    } else {
      setPadding(getPaddingLeft(), backgroundPadding.top, getPaddingRight(), getPaddingBottom());
    }
  }

  public void setUseFloatingLabel(boolean useFloatingLabel) {
    if (this.useFloatingLabel != useFloatingLabel) {
      this.useFloatingLabel = useFloatingLabel;
      onUseFloatingLabelChanged();
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    paint.setAlpha((int) (0xff * floatingLabelFraction));
    float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
    canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET + extraOffset , paint);
  }
}
