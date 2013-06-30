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

/**
 * 设备信息，主要用于服务器端设备信息的统计
 * 
 * @author Steven.Luo
 * 
 */
public class DeviceInformant {

	private String deviceMode;
	private String osSystem;
	private String osSystemVer;
	private String romVer;
	private int appVerCode;
	private String countryCode;
	private String deviceID;
	private String phoneNumber;
	private int screenHeight;
	private int screenWidth;
	private int densityDpi;
	private String version;

	public DeviceInformant(Context context) {
		try {
			osSystem = "Android";
			osSystemVer = Build.VERSION.RELEASE;
			deviceMode = Build.MODEL;
			romVer = Build.FINGERPRINT;

			version = AndroidUtil.getVerName(context);
			deviceID = AndroidUtil.getDeviceId(context);
			phoneNumber=AndroidUtil.getPhoneNumber(context);
			appVerCode = AndroidUtil.getVerCode(context);
			screenWidth = context.getResources().getDisplayMetrics().widthPixels;
			screenHeight = context.getResources().getDisplayMetrics().heightPixels;
			densityDpi = context.getResources().getDisplayMetrics().densityDpi;

			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			countryCode = tm == null ? "" : (tm.getSimCountryIso() == null ? "" : tm.getSimCountryIso().toUpperCase());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Device Info Error", e);
		}
	}

	public String getDeviceMode() {
		return deviceMode;
	}

	public void setDeviceMode(String deviceMode) {
		this.deviceMode = deviceMode;
	}

	public String getOsSystem() {
		return osSystem;
	}

	public void setOsSystem(String osSystem) {
		this.osSystem = osSystem;
	}

	public String getOsSystemVer() {
		return osSystemVer;
	}

	public void setOsSystemVer(String osSystemVer) {
		this.osSystemVer = osSystemVer;
	}

	public String getRomVer() {
		return romVer;
	}

	public void setRomVer(String romVer) {
		this.romVer = romVer;
	}

	public int getAppVerCode() {
		return appVerCode;
	}

	public void setAppVerCode(int appVerCode) {
		this.appVerCode = appVerCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getDensityDpi() {
		return densityDpi;
	}

	public void setDensityDpi(int densityDpi) {
		this.densityDpi = densityDpi;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
