package com.beanu.demo;

import com.arad.AradApplication;
import com.arad.AradApplicationConfig;

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
