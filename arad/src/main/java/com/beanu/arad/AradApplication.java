package com.beanu.arad;

import android.app.Application;
import android.content.Context;

import com.beanu.arad.utils.DeviceInformant;

import org.greenrobot.eventbus.EventBus;


public abstract class AradApplication extends Application {

    public DeviceInformant deviceInfo;
    public AradApplicationConfig config;


    @Override
    public void onCreate() {
        super.onCreate();
        config = appConfig();

        Arad.app = this;
        Arad.db = DB.getInstance(getApplicationContext());
        Arad.preferences = new Preferences(getSharedPreferences(config.preferencesName, Context.MODE_PRIVATE));
        deviceInfo = new DeviceInformant(getApplicationContext());
        Arad.bus = EventBus.getDefault();
    }

    protected abstract AradApplicationConfig appConfig();
}
