package com.beanu.arad.utils;

import android.content.ComponentCallbacks2;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.collection.ArrayMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;


/**
 * Created by lizhihua on 2017/2/8.
 */

public class FragmentSwitcherUtils {
    private FragmentManager manager;
    private int containerViewId;
    private Fragment currentFragment;

    private final List<Fragment> lruFragmentList = new ArrayList<>();
    private final Map<String, Fragment> fragmentMap = new WeakHashMap<>();
    private final Map<String, FragmentLazyProvider> providerMap = new ArrayMap<>();

    public FragmentSwitcherUtils(FragmentManager manager, @IdRes int containerViewId) {
        this.manager = manager;
        this.containerViewId = containerViewId;
    }

    public FragmentSwitcherUtils addFragment(String tag, Fragment fragment) {
        fragmentMap.put(tag, fragment);
        return this;
    }

    public FragmentSwitcherUtils addFragment(String tag, FragmentLazyProvider provider) {
        providerMap.put(tag, provider);
        return this;
    }

    public void showFragment(String tag) {
        FragmentTransaction transition = manager.beginTransaction();
        try {
            Fragment fragment = manager.findFragmentByTag(tag);
            if(currentFragment != null){
                if (fragment == currentFragment){
                    lruFragmentList.remove(fragment);
                    return;
                }
                transition.hide(currentFragment);
                moveFragmentToLast(currentFragment);
            }
            if (fragment == null) {
                fragment = findFragmentByTag(tag);
                transition.add(containerViewId, fragment, tag);
            } else {
                transition.show(fragment);
            }
            currentFragment = fragment;
            lruFragmentList.remove(fragment);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } finally {
            transition.commit();
        }
    }

    public void hideFragment(String tag) {
        FragmentTransaction transition = manager.beginTransaction();
        try {
            Fragment fragment = manager.findFragmentByTag(tag);
            if (fragment != null) {
                transition.hide(fragment);
                moveFragmentToLast(fragment);
            }
        } finally {
            transition.commit();
        }
    }

    /**
     * 如果要使用该方法，所有 Fragment 必须使用 FragmentLazyProvider 注册，否则可能会移除注册的 Fragment 后没办法加载新的
     */
    public void onTrimMemory(int level) {
        Fragment fragment = lruFragmentList.size() > 0 ? lruFragmentList.get(0) : null;
        //没有隐藏的Fragment
        if (fragment == null){
            return;
        }
        //移除在视图上隐藏的Fragment，用弱引用判断是否该回收这个fragment
        if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL){
            manager.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
        //从map移除这个Fragment
        if (level >= ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN){
            Set<Map.Entry<String, Fragment>> set = fragmentMap.entrySet();
            for (Map.Entry<String, Fragment> entry : set) {
                if (entry.getValue() == fragment){
                    fragmentMap.remove(entry.getKey());
                    lruFragmentList.remove(fragment);
                    break;
                }
            }
        }
    }

    private Fragment findFragmentByTag(String tag) throws IllegalAccessException {
        Fragment fragment = fragmentMap.get(tag);
        if (fragment != null){
            return fragment;
        }

        FragmentLazyProvider provider = providerMap.get(tag);
        if (provider != null){
            fragment = provider.getFragment();
            if (fragment != null){
                addFragment(tag, fragment);
                return fragment;
            }
        }

        throw new IllegalAccessException("did not find Fragment by " + tag);
    }

    private void moveFragmentToLast(@NonNull Fragment fragment){
        lruFragmentList.remove(fragment);
        lruFragmentList.add(fragment);
    }

    @FunctionalInterface
    public interface FragmentLazyProvider {
        Fragment getFragment();
    }
}
