package com.arad;

import android.app.Application;
import android.content.Context;

import com.arad.utils.DeviceInformant;

import org.greenrobot.eventbus.EventBus;


public abstract class AradApplication extends Application {

    public DeviceInformant deviceInfo;
    public AradApplicationConfig config;


    @Override
    public void onCreate() {
        super.onCreate();
        config = appConfig();
        config.daoConfig.setContext(getApplicationContext());

        Arad.app = this;
        Arad.db = DataBase.getInstance(getApplicationContext());
        Arad.preferences = new Preferences(getSharedPreferences(config.preferencesName, Context.MODE_PRIVATE));
        deviceInfo = new DeviceInformant(getApplicationContext());
        Arad.bus = EventBus.getDefault();
//
//        this.mMainThreadHandler = new Handler();
//        this.mMainThreadLooper = getMainLooper();
//        this.mMainThead = Thread.currentThread();
//        this.mMainTheadId = android.os.Process.myTid();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    protected abstract AradApplicationConfig appConfig();
}
