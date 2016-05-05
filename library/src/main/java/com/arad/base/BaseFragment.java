package com.arad.base;

import android.support.v4.app.Fragment;

import com.arad.widget.dialog.ProgressHUD;


public class BaseFragment extends Fragment {

    ProgressHUD mProgressHUD;

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
