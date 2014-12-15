package com.beanu.arad.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 嵌入到scrollView中,重新计算高度的GridView
 * Created by beanu on 14-9-10.
 */
public class MeasuredGridView extends GridView {

    public MeasuredGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasuredGridView(Context context) {
        super(context);
    }

    public MeasuredGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}