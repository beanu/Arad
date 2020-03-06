package com.beanu.arad.base;

import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.NonNull;

/**
 * MVP
 * 基础View
 *
 * @author Beanu
 */
public interface BaseView {

    /**
     * 加载内容
     */
    void contentLoading();

    /**
     * 内容加载完成
     */
    void contentLoadingComplete();

    /**
     * 内容加载失败
     */
    void contentLoadingError();

    /**
     * 内容为空
     */
    void contentLoadingEmpty();

    /**
     * 无网络
     */
    void contentLoadingNoNetwork();

    /**
     * 点击重试
     */
    void setOnRetryListener(View.OnClickListener onRetryListener);

    /**
     * 当前页面等待框
     */
    void showProgressDialog();

    /**
     * 当前页面隐藏等待框
     */
    void hideProgressDialog();

    /**
     * 显示一段信息，1.5s后自动消失
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    QMUITipDialog showMessage(@NonNull String message);

    QMUITipDialog showErrorMessage(@NonNull String message);

    QMUITipDialog showSuccessMessage(@NonNull String message);

}