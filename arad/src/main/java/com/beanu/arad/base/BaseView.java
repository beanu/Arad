package com.beanu.arad.base;

/**
 * MVP
 * 基础View
 */
public interface BaseView {

    // 加载内容
    void contentLoading();

    // 内容加载完成
    void contentLoadingComplete();

    // 内容加载失败
    void contentLoadingError();

    // 内容为空
    void contentLoadingEmpty();

}
