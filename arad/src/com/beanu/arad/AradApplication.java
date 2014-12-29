package com.beanu.arad;

import android.app.Application;
import android.content.Context;

import com.beanu.arad.utils.DeviceInformant;
import com.bugsnag.android.Bugsnag;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;

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
        Arad.http = Http.instance();
        Arad.imageLoader = Picasso.with(getApplicationContext());
        Arad.preferences = new Preferences(getSharedPreferences(config.preferencesName, Context.MODE_PRIVATE));
        deviceInfo = new DeviceInformant(getApplicationContext());
        Arad.bus = new EventBus();

        /**
         * bug日志在线收集
         * 账号注册地址https://bugsnag.com
         * 现在bugsnag的android版本有大神loopj维护
         */
        Bugsnag.init(this, "d56179c1199b428569f9e606eeceafe7");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    protected abstract AradApplicationConfig appConfig();
}
