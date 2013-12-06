package com.beanu.arad.support;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;
import com.beanu.arad.http.INetResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Map;

public abstract class IDao<T> {

    protected INetResult mResult;
    protected IHttpConfig mHttpConfig;

    public IDao(INetResult activity, IHttpConfig httpConfig) {
        this.mResult = activity;
        this.mHttpConfig = httpConfig;
    }

    /**
     * 得到结果后，对结果处理逻辑
     *
     * @param result
     * @param type
     * @throws java.io.IOException
     */
    public abstract void onRequestSuccess(T result, int type) throws IOException;


    /**
     * get请求网络，本方法提供结果的分发
     *
     * @param params
     */
    protected void getRequest(String url, Map<String, String> params) {
        _getRequest(url, params, 0);
    }

    /**
     * get请求网络，带有请求编号
     *
     * @param params
     */
    protected void getRequestForResult(String url, Map<String, String> params, int requestCode) {
        _getRequest(url, params, requestCode);
    }


    private void _getRequest(String url, Map<String, String> params, final int type) {

        AjaxParams ajaxParams = new AjaxParams(params);
        Arad.http.get(url, ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {

                try {
                    if (mHttpConfig != null) {
                        Object node = mHttpConfig.handleResult(t);
                        onRequestSuccess((T) node, type);
                    }
                    mResult.onSuccess(type);
                } catch (AradException e) {
                    mResult.onFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        });

    }

    /**
     * POST 请求
     *
     * @param type 自定义这是第几个post请求，用于结果的区分
     */
    public void postRequest(String url, AjaxParams ajaxParams, final int type) {

        Arad.http.post(url, ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {
                try {
                    if (mHttpConfig != null) {
                        Object node = mHttpConfig.handleResult(t);
                        onRequestSuccess((T) node, type);

                    }
                    mResult.onSuccess(type);
                } catch (AradException e) {
                    mResult.onFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        });
    }


}
