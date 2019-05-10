package com.beanu.arad;


/**
 * 全局配置
 *
 * @author beanu
 */
public class AradApplicationConfig {

    /**
     * SharedPreference轻量级存储 路径在/data/data/PACKAGE_NAME/shared_prefs
     */
    public String preferencesName = "arad_preference";


    /**
     * 如果SD卡存在，则存在SD/Android/PACKAGE_NAME/cache/aradCache
     * 如果没有SD卡，则存在/data/data/PACKAGE_NAME/cache/aradCache
     */
    public String imageCacheFolder = "aradCache";

    public boolean debug = BuildConfig.DEBUG;

}
