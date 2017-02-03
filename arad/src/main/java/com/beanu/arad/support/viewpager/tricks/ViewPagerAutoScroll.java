package com.beanu.arad.support.viewpager.tricks;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * view pager自动滚动
 * Created by yunhe on 2015/5/27.
 */
public class ViewPagerAutoScroll {

    private ViewPager mViewPager;

    //banner自动滑动
    private int currentItem = 0;
    private boolean play = false;
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture mFuture;

    //翻页延时时间 和 周期事件
    private long initialDelay = 3, period = 5;

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
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    }

    public ViewPagerAutoScroll(long initialDelay, long period) {
        this.initialDelay = initialDelay;
        this.period = period;

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }


    public void start() {
        if (!play) {
            // 当Activity显示出来后，每五秒钟切换一次图片显示
            mFuture = scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), initialDelay, period, TimeUnit.SECONDS);
            play = true;
        }
    }

    /**
     * 停止播放，可以重复start
     */
    public void stop() {
        if (play) {
            if (mFuture != null && !mFuture.isCancelled()) {
                mFuture.cancel(true);
            }
            play = false;
        }
    }

    /**
     * 停止播放，不可以再此开启
     */
    public void destory() {
        if (play) {
            if (!scheduledExecutorService.isShutdown()) {
                scheduledExecutorService.shutdown();
            }
            play = false;
        }
    }

}
