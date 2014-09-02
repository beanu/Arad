package com.beanu.arad;

import com.beanu.arad.http.HttpConfig;

import net.tsz.afinal.FinalDb.DaoConfig;

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
     * 数据库配置
     */
    public DaoConfig daoConfig = new DaoConfig();

    {
        daoConfig.setDbName("arad.db");
    }

    /**
     * 如果SD卡存在，则存在SD/Android/PACKAGE_NAME/cache/aradCache
     * 如果没有SD卡，则存在/data/data/PACKAGE_NAME/cache/aradCache
     */
    public String imageCacheFolder = "aradCache";

    public HttpConfig httpConfig = new HttpConfig();

}
