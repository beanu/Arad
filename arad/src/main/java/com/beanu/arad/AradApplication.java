package com.beanu.arad;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.beanu.arad.utils.DeviceInformant;
import com.beanu.arad.utils.Utils;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author Beanu
 */
public abstract class AradApplication extends Application {

    public DeviceInformant deviceInfo;
    public AradApplicationConfig config;

    protected String processName;

    @Override
    public void onCreate() {
        super.onCreate();

        processName = getProcessName(android.os.Process.myPid());

        if (getApplicationContext().getPackageName().equals(processName)) {
            //只在主进程中，初始化一次
            Utils.init(getApplicationContext());

            config = appConfig();
            Arad.app = this;
            Arad.db = DB.getInstance(getApplicationContext(), config.debug);
            Arad.preferences = new Preferences(getSharedPreferences(config.preferencesName, Context.MODE_PRIVATE));
            Arad.kv = new MMKVStorage(getApplicationContext());
            deviceInfo = new DeviceInformant(getApplicationContext());
            Arad.bus = EventBus.getDefault();

            registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
        }


    }

    /**
     * 应用程序初始化配置
     *
     * @return 基础配置
     */
    protected abstract AradApplicationConfig appConfig();

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
