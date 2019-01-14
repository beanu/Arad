package com.beanu.arad.base;

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
     * 当前页面加载条
     */
    void showProgress();

    /**
     * 当前页面隐藏加载条
     */
    void hideProgress();

    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 {@code null}
     */
    void showMessage(@NonNull String message);



}