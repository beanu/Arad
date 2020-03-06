package com.beanu.arad.base;

import android.os.Bundle;
import android.view.View;

import com.beanu.arad.R;
import com.beanu.arad.widget.MultipleStatusView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 1.加入toolbar的操作
 * 2. 加入了全局的loading
 *
 * @author beanu
 */
public class ToolBarActivity<T extends BasePresenter, E extends BaseModel> extends BaseActivity<T, E> implements BaseView {

    private QMUITopBarLayout mTopBarLayout;
    private MultipleStatusView mMultipleStatusView;

    private QMUITipDialog mTipDialog;
    private QMUITipDialog mMessageDialog;

    private boolean isDestroy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDestroy = false;
    }

    @Override
    protected void onDestroy() {
        isDestroy = true;
        super.onDestroy();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);

        mTopBarLayout = findViewById(R.id.arad_toolbar);
        mMultipleStatusView = findViewById(R.id.arad_status_view);

        if (mTopBarLayout != null) {
            initTopBar(mTopBarLayout);
        }
    }

    public void initTopBar(QMUITopBarLayout topBarLayout) {
//       mTopBarLayout.addLeftImageButton(R.drawable.qmui_icon_topbar_back, R.id.arad_toolbar_left_button);

    }

    public QMUITopBarLayout getTopBarLayout() {
        return mTopBarLayout;
    }

    public MultipleStatusView getMultipleStatusView() {
        return mMultipleStatusView;
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
    public void showProgressDialog() {
        mTipDialog = new QMUITipDialog.Builder(this)
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

    @Override
    public void setOnRetryListener(View.OnClickListener onRetryListener) {
        if (mMultipleStatusView != null) {
            mMultipleStatusView.setOnRetryClickListener(onRetryListener);
        }
    }


    private QMUITipDialog showMessage(@NonNull String message, int type) {
        if (mTipDialog != null && mTipDialog.isShowing()) {
            mTipDialog.dismiss();
        }
        mMessageDialog = new QMUITipDialog.Builder(this)
                .setIconType(type)
                .setTipWord(message)
                .create();
        mMessageDialog.show();
        getWindow().getDecorView().postDelayed(() -> {
            if (!isDestroy && mMessageDialog != null) {
                mMessageDialog.dismiss();
            }
        }, 1500);

        return mMessageDialog;
    }
}
