package com.beanu.arad;

import com.beanu.arad.utils.DeviceInformant;

import android.app.Application;

public class AradApplication extends Application {

	public DeviceInformant deviceInfo;

	@Override
	public void onCreate() {
		super.onCreate();
		Arad.app = this;
		Arad.db = DB.getInstance(getApplicationContext());
		Arad.http = Http.create();
		Arad.imageLoader = ImageLoader.getInstance(getApplicationContext());
		deviceInfo = new DeviceInformant(getApplicationContext());
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

}
