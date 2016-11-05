package com.beanu.arad.support.updateversion;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;

import com.beanu.arad.R;


public class UpdateChecker extends Fragment {

    private static final String NOTICE_TYPE_KEY = "type";
    public static final String APP_UPDATE_MESSAGE = "app_update_message";
    public static final String APP_UPDATE_URL = "app_update_url";
    private static final String APP_UPDATE_CODE = "app_update_code";

    private static final int NOTICE_NOTIFICATION = 2;
    private static final int NOTICE_DIALOG = 1;
    private static final String TAG = "UpdateChecker";


    private FragmentActivity mContext;
    private Thread mThread;
    private int mTypeOfNotice;

    /**
     * Show a Dialog if an update is available for download. Callable in a
     * FragmentActivity. Number of checks after the dialog will be shown:
     * default, 5
     *
     * @param fragmentActivity Required.
     */
    public static void checkForDialog(FragmentActivity fragmentActivity, String updateMessage, String apkUrl, int apkCode) {
        FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
        UpdateChecker updateChecker = new UpdateChecker();
        Bundle args = new Bundle();
        args.putInt(NOTICE_TYPE_KEY, NOTICE_DIALOG);
        args.putString(APP_UPDATE_MESSAGE, updateMessage);
        args.putString(APP_UPDATE_URL, apkUrl);
        args.putInt(APP_UPDATE_CODE, apkCode);

        //args.putInt(SUCCESSFUL_CHECKS_REQUIRED_KEY, 5);
        updateChecker.setArguments(args);
        content.add(updateChecker, null).commit();
    }


    /**
     * Show a Notification if an update is available for download. Callable in a
     * FragmentActivity Specify the number of checks after the notification will
     * be shown.
     *
     * @param fragmentActivity Required.
     */
    public static void checkForNotification(FragmentActivity fragmentActivity, String updateMessage, String apkUrl, int apkCode) {
        FragmentTransaction content = fragmentActivity.getSupportFragmentManager().beginTransaction();
        UpdateChecker updateChecker = new UpdateChecker();
        Bundle args = new Bundle();
        args.putInt(NOTICE_TYPE_KEY, NOTICE_NOTIFICATION);
        args.putString(APP_UPDATE_MESSAGE, updateMessage);
        args.putString(APP_UPDATE_URL, apkUrl);
        args.putInt(APP_UPDATE_CODE, apkCode);
        updateChecker.setArguments(args);
        content.add(updateChecker, null).commit();
    }


    /**
     * This class is a Fragment. Check for the method you have chosen.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (FragmentActivity) activity;
        Bundle args = getArguments();
        mTypeOfNotice = args.getInt(NOTICE_TYPE_KEY);
        String url = args.getString(APP_UPDATE_URL);
        String updateMessage = args.getString(APP_UPDATE_MESSAGE);
        int versionCode = args.getInt(APP_UPDATE_CODE);

        checkForUpdates(url, updateMessage, versionCode);
    }

    /**
     * Heart of the library. Check if an update is available for download
     * parsing the desktop Play Store page of the app
     */
    private void checkForUpdates(String apkUrl, String updateMessage, int apkCode) {

        try {
            int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
            if (apkCode > versionCode) {
                if (mTypeOfNotice == NOTICE_NOTIFICATION) {
                    showNotification(updateMessage, apkUrl);
                } else if (mTypeOfNotice == NOTICE_DIALOG) {
                    showDialog(updateMessage, apkUrl);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Show dialog
     */
    public void showDialog(String content, String apkUrl) {
        UpdateDialog d = new UpdateDialog();
        Bundle args = new Bundle();
        args.putString(APP_UPDATE_MESSAGE, content);
        args.putString(APP_UPDATE_URL, apkUrl);
        d.setArguments(args);
        d.show(mContext.getSupportFragmentManager(), null);
    }

    /**
     * Show Notification
     */
    public void showNotification(String content, String apkUrl) {
        Notification noti;
        Intent myIntent = new Intent(mContext, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(APP_UPDATE_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = mContext.getApplicationInfo().icon;
        noti = new NotificationCompat.Builder(mContext).setTicker(getString(R.string.arad_newUpdateAvailable))
                .setContentTitle(getString(R.string.arad_newUpdateAvailable)).setContentText(content).setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();

        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }


    /**
     * Check if a network available
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                connected = ni.isConnected();
            }
        }
        return connected;
    }


}
