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

    /**
     * 如果SD卡存在，则存在SD/Android/PACKAGE_NAME/files/aradLog
     * 如果没有SD卡，则存在/data/data/PACKAGE_NAME/files/aradLog
     * 如果将此项设为 null 或 空字符串，则不在本机上存储日志
     */
    public String logFolder = "aradLog";

    /**
     * 如果开启了CrashHandler并设置了此项，就会将错误信息以字符流的形式传到此服务器上
     */
    public String debugServer = "";

}
