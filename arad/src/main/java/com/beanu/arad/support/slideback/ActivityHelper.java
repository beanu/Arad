package com.beanu.arad.support.slideback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.beanu.arad.base.BaseActivity;

import java.util.Stack;

/**
 * Created by Oubowu on 2016/9/20 3:28.
 */
public class ActivityHelper implements Application.ActivityLifecycleCallbacks {

    private static Stack<Activity> mActivityStack;

    public ActivityHelper() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);

        //如果Activity栈的元素个数至少为2个时，并且activity是BaseActivity的子类，则开启滑动返回
        if (mActivityStack.size() > 1) {
            if (activity instanceof BaseActivity) {
                ((BaseActivity) activity).enableSlideBack();
            }
        }
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
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //当前至少有2个Activity时
        if (mActivityStack.size() > 1) {
            //activity是栈底元素
            if (activity.equals(mActivityStack.get(0))) {
                Activity a = mActivityStack.get(1);//得到下标为1的元素
                if (a instanceof BaseActivity) { //如果是BaseActivity的子类则关闭滑动返回
                    ((BaseActivity) a).disableSlideBack();
                }
            }
        }

        mActivityStack.remove(activity);

        if (mListener != null) {
            mListener.onDestroy(activity);
        }
    }

    public Activity getPreActivity() {
        if (mActivityStack == null) {
            return null;
        }
        int size = mActivityStack.size();
        if (size < 2) {
            return null;
        }
        return mActivityStack.elementAt(size - 2);
    }

    public void finishAllActivity() {
        if (mActivityStack == null) {
            return;
        }
        for (Activity activity : mActivityStack) {
            activity.finish();
        }
    }

    public void printAllActivity() {
        if (mActivityStack == null) {
            return;
        }
        for (int i = 0; i < mActivityStack.size(); i++) {
            Log.e("TAG", "位置" + i + ": " + mActivityStack.get(i));
        }
    }

    @Deprecated
    void setOnActivityDestroyListener(OnActivityDestroyListener listener) {
        mListener = listener;
    }

    @Deprecated
    private OnActivityDestroyListener mListener;

    @Deprecated
    interface OnActivityDestroyListener {
        void onDestroy(Activity activity);
    }

}
