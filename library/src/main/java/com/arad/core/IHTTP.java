package com.arad.core;//package com.beanu.arad.core;
//
//import android.content.Context;
//
//public interface IHTTP {
//
//    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);
//
//    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);
//
//    public void download(String url, BinaryHttpResponseHandler responseHandler);
//
//    //指定context 统一管理
//    public void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler);
//
//    public void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler);
//
//    public void download(Context context, String url, BinaryHttpResponseHandler responseHandler);
//
//    /**
//     * post json
//     * <p>
//     * JSONObject jsonParams = new JSONObject();
//     * jsonParams.put("notes", "Test api support");
//     * jsonParams.toString()
//     */
//    public void post(Context context, String url, String jsonParams, AsyncHttpResponseHandler responseHandler);
//
//
//    public void cancelRequests(Context context);
//
//    public void cancelAllRequests();
//
//}
