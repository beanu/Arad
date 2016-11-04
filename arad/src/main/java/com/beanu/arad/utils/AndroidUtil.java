/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beanu.arad.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.List;

/**
 * 
 * @author Justin Yang
 */
public class AndroidUtil {

	/**
	 * Dp float value xform to Px int value
	 * 
	 * @param context
	 * @param dpValue
	 * @return int px value
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int getVerCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (Exception e) {
			Log.e("Arad","Cannot find package and its version info.");
			return -1;
		}
	}

	public static String getVerName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (Exception e) {
			Log.e("Arad","Cannot find package and its version info.");
			return "no version name";
		}
	}

	// Imsi
	public static String getDeviceImsi(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			return "null";
		} else {
			String id = tm.getSubscriberId();
			return id == null ? "null" : id;
		}
	}

	/**
	 * 获取DeviceId 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID.
	 * 
	 * @param context
	 * @return 当获取到的TelephonyManager为null时，将返回"null"
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			return "null";
		} else {
			String id = tm.getDeviceId();
			return id == null ? "null" : id;
		}
	}

	/**
	 * 获取PhoneNumber
	 * 
	 * @param context
	 * @return 当获取到的TelephonyManager为null时，将返回"null"
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm == null) {
			return "null";
		} else {
			String number = tm.getLine1Number();
			return number == null ? "null" : number;
		}
	}

	/**
	 * 显示或隐藏IME
	 * 
	 * @param context
	 * @param bHide
	 */
	public static void hideIME(Activity context, boolean bHide) {
		if (bHide) {
			try {
				((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
						context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		} else { // show IME
			try {
				((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE)).showSoftInput(
						context.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
			} catch (NullPointerException npe) {
				npe.printStackTrace();
			}
		}
	}

	/**
	 * 在dialog开启前确定需要开启后跳出IME
	 * 
	 * @param dialog
	 */
	public static void showIMEonDialog(AlertDialog dialog) {
		try {
			Window window = dialog.getWindow();
			window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		} catch (Exception e) {
			Log.e("Arad",e.toString());
		}
	}

	/**
	 * 判断一个apk是否安装
	 * 
	 * @param ctx
	 * @param packageName
	 * @return
	 */
	public static boolean isPkgInstalled(Context ctx, String packageName) {
		PackageManager pm = ctx.getPackageManager();
		try {
			pm.getPackageInfo(packageName, 0);
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
		return true;
	}

	public static boolean isGooglePlayInstalled(Context ctx) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://search?q=foo"));
		PackageManager pm = ctx.getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void installApkWithPrompt(File apkFile, Context context) {
		Intent promptInstall = new Intent(Intent.ACTION_VIEW);
		promptInstall.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		context.startActivity(promptInstall);
	}

	/**
	 * @param context
	 *            used to check the device version and DownloadManager
	 *            information
	 * @return true if the download manager is available
	 */
	public static boolean isDownloadManagerAvailable(Context context) {
		try {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
				return false;
			}
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
			List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
			return list.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean networkStatusOK(final Context context) {
		boolean netStatus = false;

		try {
			ConnectivityManager connectManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectManager.getActiveNetworkInfo();
			if (activeNetworkInfo != null) {
				if (activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
					netStatus = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return netStatus;
	}

	/**
	 * 网络未连接时，调用设置方法
	 */
	public static void setNetwork(final Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("网络提示信息");
		builder.setMessage("网络不可用，如果继续，请先设置网络！");
		builder.setPositiveButton("设置", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
				/**
				 * 判断手机系统的版本！如果API大于10 就是3.0 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
				 */
				if (Build.VERSION.SDK_INT > 10) {
					intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
				} else {
					intent = new Intent();
					ComponentName component = new ComponentName("com.android.settings",
							"com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				context.startActivity(intent);
			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create();
		builder.show();
	}

	/**
	 * 网络已经连接，然后去判断是wifi连接还是GPRS连接 设置一些自己的逻辑调用
	 */
	public static boolean isWifiNetworkAvailable(Context context) {
		if (networkStatusOK(context)) {
			ConnectivityManager connectManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			State gprs = connectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			State wifi = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
			if (gprs == State.CONNECTED || gprs == State.CONNECTING) {
				return false;
			}
			// 判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
			if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
				return true;
			}
		} else {
			setNetwork(context);
		}
		return false;
	}

	/** 打电话拨号 */
	public static void dial(Context ctx, String telephone) {
		try {
			if (telephone != null && !telephone.equals("")) {
				Uri uri = Uri.parse("tel:" + telephone);
				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
				ctx.startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void shareText(Context context, String title, String text) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		context.startActivity(Intent.createChooser(intent, title));
	}

	public static boolean isActionBarAvailable() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return true;
		}
		return false;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static boolean hasJellyBeanMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isHoneycombTablet(Context context) {
		return hasHoneycomb() && isTablet(context);
	}

	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}
}
