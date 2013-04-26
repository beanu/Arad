package com.beanu.arad;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import android.app.Application;

public class AradApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Arad.app = this;
		Arad.db = FinalDb.create(getApplicationContext());
		Arad.http = new FinalHttp();
		Arad.imageLoader = FinalBitmap.create(getApplicationContext());
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

}
