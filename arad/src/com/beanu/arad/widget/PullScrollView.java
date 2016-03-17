package com.beanu.arad.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.beanu.arad.R;

/**
 * Created by harvic on 2015/6/17
 * @adress blog.csdn.net/harvic880925
 */
public class PullScrollView extends ScrollView {
    //底部图片View
    private View mHeaderView;
    //头部图片的初始化位置
    private Rect mHeadInitRect = new Rect();
    //底部View
    private View mContentView;
    //ScrollView的contentView的初始化位置
    private Rect mContentInitRect = new Rect();

    //初始点击位置
    private Point mTouchPoint = new Point();


    //标识当前view是否移动
    boolean mIsMoving = false;

    //是否禁止控件本身的的移动
    boolean mEnableMoving = false;

    //是否使用layout函数移动布局
    boolean mIsLayout = false;
    /**
     * 阻尼系数,越小阻力就越大.
     */
    private static final float SCROLL_RATIO = 0.5f;

    private int mContentTop, mContentBottom;

    private int mHeaderCurTop, mHeaderCurBottom;

    //用户定义的headview高度
    private int mHeaderHeight = 0;

    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // set scroll mode
        setOverScrollMode(OVER_SCROLL_NEVER);

        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PullScrollView);

            if (ta != null) {
                mHeaderHeight = (int) ta.getDimension(R.styleable.PullScrollView_headerHeight, -1);
                ta.recycle();

            }
        }
    }

    public void setmHeaderView(View view) {
        mHeaderView = view;
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            mContentView = getChildAt(0);
        }
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                int deltaY = (int) event.getY() - mTouchPoint.y;
//                deltaY = deltaY < 0 ? 0 : (deltaY > mHeaderView.getHeight() ? mHeaderView.getHeight() : deltaY);
                deltaY = deltaY < 0 ? 0 : (deltaY > mHeaderHeight ? mHeaderHeight : deltaY);
                if (deltaY > 0 && deltaY >= getScrollY() && mIsLayout) {
                    float headerMoveHeight = deltaY * 0.5f * SCROLL_RATIO;
                    mHeaderCurTop = (int) (mHeadInitRect.top + headerMoveHeight);
                    mHeaderCurBottom = (int) (mHeadInitRect.bottom + headerMoveHeight);

                    float contentMoveHeight = deltaY * SCROLL_RATIO;
                    mContentTop = (int) (mContentInitRect.top + contentMoveHeight);
                    mContentBottom = (int) (mContentInitRect.bottom + contentMoveHeight);

                    if (mContentTop <= mHeaderCurBottom) {
                        mHeaderView.layout(mHeadInitRect.left, mHeaderCurTop, mHeadInitRect.right, mHeaderCurBottom);
                        mContentView.layout(mContentInitRect.left, mContentTop, mContentInitRect.right, mContentBottom);
                        mIsMoving = true;
                        mEnableMoving = true;
                    }
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                //反弹
                if (mIsMoving) {
                    mHeaderView.layout(mHeadInitRect.left, mHeadInitRect.top, mHeadInitRect.right, mHeadInitRect.bottom);
                    TranslateAnimation headAnim = new TranslateAnimation(0, 0, mHeaderCurTop - mHeadInitRect.top, 0);
                    headAnim.setDuration(200);
                    mHeaderView.startAnimation(headAnim);

                    mContentView.layout(mContentInitRect.left, mContentInitRect.top, mContentInitRect.right, mContentInitRect.bottom);
                    TranslateAnimation contentAnim = new TranslateAnimation(0, 0, mContentTop - mContentInitRect.top, 0);
                    contentAnim.setDuration(200);
                    mContentView.startAnimation(contentAnim);
                    mIsMoving = false;
                }
                mEnableMoving = false;
                mIsLayout = false;
            }
            break;
        }
        // 禁止控件本身的滑动.
        //这句厉害,如果mEnableMoving返回TRUE,那么就不会执行super.onTouchEvent(event)
        //只有返回FALSE的时候,才会执行super.onTouchEvent(event)
        //禁止控件本身的滑动，就会让它，本来应有的滑动就不会滑动了，比如向上滚动
        return mEnableMoving || super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //保存原始位置
            mTouchPoint.set((int) event.getX(), (int) event.getY());
            mHeadInitRect.set(mHeaderView.getLeft(), mHeaderView.getTop(), mHeaderView.getRight(), mHeaderView.getBottom());
            mContentInitRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
            mIsMoving = false;
            //如果当前不是从初始化位置开始滚动的话，就不让用户拖拽
            if (getScrollY() == 0){
                mIsLayout = true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //如果当前的事件是我们要处理的事件时，比如现在的下拉，这时候，我们就不能让子控件来处理这个事件
            //这里就需要把它截获，不传给子控件，更不能让子控件消费这个事件
            //不然子控件的行为就可能与我们的相冲突
            int deltaY = (int) event.getY() - mTouchPoint.y;
//          deltaY = deltaY < 0 ? 0 : (deltaY > mHeaderView.getHeight() ? mHeaderView.getHeight() : deltaY);
            deltaY = deltaY < 0 ? 0 : (deltaY > mHeaderHeight ? mHeaderHeight : deltaY);
            if (deltaY > 0 && deltaY >= getScrollY()&& getScrollY() == 0) {
                onTouchEvent(event);
                return true;
            }
        }
        return super.onInterceptTouchEvent(event);
    }
}
