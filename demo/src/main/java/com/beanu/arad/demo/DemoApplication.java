package com.beanu.arad.demo;

import android.content.Context;

import com.beanu.arad.AradApplication;
import com.beanu.arad.AradApplicationConfig;

import androidx.multidex.MultiDex;

public class DemoApplication extends AradApplication {
    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
