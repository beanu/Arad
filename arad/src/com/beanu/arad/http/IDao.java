package com.beanu.arad.http;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.util.Map;

/**
 * 公用的网络请求辅助类
 * 网路请求的第一层过滤（统一处理错误结果）
 */
public abstract class IDao {

    protected INetResult mResult;
    protected Context context;

    public IDao(INetResult iNetResult) {
        mResult = iNetResult;

        if (iNetResult instanceof Fragment) {
            context = ((Fragment) iNetResult).getActivity();
        } else if (iNetResult instanceof Activity) {
            context = (Activity) iNetResult;
        }
    }

    /**
     * 得到结果后，对结果处理逻辑
     *
     * @param result      网络请求返回的结果
     * @param requestCode 区别请求号码
     * @throws java.io.IOException
     */
    public abstract void onRequestSuccess(JsonNode result, int requestCode) throws IOException;


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
    protected void getRequestForCode(String url, Map<String, String> params, int requestCode) {
        _getRequest(url, params, requestCode);
    }


    private void _getRequest(String url, Map<String, String> params, final int requestCode) {

        RequestParams ajaxParams = new RequestParams(params);
        Log.d(AsyncHttpClient.getUrlWithQueryString(true, url, ajaxParams));

        Arad.http.get(context, url, ajaxParams, new TextHttpResponseHandler() {

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.d(responseBody);
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        onRequestSuccess(node, requestCode);
                    }
                    mResult.onRequestSuccess(requestCode);
                } catch (AradException e) {
                    mResult.onRequestFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                Log.d("statusCode:" + statusCode + " Body:" + responseBody);
                if (statusCode == 0)
                    mResult.onNoConnect();
                else
                    mResult.onRequestFaild("" + statusCode, responseBody);
            }
        });

    }

    /**
     * POST 请求
     *
     * @param requestCode 自定义这是第几个post请求，用于结果的区分
     */
    public void postRequest(String url, RequestParams params, final int requestCode) {

        Log.d("POST: " + AsyncHttpClient.getUrlWithQueryString(true, url, params));

        Arad.http.post(context, url, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.d(responseBody);

                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        onRequestSuccess(node, requestCode);
                    }
                    mResult.onRequestSuccess(requestCode);
                } catch (AradException e) {
                    mResult.onRequestFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                Log.d("statusCode:" + statusCode + " Body:" + responseBody);

                if (statusCode == 0)
                    mResult.onNoConnect();
                else
                    mResult.onRequestFaild("" + statusCode, responseBody);
            }
        });

    }

    /**
     * Post json 请求
     *
     * @param url         接口地址
     * @param jsonParams  请求参数 json字符串
     * @param requestCode 请求编号，区分返回的结果
     */
    public void postRequest(String url, String jsonParams, final int requestCode) {
        Log.d("POST: " + url + " JSONParams:" + jsonParams);

        Arad.http.post(context, url, jsonParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable throwable) {
                Log.d("statusCode:" + statusCode + " Body:" + responseBody);

                if (statusCode == 0)
                    mResult.onNoConnect();
                else
                    mResult.onRequestFaild("" + statusCode, responseBody);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.d(responseBody);

                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        onRequestSuccess(node, requestCode);
                    }
                    mResult.onRequestSuccess(requestCode);
                } catch (AradException e) {
                    mResult.onRequestFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    mResult.onRequestFaild("error", "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
