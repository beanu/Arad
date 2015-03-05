package com.beanu.arad.demo;

import com.beanu.arad.AradApplication;
import com.beanu.arad.AradApplicationConfig;
/**
 * 全局入口，初始化arad
 * Created by beanu on 14/11/19.
 */
public class DemoApplication extends AradApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }
}
