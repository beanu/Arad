package com.beanu.arad.core;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

//TODO 去掉RequestParams & AsyncHttpResponseHandler & BinaryHttpResponseHandler
public interface IHTTP {

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);

    public void download(String url, BinaryHttpResponseHandler responseHandler);

}
