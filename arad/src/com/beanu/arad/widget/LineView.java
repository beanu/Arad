package com.beanu.arad.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * 带有动画的线段
 * 提供一组点数据，从头到尾以动画的形式绘制出来
 * Created by beanu on 14-1-4.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LineView extends View implements ValueAnimator.AnimatorUpdateListener {

    private Paint paint;
    private Path path;
    private float currentX;
    private float currentY;

    private float[] dataX;
    private float[] dataY;

    private AnimatorSet animatorSet;

    public LineView(Context context) {
        super(context);
        init();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.WHITE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        path = new Path();
    }


    /**
     * 开始显示数据的动画（横向划线）
     */
    public void startAnimation() {
        if (dataX != null && dataY != null) {
            path.reset();
            path.moveTo(dataX[0], dataY[0]);
            setCurrentX(dataX[0]);
            setCurrentY(dataY[0]);

            ValueAnimator animatorX = ObjectAnimator.ofFloat(this, "currentX", dataX);
            animatorX.setDuration(2000);
            animatorX.setInterpolator(new DecelerateInterpolator());

            ValueAnimator animatorY = ObjectAnimator.ofFloat(this, "currentY", dataY);
            animatorY.setDuration(2000);
            animatorY.setInterpolator(new DecelerateInterpolator());
            animatorY.addUpdateListener(this);

            if (animatorSet != null)
                if (animatorSet.isRunning())
                    animatorSet.cancel();

            animatorSet = new AnimatorSet();
            animatorSet.playTogether(animatorX, animatorY);
            animatorSet.start();
        }
    }


    /**
     * //TODO
     * 重新显示数据的动画（上下跳动）
     */
    public void startAnimationChange() {
        if (dataX != null && dataY != null) {
            path.rewind();
            path.moveTo(dataX[0], dataY[0]);
            setCurrentY(dataY[0]);

            ValueAnimator animatorY = ObjectAnimator.ofFloat(this, "currentY", dataY);
            animatorY.setDuration(2000);
            animatorY.setInterpolator(new DecelerateInterpolator());
            animatorY.addUpdateListener(this);

            if (animatorSet != null)
                if (animatorSet.isRunning())
                    animatorSet.cancel();

            animatorSet = new AnimatorSet();
            animatorSet.playTogether(animatorY);
            animatorSet.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(8, 8);
        canvas.drawPath(path, paint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {

        Log.d("TEST", "currentX:" + currentX + " currentY:" + currentY);
        path.lineTo(currentX, currentY);
        invalidate();
    }


    public float getCurrentX() {
        return currentX;
    }

    public void setCurrentX(float currentX) {
        this.currentX = currentX;
    }

    public float getCurrentY() {
        return currentY;
    }

    public void setCurrentY(float currentY) {
        this.currentY = currentY;
    }

    public float[] getDataX() {
        return dataX;
    }

    public void setDataX(float[] dataX) {
        this.dataX = dataX;
    }

    public float[] getDataY() {
        return dataY;
    }

    public void setDataY(float[] dataY) {
        this.dataY = dataY;
    }
}
