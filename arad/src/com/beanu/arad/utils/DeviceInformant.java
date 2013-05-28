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

import java.util.HashMap;
import java.util.Map;

/**
 * 设备信息，主要用于服务器端设备信息的统计
 * 
 * @author Steven.Luo
 * 
 */
public class DeviceInformant {
//	private static final String TAG = "DeviceInfo";

    private Map<String, String> paramMap = new HashMap<String, String>();

	private String clientName;
	private String deviceMode;
	private String osSystem;
	private String osSystemVer;
	private String romVer;
	private int appVerCode;
	private String countryCode;
	private String deviceID;
	private int screenHeight;
	private int screenWidth;
	private int densityDpi;
	private String version;

	public DeviceInformant(Context context) {
	    try {
	        clientName = "Alibaba.Android";
	        osSystem = "Android";
            osSystemVer = Build.VERSION.RELEASE;
            deviceMode = Build.MODEL;
            romVer = Build.FINGERPRINT;
            
    		version = AndroidUtil.getVerName(context);
    		deviceID = AndroidUtil.getDeviceId(context);
    		appVerCode = AndroidUtil.getVerCode(context);
    		screenWidth = context.getResources().getDisplayMetrics().widthPixels;
    		screenHeight = context.getResources().getDisplayMetrics().heightPixels;
    		densityDpi = context.getResources().getDisplayMetrics().densityDpi;
    
    		TelephonyManager tm = (TelephonyManager) context
    				.getSystemService(Context.TELEPHONY_SERVICE);
    		countryCode = tm == null ? "" : (tm.getSimCountryIso() == null ? ""
    				: tm.getSimCountryIso().toUpperCase());
	    } catch (Exception e) {
	        e.printStackTrace();
	        Log.e("Device Info Error", e);
	    }
	}

	/**
	 * 获取Post请求的键值对
	 * 
	 * @return
	 */
	public Map<String, String> getParamMap() {

		paramMap.put("clientName", clientName);
		paramMap.put("version", version);
		paramMap.put("countryCode", countryCode);
		paramMap.put("deviceID", deviceID);
		paramMap.put("appVerCode", String.valueOf(appVerCode));
		paramMap.put("osSystem", osSystem);
		paramMap.put("osSystemVer", osSystemVer);
		paramMap.put("deviceMode", deviceMode);
		paramMap.put("screenWidth", String.valueOf(screenWidth));
		paramMap.put("screenHeight", String.valueOf(screenHeight));
		paramMap.put("romVer", romVer);
		paramMap.put("densityDpi", String.valueOf(densityDpi));
		//paramMap.put("loginId", getLoginId());

		return paramMap;
	}
}
