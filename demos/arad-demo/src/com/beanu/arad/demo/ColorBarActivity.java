package com.beanu.arad.demo;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.beanu.arad.base.BaseActivity;
import com.beanu.arad.support.ColorAnimationDrawable;

public class ColorBarActivity extends BaseActivity {
	private final Handler mHandler = new Handler();
	private ColorAnimationDrawable mActionBarBackground;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mActionBarBackground = new ColorAnimationDrawable();
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			mActionBarBackground.setCallback(mDrawableCallback);
		} else {
			getSupportActionBar().setBackgroundDrawable(mActionBarBackground);
		}
		mActionBarBackground.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mActionBarBackground.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mActionBarBackground.stop();
	}

	private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getSupportActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			mHandler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			mHandler.removeCallbacks(what);
		}
	};

}
