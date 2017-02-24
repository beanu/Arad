package com.beanu.arad;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.beanu.arad.crash.CrashHandler;
import com.beanu.arad.support.slideback.ActivityHelper;
import com.beanu.arad.utils.DeviceInformant;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public abstract class AradApplication extends Application {

    public DeviceInformant deviceInfo;
    public AradApplicationConfig config;

    protected String processName;

    public static ActivityHelper activityHelper;


    @Override
    public void onCreate() {
        super.onCreate();

        processName = getProcessName(android.os.Process.myPid());

        if (getApplicationContext().getPackageName().equals(processName)) {
            //只在主进程中，初始化一次

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


    }

    protected abstract AradApplicationConfig appConfig();

    public void disableCrashHandler() {
        CrashHandler.getInstance().disable();
    }

    public void enableCrashHandler() {
        CrashHandler.getInstance().enable();
    }

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
