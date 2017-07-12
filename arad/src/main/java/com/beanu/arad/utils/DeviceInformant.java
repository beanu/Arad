/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beanu.arad.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 设备信息，主要用于服务器端设备信息的统计
 *
 * @author Steven.Luo
 */
public class DeviceInformant {

    private String osSystem;// 手机操作系统
    private String osSystemVer;// 手机系统版本

    private String deviceMode;// 设备型号

    private int versionCode;
    private String versionName;

    private String countryCode;
    private String deviceID;

    private String deviceImsi;// SIM卡的IMSI码
    private int phoneType;

    private int screenHeight;
    private int screenWidth;
    private int densityDpi;


    public DeviceInformant(Context context) {
        try {
            osSystem = "Android";
            osSystemVer = Build.VERSION.RELEASE;
            deviceMode = Build.MODEL;

            screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            screenHeight = context.getResources().getDisplayMetrics().heightPixels;
            densityDpi = context.getResources().getDisplayMetrics().densityDpi;

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            countryCode = tm == null ? "" : (tm.getSimCountryIso() == null ? "" : tm.getSimCountryIso().toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR", "Device Info Error");
        }
    }

    public String getDeviceImsi() {
        if (StringUtils.isEmpty(deviceImsi))
            deviceImsi = PhoneUtils.getIMSI();
        return deviceImsi;
    }

    public String getOsSystem() {
        return osSystem;
    }

    public String getOsSystemVer() {
        return osSystemVer;
    }

    public String getDeviceMode() {
        return deviceMode;
    }

    public int getVersionCode() {
        if (versionCode == 0)
            versionCode = AppUtils.getAppVersionCode();
        return versionCode;
    }

    public String getVersionName() {
        if (StringUtils.isEmpty(versionName))
            versionName = AppUtils.getAppVersionName();
        return versionName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getDeviceID() {
        if (StringUtils.isEmpty(deviceID))
            deviceID = DeviceUtils.getAndroidID();
        return deviceID;
    }

    public int getPhoneType() {
        phoneType = PhoneUtils.getPhoneType();
        return phoneType;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

}
