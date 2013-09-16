package com.beanu.arad.base;

import android.view.MenuItem;

import com.beanu.arad.base.progress.ProgressActivity;

/**
 * 检测Actionbar的home 事件
 * 
 * @author beanu
 * 
 */
public class BaseActivity extends ProgressActivity {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:// ActionBar左方的返回按钮，需要覆写
			this.finish();// TODO 这里应该是UP操作，不是back操作
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
