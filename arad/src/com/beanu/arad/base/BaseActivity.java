package com.beanu.arad.base;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class BaseActivity extends SherlockFragmentActivity implements OnGestureListener {

	private GestureDetector mDetector;
	private boolean mEnableGD;
	private SlidingEventListener slidingListener;

	/**
	 * 设置左右滑动的事件,前提是enableSlideGestureDetector(true)
	 */
	public interface SlidingEventListener {
		void leftSlidingEvent();

		void rightSlidingEvent();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mEnableGD = false;
		mDetector = new GestureDetector(this, this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:// ActionBar左方的返回按钮，需要覆写
			this.finish();
			if (mEnableGD && slidingListener != null)
				slidingListener.rightSlidingEvent();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mEnableGD)
			return mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * 是否设置左右滑动监听
	 * 
	 * @param enable
	 */
	public void enableSlideGestureDetector(boolean enable) {
		mEnableGD = enable;
	}

	public void setSlidingEventListener(SlidingEventListener listener) {
		this.slidingListener = listener;
	}

	/**
	 * ***********************************************************************
	 * *************************OnGestureListener*****************************
	 */

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1.getX() - e2.getX() > 50 && Math.abs(velocityX) > 0) {
			if (slidingListener != null && mEnableGD) {
				slidingListener.leftSlidingEvent();
			}
		} else if (e2.getX() - e1.getX() > 50 && Math.abs(velocityX) > 0) {
			if (slidingListener != null && mEnableGD) {
				slidingListener.rightSlidingEvent();
			}
		}

		return false;
	}

}
