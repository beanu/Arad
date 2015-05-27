package com.beanu.arad.support.viewpager.tricks;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * view pager自动滚动
 * Created by yunhe on 2015/5/27.
 */
public class ViewPagerAutoScroll {

    ViewPager mViewPager;

    //banner自动滑动
    private int currentItem = 0;
    private boolean play = false;
    private ScheduledExecutorService scheduledExecutorService;

    //handle
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            try {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(currentItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    });

    private class ScrollTask implements Runnable {
        public void run() {
            if (mViewPager != null && mViewPager.getAdapter() != null) {
                int pageCount = mViewPager.getAdapter().getCount();
                synchronized (mViewPager) {
                    if (pageCount != 0) {
                        currentItem = (mViewPager.getCurrentItem() + 1) % pageCount;
                    }
                    handler.obtainMessage().sendToTarget();

                }
            }
        }
    }

    public ViewPagerAutoScroll() {
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }


    public void start() {
        if (!play) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            // 当Activity显示出来后，每两秒钟切换一次图片显示
            scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 5, TimeUnit.SECONDS);
            play = true;
        }
    }

    public void stop() {
        if (play) {
            // 当Activity不可见的时候停止切换
            scheduledExecutorService.shutdown();
            play = false;
        }
    }

}
