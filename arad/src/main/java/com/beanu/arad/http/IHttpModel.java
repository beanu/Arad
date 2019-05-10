package com.beanu.arad.http;

/**
 * 基础实体类
 *
 * @author Beanu
 */
public interface IHttpModel<T> {

    /**
     * 接口是否正确
     *
     * @return 接口状态
     */
    boolean success();

    /**
     * 错误码
     *
     * @return 错误代码
     */
    int getErrorCode();

    /**
     * 接口提示信息
     *
     * @return 接口消息
     */
    String getMsg();

    /**
     * 结果集
     *
     * @return 结果
     */
    T getResults();

}
