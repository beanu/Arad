package com.beanu.arad.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beanu.arad.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;


/**
 * 类描述：  一个方便在多种状态切换的view
 * <p>
 * 创建人:   续写经典
 * 创建时间: 2016/1/15 10:20.
 *
 * @author Beanu
 */
@SuppressWarnings("unused")
public class MultipleStatusView extends RelativeLayout {
    private static final String TAG = "MultipleStatusView";

    private static final RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS =
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);

    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;

    private static final int NULL_RESOURCE_ID = -1;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mNoNetworkView;
    private View mContentView;

    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private int mContentViewResId;

    private int mViewStatus = -1;
    private final LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;
    private OnViewStatusChangeListener mViewStatusListener;

    private final ArrayList<Integer> mOtherIds = new ArrayList<>();

    public MultipleStatusView(Context context) {
        this(context, null);
    }

    public MultipleStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.MultipleStatusView_emptyView, R.layout.empty_view);
        mErrorViewResId = a.getResourceId(R.styleable.MultipleStatusView_errorView, R.layout.error_view);
        mLoadingViewResId = a.getResourceId(R.styleable.MultipleStatusView_loadingView, R.layout.loading_view);
        mNoNetworkViewResId = a.getResourceId(R.styleable.MultipleStatusView_noNetworkView, R.layout.no_network_view);
        mContentViewResId = a.getResourceId(R.styleable.MultipleStatusView_contentView, NULL_RESOURCE_ID);
        a.recycle();
        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showContent();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clear(mEmptyView, mLoadingView, mErrorView, mNoNetworkView);
        if (!mOtherIds.isEmpty()) {
            mOtherIds.clear();
        }
        if (null != mOnRetryClickListener) {
            mOnRetryClickListener = null;
        }
        if (null != mViewStatusListener) {
            mViewStatusListener = null;
        }
    }

    /**
     * 获取当前状态
     *
     * @return 视图状态
     */
    public int getViewStatus() {
        return mViewStatus;
    }

    /**
     * 设置重试点击事件
     *
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    /**
     * 显示空视图
     */
    public final void showEmpty() {
        showEmpty(mEmptyViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示空视图
     *
     * @param hintResId  自定义提示文本内容
     * @param formatArgs 占位符参数
     */
    public final void showEmpty(int hintResId, Object... formatArgs) {
        showEmpty();
        setStatusHintContent(mEmptyView, hintResId, formatArgs);
    }

    /**
     * 显示空视图
     *
     * @param hint 自定义提示文本内容
     */
    public final void showEmpty(String hint) {
        showEmpty();
        setStatusHintContent(mEmptyView, hint);
    }

    /**
     * 显示空视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showEmpty(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showEmpty(null == mEmptyView ? inflateView(layoutId) : mEmptyView, layoutParams);
    }

    /**
     * 显示空视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showEmpty(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Empty view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_EMPTY);
        if (null == mEmptyView) {
            mEmptyView = view;
            View emptyRetryView = mEmptyView.findViewById(R.id.empty_retry_view);
            if (null != mOnRetryClickListener && null != emptyRetryView) {
                emptyRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(mEmptyView.getId());
            addView(mEmptyView, 0, layoutParams);
        }
        showViewById(mEmptyView.getId());
    }

    /**
     * 显示错误视图
     */
    public final void showError() {
        showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示错误视图
     *
     * @param hintResId  自定义提示文本内容
     * @param formatArgs 占位符参数
     */
    public final void showError(int hintResId, Object... formatArgs) {
        showError();
        setStatusHintContent(mErrorView, hintResId, formatArgs);
    }

    /**
     * 显示错误视图
     *
     * @param hint 自定义提示文本内容
     */
    public final void showError(String hint) {
        showError();
        setStatusHintContent(mErrorView, hint);
    }

    /**
     * 显示错误视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showError(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showError(null == mErrorView ? inflateView(layoutId) : mErrorView, layoutParams);
    }

    /**
     * 显示错误视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showError(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Error view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_ERROR);
        if (null == mErrorView) {
            mErrorView = view;
            View errorRetryView = mErrorView.findViewById(R.id.error_retry_view);
            if (null != mOnRetryClickListener && null != errorRetryView) {
                errorRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(mErrorView.getId());
            addView(mErrorView, 0, layoutParams);
        }
        showViewById(mErrorView.getId());
    }

    /**
     * 显示加载中视图
     */
    public final void showLoading() {
        showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示加载中视图
     *
     * @param hintResId  自定义提示文本内容
     * @param formatArgs 占位符参数
     */
    public final void showLoading(int hintResId, Object... formatArgs) {
        showLoading();
        setStatusHintContent(mLoadingView, hintResId, formatArgs);
    }

    /**
     * 显示加载中视图
     *
     * @param hint 自定义提示文本内容
     */
    public final void showLoading(String hint) {
        showLoading();
        setStatusHintContent(mLoadingView, hint);
    }

    /**
     * 显示加载中视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showLoading(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showLoading(null == mLoadingView ? inflateView(layoutId) : mLoadingView, layoutParams);
    }

    /**
     * 显示加载中视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showLoading(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Loading view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_LOADING);
        if (null == mLoadingView) {
            mLoadingView = view;
            mOtherIds.add(mLoadingView.getId());
            addView(mLoadingView, 0, layoutParams);
        }
        showViewById(mLoadingView.getId());
    }

    /**
     * 显示无网络视图
     */
    public final void showNoNetwork() {
        showNoNetwork(mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示无网络视图
     *
     * @param hintResId  自定义提示文本内容
     * @param formatArgs 占位符参数
     */
    public final void showNoNetwork(int hintResId, Object... formatArgs) {
        showNoNetwork();
        setStatusHintContent(mNoNetworkView, hintResId, formatArgs);
    }

    /**
     * 显示无网络视图
     *
     * @param hint 自定义提示文本内容
     */
    public final void showNoNetwork(String hint) {
        showNoNetwork();
        setStatusHintContent(mNoNetworkView, hint);
    }

    /**
     * 显示无网络视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showNoNetwork(null == mNoNetworkView ? inflateView(layoutId) : mNoNetworkView, layoutParams);
    }

    /**
     * 显示无网络视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "No network view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_NO_NETWORK);
        if (null == mNoNetworkView) {
            mNoNetworkView = view;
            View noNetworkRetryView = mNoNetworkView.findViewById(R.id.no_network_retry_view);
            if (null != mOnRetryClickListener && null != noNetworkRetryView) {
                noNetworkRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(mNoNetworkView.getId());
            addView(mNoNetworkView, 0, layoutParams);
        }
        showViewById(mNoNetworkView.getId());
    }

    /**
     * 显示内容视图
     */
    public final void showContent() {
        changeViewStatus(STATUS_CONTENT);
        if (null == mContentView && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater.inflate(mContentViewResId, null);
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
        }
        showContentView();
    }

    /**
     * 显示内容视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showContent(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showContent(inflateView(layoutId), layoutParams);
    }

    /**
     * 显示内容视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    public final void showContent(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Content view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_CONTENT);
        clear(mContentView);
        mContentView = view;
        addView(mContentView, 0, layoutParams);
        showViewById(mContentView.getId());
    }

    private void setStatusHintContent(View view, int resId, Object... formatArgs) {
        checkNull(view, "Target view is null.");
        setStatusHintContent(view, view.getContext().getString(resId, formatArgs));
    }

    private void setStatusHintContent(View view, String hint) {
        checkNull(view, "Target view is null.");
        TextView hintView = view.findViewById(R.id.status_hint_content);
        if (null != hintView) {
            hintView.setText(hint);
        } else {
            throw new NullPointerException("Not find the view ID `status_hint_content`");
        }
    }

    private View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    private void showViewById(int viewId) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
        }
    }

    private void showContentView() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(mOtherIds.contains(view.getId()) ? View.GONE : View.VISIBLE);
        }
    }

    private void checkNull(Object object, String hint) {
        if (null == object) {
            throw new NullPointerException(hint);
        }
    }

    private void clear(View... views) {
        if (null == views) {
            return;
        }
        try {
            for (View view : views) {
                if (null != view) {
                    removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 视图状态改变接口
     */
    public interface OnViewStatusChangeListener {

        /**
         * 视图状态改变时回调
         *
         * @param oldViewStatus 之前的视图状态
         * @param newViewStatus 新的视图状态
         */
        void onChange(int oldViewStatus, int newViewStatus);
    }

    /**
     * 设置视图状态改变监听事件
     *
     * @param onViewStatusChangeListener 视图状态改变监听事件
     */
    public void setOnViewStatusChangeListener(OnViewStatusChangeListener onViewStatusChangeListener) {
        this.mViewStatusListener = onViewStatusChangeListener;
    }

    /**
     * 改变视图状态
     *
     * @param newViewStatus 新的视图状态
     */
    private void changeViewStatus(int newViewStatus) {
        if (mViewStatus == newViewStatus) {
            return;
        }
        if (null != mViewStatusListener) {
            mViewStatusListener.onChange(mViewStatus, newViewStatus);
        }
        mViewStatus = newViewStatus;
    }


    private void setContentViewResId(int contentViewResId) {
        this.mContentViewResId = contentViewResId;
        this.mContentView = mInflater.inflate(mContentViewResId, null);
        addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
    }

    private void setContentView(ViewGroup contentView) {
        this.mContentView = contentView;
        addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
    }

    public static MultipleStatusView attach(Fragment fragment, int rootAnchor) {
        if (null == fragment || fragment.getView() == null) {
            throw new IllegalArgumentException("fragment is null or fragment.getView is null");
        }
        if (-1 != rootAnchor) {
            ViewGroup contentAnchor = fragment.getView().findViewById(rootAnchor);
            if (null != contentAnchor) {
                attach(contentAnchor);
            }
        }
        ViewGroup contentParent = (ViewGroup) fragment.getView().getParent();
        return attach(contentParent);
    }

    public static MultipleStatusView attach(Activity activity, int rootAnchor) {
        if (-1 != rootAnchor) {
            ViewGroup contentAnchor = activity.findViewById(rootAnchor);
            if (null != contentAnchor) {
                attach(contentAnchor);
            }
        }
        ViewGroup defaultAnchor = activity.findViewById(android.R.id.content);
        return attach(defaultAnchor);
    }

    public static MultipleStatusView attach(ViewGroup rootAnchor) {
        if (null == rootAnchor) {
            throw new IllegalArgumentException("root Anchor View can't be null");
        }
        ViewGroup parent = (ViewGroup) rootAnchor.getParent();
        int anchorIndex = parent.indexOfChild(rootAnchor);
        if (-1 != anchorIndex) {
            parent.removeView(rootAnchor);
            MultipleStatusView statusView = new MultipleStatusView(rootAnchor.getContext());
            statusView.setContentView(rootAnchor);
            ViewGroup.LayoutParams p = rootAnchor.getLayoutParams();
            parent.addView(statusView, anchorIndex, p);
            return statusView;
        }
        return null;
    }
}