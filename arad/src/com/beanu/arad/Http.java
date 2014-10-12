package com.beanu.arad;

import com.beanu.arad.core.IHTTP;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class Http implements IHTTP {

    private static Http instance;
    private static AsyncHttpClient client;

    private Http() {
        client = new AsyncHttpClient();
    }

    public static Http instance() {
        if (instance == null)
            instance = new Http();
        //TODO http拦截器-增加全局错误处理机制
        ((DefaultHttpClient) client.getHttpClient()).addResponseInterceptor(new HttpResponseInterceptor() {
            @Override
            public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {

            }
        });
        return instance;
    }

    @Override
    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    @Override
    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    @Override
    public void download(String url, BinaryHttpResponseHandler responseHandler) {
        client.get(url, responseHandler);
    }

}
