package com.beanu.arad.http;

/**
 * 基础实体类
 */
public interface IHttpModel<T> {

    /**
     * 接口是否正确
     */
    boolean success();

    /**
     * 错误码
     */
    String getErrorCode();

    /**
     * 接口提示信息
     */
    String getMsg();

    /**
     * 结果集
     */
    T getResults();

}
