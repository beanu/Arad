package com.beanu.arad.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;

/**
 * 统一管理首页的导航Fragment(参考官方的FragmentTabHost)
 * Created by beanu on 15/2/13.
 */
public class FragmentsManager {

    private final FragmentActivity mActivity;
    private final int mContainerId;
    private final HashMap<String, FragmentInfo> mTabs = new HashMap<>();
    FragmentInfo mLastFragment;

    static final class FragmentInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        FragmentInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }

    }

    /**
     * @param activity    依附的主Activity
     * @param containerId 填充Fragment的Layout Id
     */
    public FragmentsManager(FragmentActivity activity, int containerId) {
        mActivity = activity;
        mContainerId = containerId;
    }

    public void addFragment(String tag, Class<?> clss, Bundle args) {

        FragmentInfo info = new FragmentInfo(tag, clss, args);

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state. If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
        if (info.fragment != null && !info.fragment.isDetached()) {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            ft.detach(info.fragment);
            ft.commit();
        }

        mTabs.put(tag, info);
    }

    public void switchFragment(String tag) {

        FragmentInfo newTab = mTabs.get(tag);
        if (mLastFragment != newTab) {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            if (mLastFragment != null) {
                if (mLastFragment.fragment != null) {
                    ft.detach(mLastFragment.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
                    ft.add(mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }

            mLastFragment = newTab;
            ft.commit();
            mActivity.getSupportFragmentManager().executePendingTransactions();
        }
    }

}
