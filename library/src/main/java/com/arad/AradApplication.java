package com.arad;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.arad.utils.DeviceInformant;

import org.greenrobot.eventbus.EventBus;


public abstract class AradApplication extends Application {

    public DeviceInformant deviceInfo;
    public AradApplicationConfig config;

    private static Thread mMainThead = null;
    private static int mMainTheadId;
    private static Handler mMainThreadHandler = null;
    private static Looper mMainThreadLooper = null;

    @Override
    public void onCreate() {
        super.onCreate();
        config = appConfig();
        config.daoConfig.setContext(getApplicationContext());

        Arad.app = this;
        Arad.db = DB.getInstance(config.daoConfig);
//        Arad.http = Http.instance();
        Arad.preferences = new Preferences(getSharedPreferences(config.preferencesName, Context.MODE_PRIVATE));
        deviceInfo = new DeviceInformant(getApplicationContext());
        Arad.bus = EventBus.getDefault();

        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        this.mMainTheadId = android.os.Process.myTid();
    }

    public static Thread getMainThread() {
        return mMainThead;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    protected abstract AradApplicationConfig appConfig();
}
