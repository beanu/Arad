package com.beanu.arad.crash;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.beanu.arad.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DefaultCrashActivity extends Activity {
    private static final String TAG = "DefaultCrashActivity";

    public static final String EXTRA_EX = "Exception";

    public static final String LOG_FOLDER = "LOG_FOLDER";
    public static final String DEBUG_SERVER = "DEBUG_SERVER";

    private static final String FILE_NAME = "crash";
    //log文件的后缀名
    private static final String FILE_NAME_SUFFIX = ".trace";

    private String mLogFolder = "";
    private String mDebugServer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        Intent intent = getIntent();
        final Throwable ex = (Throwable) intent.getSerializableExtra(EXTRA_EX);
        mLogFolder = intent.getStringExtra(LOG_FOLDER);
        mDebugServer = intent.getStringExtra(DEBUG_SERVER);

        new AlertDialog.Builder(this).setTitle("系统提示")//设置对话框标题

                .setMessage("对不起，程序出现错误！")//设置显示的内容

                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        finish();
                    }
                }).show();//在按键响应事件中显示此对话框

        //将数据写入磁盘和上传网络
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dumpExceptionToSDCard(ex);
                    uploadExceptionToServer(ex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void dumpExceptionToSDCard(Throwable ex) throws IOException {
        if(TextUtils.isEmpty(mLogFolder)){
            return;
        }
        File dir;
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || !Environment.isExternalStorageRemovable()) {
            dir = getExternalFilesDir(null);
        } else {
            dir = getFilesDir();
        }
        if(dir == null){
            return;
        }
        File path = new File(dir.getAbsolutePath()+"/" +mLogFolder);
        if(!path.exists()){
            if(!path.mkdirs()) return;
        }

        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(current));
        //以当前时间创建log文件
        File file = new File(path.getAbsoluteFile()+"/" + FILE_NAME + time + FILE_NAME_SUFFIX);

        try {
            if(!file.createNewFile()) return;

            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //导出发生异常的时间
            pw.println(time);

            //导出手机信息
            dumpPhoneInfo(pw);

            pw.println();
            //导出异常的调用栈信息
            ex.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            Log.e(TAG, "dump crash info failed");
            e.printStackTrace();
        }
    }

    protected void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        //应用的版本名称和版本号
        PackageManager pm = getPackageManager();
        PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);

        //android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);

        //cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    protected void uploadExceptionToServer(Throwable ex) {
        //TODO 在此将错误信息上传服务器
    }
}
