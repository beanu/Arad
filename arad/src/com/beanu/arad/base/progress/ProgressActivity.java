package com.beanu.arad.base.progress;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;

import com.beanu.arad.R;

public class ProgressActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 是否显示内容
	 */
	public void setContentShown(boolean shown) {
		View view = findViewById(R.id.root_progress);
		if (view == null) {
			ensureContent();
		}

		if (shown) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	/** 给Activity添加一个progressBar */
	private void ensureContent() {
		ProgressBar progressBar = new ProgressBar(this);
		progressBar.setId(R.id.root_progress);
		LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.gravity = Gravity.CENTER;
		progressBar.setLayoutParams(param);
		addContentView(progressBar, param);
	}

}
