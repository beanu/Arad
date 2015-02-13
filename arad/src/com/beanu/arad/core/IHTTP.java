package com.beanu.arad.core;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public interface IHTTP {

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);

    public void download(String url, BinaryHttpResponseHandler responseHandler);

    //
    public void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler);

    public void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler);

    public void download(Context context, String url, BinaryHttpResponseHandler responseHandler);

}
