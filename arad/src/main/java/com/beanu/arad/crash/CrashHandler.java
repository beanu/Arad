package com.beanu.arad.crash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Process;


/**
 * Created by lizhihua on 2016/11/5.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";

    private static CrashHandler sInstance = new CrashHandler();

    //系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private boolean mIsDisabled = false;

    private Context mContext;

    //默认Crash处理Activity
    private Class mDefaultCrashActivity = DefaultCrashActivity.class;

    //存储错误日志文件夹
    private String mLogFolder;
    //错误接受服务器地址
    private String mDebugServer;

    //构造方法私有，防止外部构造多个实例，即采用单例模式
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    //这里主要完成初始化工作
    public void init(Context context) {
        //获取系统默认的异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //获取Context，方便内部使用
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //如果禁用了自定义的CrashHandler，便调用默认的CrashHandler
        if (mIsDisabled) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
            return;
        }

        //跳转到处理错误的页面
        Intent intent = new Intent(mContext, mDefaultCrashActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(DefaultCrashActivity.EXTRA_EX, ex);
        intent.putExtra(DefaultCrashActivity.LOG_FOLDER, mLogFolder);
        intent.putExtra(DefaultCrashActivity.DEBUG_SERVER, mDebugServer);
        mContext.startActivity(intent);

        //结束当前进程
        Process.killProcess(Process.myPid());
    }

    public void setCrashActivity(Class<? extends Activity> activityClass) {
        mDefaultCrashActivity = activityClass;
    }

    public String getDebugServer() {
        return mDebugServer;
    }

    public void setDebugServer(String mDebugServer) {
        this.mDebugServer = mDebugServer;
    }

    public String getLogFolder() {
        return mLogFolder;
    }

    public void setLogFolder(String mLogFolder) {
        this.mLogFolder = mLogFolder;
    }

    public void disable() {
        mIsDisabled = true;
    }

    public void enable() {
        mIsDisabled = false;
    }
}