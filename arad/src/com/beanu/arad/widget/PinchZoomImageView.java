package com.beanu.arad.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

public class PinchZoomImageView extends ImageView_ {
	enum Mode {
		NONE, DRAGING, // 拖动中
		ZOOMING // 缩放中
	}

	enum ZoomFlag {
		BIGGER, // 放大ing
		SMALLER // 缩小ing
	}

	private Mode mode = Mode.NONE; // 当前的事件

	private float beforeLenght; // 两触点距离
	private float afterLenght; // 两触点距离
	private float scale = 0.03f; // 缩放的比例 X Y方向都是这个值 越大缩放的越快

	// 处理拖动 变量
	private int start_x;
	private int start_y;
	private int stop_x;
	private int stop_y;

	private TranslateAnimation trans; // 处理超出边界的动画

	public PinchZoomImageView(Context context, AttributeSet attributes) {
		super(context, attributes); // defaults
	}

	public PinchZoomImageView(Context context) {
		super(context); // defaults
	}

	/**
	 * distance of 2 points
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * handle touch event
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = Mode.DRAGING;
			stop_x = (int) event.getRawX();
			stop_y = (int) event.getRawY();
			start_x = (int) event.getX();
			start_y = stop_y - this.getTop();

			try {
				if (getLeft() >= 0 && (stop_x - event.getRawX()) < 0) {
					getParent().getParent().requestDisallowInterceptTouchEvent(false);
				} else if (getRight() <= (((View) getParent()).getWidth()) && (stop_x - event.getRawX()) > 0) {
					getParent().getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					getParent().getParent().requestDisallowInterceptTouchEvent(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			;

			if (event.getPointerCount() == 2)
				beforeLenght = spacing(event);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			if (spacing(event) > 10f) {
				mode = Mode.ZOOMING;
				beforeLenght = spacing(event);
			}
			break;
		case MotionEvent.ACTION_UP:
			// handle and process if the image out of range
			int deltaXfrom = 0,
			deltaXto = 0;
			int deltaYfrom = 0,
			deltaYto = 0;
			View parentView = (View) getParent();

			if (getTop() < 0) { // must down
				int topHide = -getTop();
				int bottomLeave = (parentView.getHeight() - (getHeight() + getTop()));
				if (bottomLeave <= 0) {
					// no op
				} else if (bottomLeave > topHide) { // 下面空间比上面遮挡部分大
					deltaYfrom = getTop();
					deltaYto = 0;

					this.layout(getLeft(), 0, getRight(), getBottom() + topHide);
				} else {
					deltaYfrom = -bottomLeave;
					deltaYto = 0;

					this.layout(getLeft(), getTop() + bottomLeave, getRight(), getBottom() + bottomLeave);
				}
			} else if (getBottom() > parentView.getHeight()) { // must up
				int topLeave = getTop();
				int bottomHide = getBottom() - parentView.getHeight();
				if (topLeave <= 0) {
					// no op
				} else if (topLeave > bottomHide) { // 上面空间比下面遮挡空间大
					deltaYfrom = bottomHide;
					deltaYto = 0;

					this.layout(getLeft(), getTop() - bottomHide, getRight(), getBottom() - bottomHide);
				} else {
					deltaYfrom = topLeave;
					deltaYto = 0;

					this.layout(getLeft(), getTop() - topLeave, getRight(), getBottom() - topLeave);
				}
			}

			if (getLeft() < 0) { // must right
				int leftHide = -getLeft();
				int rightLeave = (parentView.getWidth() - (getWidth() + getLeft()));
				if (rightLeave <= 0) {
					// no op
				} else if (rightLeave > leftHide) { // 右边空间比左边遮挡部分大
					deltaXfrom = getLeft();
					deltaXto = 0;

					this.layout(0, getTop(), getRight() + leftHide, getBottom());
				} else {
					deltaXfrom = -rightLeave;
					deltaXto = 0;

					this.layout(getLeft() + rightLeave, getTop(), getRight() + rightLeave, getBottom());
				}
			} else if (getRight() > parentView.getWidth()) { // must left
				int leftLeave = getLeft();
				int rightHide = getRight() - parentView.getWidth();
				if (leftLeave <= 0) {
					// no op
				} else if (leftLeave > rightHide) { // 左边空间比右边遮挡空间大
					deltaXfrom = rightHide;
					deltaXto = 0;

					this.layout(getLeft() - rightHide, getTop(), getRight() - rightHide, getBottom());
				} else {
					deltaXfrom = leftLeave;
					deltaXto = 0;

					this.layout(0, getTop(), getRight() - leftLeave, getBottom());
				}
			}
			// animations
			trans = new TranslateAnimation(deltaXfrom, deltaXto, deltaYfrom, deltaYto);
			trans.setDuration(500);
			this.startAnimation(trans);

			mode = Mode.NONE;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode = Mode.NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			// drag
			if (mode == Mode.DRAGING) {
				// trick to drag when using ViewPager
				try {
					if (getLeft() >= 0 && (stop_x - event.getRawX()) < 0) {
						getParent().getParent().requestDisallowInterceptTouchEvent(false);
					} else if (getRight() <= (((View) getParent()).getWidth()) && (stop_x - event.getRawX()) > 0) {
						getParent().getParent().requestDisallowInterceptTouchEvent(false);
					} else {
						getParent().getParent().requestDisallowInterceptTouchEvent(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				;

				// main drag logic
				if (Math.abs(stop_x - start_x - getLeft()) < 88 && Math.abs(stop_y - start_y - getTop()) < 85) {
					this.setPosition(stop_x - start_x, stop_y - start_y, stop_x + this.getWidth() - start_x, stop_y
							- start_y + this.getHeight());
					stop_x = (int) event.getRawX();
					stop_y = (int) event.getRawY();
				}

			}
			// zoom
			else if (mode == Mode.ZOOMING) {
				if (spacing(event) > 10f) {
					afterLenght = spacing(event);
					float gapLenght = afterLenght - beforeLenght;
					if (gapLenght == 0) {
						break;
					} else if (Math.abs(gapLenght) > 5f) {
						if (gapLenght > 0) {
							this.setScale(scale, ZoomFlag.BIGGER);
						} else {
							this.setScale(scale, ZoomFlag.SMALLER);
						}
						beforeLenght = afterLenght;
					}
				}
			}
			break;
		}
		return true;
	}

	/**
	 * zoom
	 */
	private void setScale(float temp, ZoomFlag zoomFlag) {

		if (zoomFlag == ZoomFlag.BIGGER) {
			this.setFrame(this.getLeft() - (int) (temp * this.getWidth()),
					this.getTop() - (int) (temp * this.getHeight()), this.getRight() + (int) (temp * this.getWidth()),
					this.getBottom() + (int) (temp * this.getHeight()));
		} else if (zoomFlag == ZoomFlag.SMALLER) {
			this.setFrame(this.getLeft() + (int) (temp * this.getWidth()),
					this.getTop() + (int) (temp * this.getHeight()), this.getRight() - (int) (temp * this.getWidth()),
					this.getBottom() - (int) (temp * this.getHeight()));
		}
	}

	/**
	 * drag
	 */
	private void setPosition(int left, int top, int right, int bottom) {
		this.layout(left, top, right, bottom);
	}

}
