package com.beanu.arad.base;

import android.support.v4.app.ListFragment;

import com.beanu.arad.widget.dialog.ProgressHUD;

public class BaseListFragment extends ListFragment {
    ProgressHUD mProgressHUD;

//    @Override
//    public void onRequestSuccess(int requestCode) {
//
//    }
//
//    @Override
//    public void onRequestFaild(String errorNo, String errorMessage) {
//        showProgress(false);
//        MessageUtils.showShortToast(getActivity(), errorMessage);
//    }
//
//    @Override
//    public void onNoConnect() {
//        showProgress(false);
//        MessageUtils.showShortToast(getActivity(), "无网络连接");
//    }

    public void showProgress(boolean show) {
        showProgressWithText(show, "加载中...");
    }

    public void showProgressWithText(boolean show, String message) {
        if (show) {
            mProgressHUD = ProgressHUD.show(getActivity(), message, true, true, null);
        } else {
            if (mProgressHUD != null) {
                mProgressHUD.dismiss();
            }
        }
    }
}
