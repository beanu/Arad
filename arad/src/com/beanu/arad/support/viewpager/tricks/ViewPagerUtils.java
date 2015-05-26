package com.beanu.arad.support.viewpager.tricks;

import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

/**
 * viewpager 工具类
 * Created by yunhe on 2015/5/26.
 */
public class ViewPagerUtils {


    /**
     * set the duration between two slider changes.
     *
     * @param period
     * @param interpolator
     */
    public static void setSliderTransformDuration(ViewPager viewPager, int period, Interpolator interpolator) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), interpolator, period);
            mScroller.set(viewPager, scroller);
        } catch (Exception e) {

        }
    }
}
