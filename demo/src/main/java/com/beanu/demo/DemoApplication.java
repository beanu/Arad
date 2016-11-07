package com.beanu.demo;

import com.beanu.arad.AradApplication;
import com.beanu.arad.AradApplicationConfig;
import com.beanu.arad.crash.CrashHandler;

import java.text.DateFormat;

/**
 * 全局入口
 * Created by Beanu on 16/8/2.
 */
public class DemoApplication extends AradApplication {
    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }
}
