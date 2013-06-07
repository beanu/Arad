package com.beanu.arad.widget.pulltorefresh;

import android.annotation.TargetApi;
import android.view.View;

@TargetApi(16)
public class SDK16 {

	public static void postOnAnimation(View view, Runnable runnable) {
		view.postOnAnimation(runnable);
	}

}
