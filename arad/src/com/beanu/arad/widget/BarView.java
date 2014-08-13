package com.beanu.arad.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 画矩形-动画
 * Created by beanu on 14-1-4.
 */
public class BarView extends View implements ValueAnimator.AnimatorUpdateListener {

    private Paint paint;
    private MyRectF[] data;

    public BarView(Context context) {
        super(context);
        init();
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(50, 140, 140, 140));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data != null) {
            for (int i = 0; i < data.length; i++) {
//                canvas.drawRoundRect(data[i], 5, 5, paint);
                canvas.drawRect(data[i], paint);
            }
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        invalidate();
    }

    public void startAnimation() {
        if (data != null) {

            List<Animator> animators = new ArrayList<Animator>();
            for (int i = 0; i < data.length; i++) {
                float barHeight = data[i].top;

                ValueAnimator animator = ObjectAnimator.ofFloat(data[i], "top", 300, 300 - barHeight);
                animator.setDuration(3000);
                animator.addUpdateListener(this);
                animator.setInterpolator(new DecelerateInterpolator());
                animators.add(animator);
            }

            AnimatorSet set = new AnimatorSet();
//        set.playSequentially(animators);
            set.playTogether(animators);
            set.start();

        }
    }


    public MyRectF[] getData() {
        return data;
    }

    public void setData(MyRectF[] data) {
        this.data = data;
    }

    /**
     * 为RectF增加get/set 添加动画能力
     */
    public static class MyRectF extends RectF {

        public MyRectF(float left, float top, float right, float bottom) {
            super(left, top, right, bottom);
        }

        public float getTop() {
            return super.top;
        }

        public void setTop(float top) {
            super.top = top;
        }
    }
}
