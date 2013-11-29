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

public abstract class IDao {

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
    public abstract void onRequestSuccess(JsonNode result, int type) throws IOException;


    /**
     * get请求网络，本方法提供结果的分发
     *
     * @param params
     */
    protected void getRequest(Map<String, String> params) {
        _getRequest(params, 0);
    }

    protected void getRequestForResult(Map<String, String> params, int requestCode) {
        _getRequest(params, requestCode);
    }


    private void _getRequest(Map<String, String> params, final int type) {

        AjaxParams ajaxParams = new AjaxParams(params);
        Arad.http.get(mHttpConfig.requestUrl(), ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String t) {

                try {
                    JsonNode node = mHttpConfig.handleResult(t);
                    onRequestSuccess(node, type);
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
                    JsonNode node = mHttpConfig.handleResult(t);
                    onRequestSuccess(node, type);
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
