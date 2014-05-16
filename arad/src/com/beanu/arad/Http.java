package com.beanu.arad;

import com.beanu.arad.core.IHTTP;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Http implements IHTTP {

    private static Http instance;
    private static AsyncHttpClient client = new AsyncHttpClient();

    private Http() {
    }

    public static Http instance() {
        if (instance == null)
            instance = new Http();
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

}
