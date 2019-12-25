package com.beanu.arad;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.Stack;

/**
 * @author Beanu
 */
public class ActivityManager implements Application.ActivityLifecycleCallbacks {

    //Activity栈
    private Stack<Activity> activities = new Stack<>();
    private static ActivityManager activityManager;

    private ActivityManager() {
    }

    /**
     * 单例
     *
     * @return activityManager instance
     */
    public static ActivityManager getInstance() {
        if (activityManager == null) {
            synchronized (ActivityManager.class) {
                if (activityManager == null) {
                    activityManager = new ActivityManager();
                }
            }
        }

        return activityManager;
    }

    /**
     * 获取Activity任务栈
     *
     * @return activity stack
     */
    public Stack<Activity> getActivityStack() {
        return activities;
    }

    /**
     * Activity 入栈
     *
     * @param activity Activity
     */
    private void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Activity出栈
     *
     * @param activity Activity
     */
    private void removeActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    /**
     * 结束某Activity
     *
     * @param activity Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 获取当前Activity
     *
     * @return current activity
     */
    public Activity getCurrentActivity() {
        return activities.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishCurrentActivity() {
        finishActivity(activities.lastElement());
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activities != null) {
            for (int i = 0, size = activities.size(); i < size; i++) {
                if (null != activities.get(i)) {
                    activities.get(i).finish();
                }
            }
            activities.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityMgr != null) {
                activityMgr.killBackgroundProcesses(context.getPackageName());
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        removeActivity(activity);
    }

}

