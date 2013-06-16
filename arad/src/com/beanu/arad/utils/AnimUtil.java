package com.beanu.arad.utils;

import android.app.Activity;

import com.beanu.arad.R;

public class AnimUtil {

	/** slip in */
	public static void intentSlidIn(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	/** Slip off */
	public static void intentSlidOut(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
