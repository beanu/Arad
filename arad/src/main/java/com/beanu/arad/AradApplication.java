package com.beanu.arad;

import android.app.Application;
import android.content.Context;

import com.beanu.arad.crash.CrashHandler;
import com.beanu.arad.support.slideback.ActivityHelper;
import com.beanu.arad.utils.DeviceInformant;

import org.greenrobot.eventbus.EventBus;


public abstract class AradApplication extends Application {

    public DeviceInformant deviceInfo;
    public AradApplicationConfig config;

    public static ActivityHelper activityHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        config = appConfig();

        Arad.app = this;
        Arad.db = DB.getInstance(getApplicationContext());
        Arad.preferences = new Preferences(getSharedPreferences(config.preferencesName, Context.MODE_PRIVATE));
        deviceInfo = new DeviceInformant(getApplicationContext());
        Arad.bus = EventBus.getDefault();

        //开启CrashHandler
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        crashHandler.setLogFolder(config.logFolder);
        crashHandler.setDebugServer(config.debugServer);

        //开启侧滑支持需要此Helper类支持
        activityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(activityHelper);
    }

    protected abstract AradApplicationConfig appConfig();

    public void disableCrashHandler(){
        CrashHandler.getInstance().disable();
    }

    public void enableCrashHandler(){
        CrashHandler.getInstance().enable();
    }
}
