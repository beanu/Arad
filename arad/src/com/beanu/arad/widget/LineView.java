package com.beanu.arad.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.os.Build;
import android.util.AttributeSet;
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
            animatorX.setInterpolator(new LinearInterpolator());

            ValueAnimator animatorY = ObjectAnimator.ofFloat(this, "currentY", dataY);
            animatorY.setDuration(2000);
            animatorY.setInterpolator(new LinearInterpolator());
            animatorY.addUpdateListener(this);

            if (animatorSet != null)
                if (animatorSet.isRunning())
                    animatorSet.cancel();

            animatorSet = new AnimatorSet();
            animatorSet.playTogether(animatorX, animatorY);
            animatorSet.setStartDelay(1000);
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

    Paint p1 = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(8, 8);
        canvas.drawPath(path, paint);

        p1.setStyle(Paint.Style.STROKE);
        p1.setAntiAlias(true);
        p1.setColor(Color.WHITE);
        p1.setStrokeWidth(2);

        initTable();
        drawXLine(canvas, p1);
        drawYLine(canvas, p1);
        drawTable(canvas);

    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {

//        Log.d("TEST", "currentX:" + currentX + " currentY:" + currentY);
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

    // 默认边距
    private int Margin = 40;
    // 原点坐标
    private int Xpoint;
    private int Ypoint;
    // X,Y轴的单位长度
    private int Xscale = 20;
    private int Yscale = 20;
    // X,Y轴上面的显示文字
    private String[] Xlabel = {};
    private String[] Ylabel = {};

    public void setXlabel(String[] xlabel) {
        Xlabel = xlabel;
    }

    public void setYlabel(String[] ylabel) {
        Ylabel = ylabel;
    }

    // 画表格
    private void drawTable(Canvas canvas) {
        Paint paintText = new Paint();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        Path path = new Path();
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        paint.setPathEffect(effects);

        paintText.setColor(Color.WHITE);
        paintText.setTextSize(this.Margin / 2);
        canvas.drawText("月", Xpoint, Ypoint + this.Margin / 2 + this.Margin / 4, paintText);
//        canvas.drawText("张", Xpoint - (this.Margin / 2), Ypoint + this.Margin / 4, paintText);

        // 纵向线
        if (Xlabel.length != 0 && Ylabel.length != 0) {
            for (int i = 0; i < Xlabel.length; i++) {
                int startX = Xpoint + i * Xscale;
                int startY = Ypoint + Margin / 2;
//            int stopY = Ypoint - (this.Ylabel.length - 1) * Yscale;
//            path.moveTo(startX, startY);
//            path.lineTo(startX, stopY);
//            canvas.drawPath(path, paint);
                if (i % 2 == 0) {
                    paintText.setColor(Color.WHITE);
                    paintText.setTextSize(this.Margin / 2);
                    canvas.drawText(this.Xlabel[i], startX - this.Margin / 4, startY + this.Margin / 4, paintText);
                }
            }
            // 横向线
            for (int i = 0; i < Ylabel.length; i++) {
                int startX = Xpoint;
                int startY = Ypoint - i * Yscale;
                int stopX = Xpoint + (this.Xlabel.length - 1) * Xscale;
                path.moveTo(startX, startY);
                path.lineTo(stopX, startY);
                paint.setColor(Color.WHITE);
                canvas.drawPath(path, paint);


                paintText.setColor(Color.WHITE);
                paintText.setTextSize(this.Margin / 2);
                canvas.drawText(this.Ylabel[i], this.Margin / 4, startY
                        + this.Margin / 4, paintText);

            }
        }
    }

    // 画横纵轴
    private void drawXLine(Canvas canvas, Paint p) {
//        canvas.drawLine(Xpoint, Ypoint, this.Margin, this.Margin, p);
//        canvas.drawLine(Xpoint, this.Margin, Xpoint - Xpoint / 3, this.Margin
//                + this.Margin / 3, p);
//        canvas.drawLine(Xpoint, this.Margin, Xpoint + Xpoint / 3, this.Margin
//                + this.Margin / 3, p);
    }

    private void drawYLine(Canvas canvas, Paint p) {
//        canvas.drawLine(Xpoint, Ypoint, this.getWidth() - this.Margin, Ypoint,
//                p);
//        canvas.drawLine(this.getWidth() - this.Margin, Ypoint, this.getWidth()
//                - this.Margin - this.Margin / 3, Ypoint - this.Margin / 3, p);
//        canvas.drawLine(this.getWidth() - this.Margin, Ypoint, this.getWidth()
//                - this.Margin - this.Margin / 3, Ypoint + this.Margin / 3, p);
    }

    // 初始化数据值
    public void initTable() {
        Xpoint = this.Margin;
        Ypoint = this.getHeight() - this.Margin;
        Xscale = (this.getWidth() - 2 * this.Margin) / (this.Xlabel.length - 1);
        Yscale = (this.getHeight() - 2 * this.Margin)
                / (this.Ylabel.length - 1);
    }
}
