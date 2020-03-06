package com.beanu.arad.base;

import android.os.Bundle;
import android.view.View;

import com.beanu.arad.R;
import com.beanu.arad.widget.MultipleStatusView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 1.加入toolbar的操作
 * 2.视图不同状态的显示，加载中，加载失败，空数据，加载完成 四个状态。使用方式，用layout包含arad_loading.xml和自己的布局，自己的布局id定义为arad_content
 *
 * @author beanu
 */
public class ToolBarFragment<T extends BasePresenter, E extends BaseModel> extends BaseFragment<T, E> implements BaseView {

    private QMUITopBarLayout mTopBarLayout;
    private MultipleStatusView mMultipleStatusView;
    private QMUITipDialog mTipDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTopBarLayout = view.findViewById(R.id.arad_toolbar);
        mMultipleStatusView = view.findViewById(R.id.arad_status_view);

        if (mTopBarLayout != null) {
            initTopBar(mTopBarLayout);
        }

    }

    public void initTopBar(QMUITopBarLayout topBarLayout) {
        //       mTopBarLayout.addLeftImageButton(R.drawable.qmui_icon_topbar_back, R.id.arad_toolbar_left_button);

    }

    @Override
    public void contentLoading() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showLoading();
        }
    }

    @Override
    public void contentLoadingComplete() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showContent();
        }
    }


    @Override
    public void contentLoadingError() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showError();
        }
    }

    @Override
    public void contentLoadingEmpty() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showEmpty();
        }
    }

    @Override
    public void contentLoadingNoNetwork() {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.showNoNetwork();
        }
    }

    @Override
    public void setOnRetryListener(View.OnClickListener onRetryListener) {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.setOnRetryClickListener(onRetryListener);
        }
    }

    @Override
    public void showProgressDialog() {
        mTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("加载中...")
                .create();
        mTipDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mTipDialog != null) {
            mTipDialog.dismiss();
        }
    }

    @Override
    public QMUITipDialog showMessage(@NonNull String message) {
        return showMessage(message, QMUITipDialog.Builder.ICON_TYPE_INFO);
    }

    @Override
    public QMUITipDialog showErrorMessage(@NonNull String message) {
        return showMessage(message, QMUITipDialog.Builder.ICON_TYPE_FAIL);
    }

    @Override
    public QMUITipDialog showSuccessMessage(@NonNull String message) {
        return showMessage(message, QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
    }


    private QMUITipDialog showMessage(@NonNull String message, int type) {

        mTipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(type)
                .setTipWord(message)
                .create();
        mTipDialog.show();
        if (getActivity() != null) {
            getActivity().getWindow().getDecorView().postDelayed(() -> {
                if (mTipDialog != null) {
                    mTipDialog.dismiss();
                }
            }, 1500);
        }

        return mTipDialog;
    }


    public QMUITopBarLayout getTopBarLayout() {
        return mTopBarLayout;
    }

    public MultipleStatusView getMultipleStatusView() {
        return mMultipleStatusView;
    }
}
