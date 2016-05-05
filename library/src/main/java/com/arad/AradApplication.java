package com.arad;

import android.app.Application;
import android.content.Context;

import com.arad.utils.DeviceInformant;


public abstract class AradApplication extends Application {

    public DeviceInformant deviceInfo;
    public AradApplicationConfig config;

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
        Arad.bus = RxBus.getDefault();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    protected abstract AradApplicationConfig appConfig();
}
