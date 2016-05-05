package com.arad;//package com.beanu.arad;
//
//import android.content.Context;
//
//
//import java.io.UnsupportedEncodingException;
//
//public class Http implements IHTTP {
//
//    private static Http instance;
//    private static AsyncHttpClient client;
//
//    private Http() {
//        client = new AsyncHttpClient();
//    }
//
//    public static Http instance() {
//        if (instance == null)
//            instance = new Http();
//        //TODO http拦截器-增加全局错误处理机制
////        ((DefaultHttpClient) client.getHttpClient()).addResponseInterceptor(new HttpResponseInterceptor() {
////            @Override
////            public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
////
////            }
////        });
//        return instance;
//    }
//
//    @Override
//    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.get(url, params, responseHandler);
//    }
//
//    @Override
//    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.post(url, params, responseHandler);
//    }
//
//    @Override
//    public void download(String url, BinaryHttpResponseHandler responseHandler) {
//        client.get(url, responseHandler);
//    }
//
//    @Override
//    public void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.get(context, url, params, responseHandler);
//    }
//
//    @Override
//    public void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.post(context, url, params, responseHandler);
//    }
//
//    @Override
//    public void download(Context context, String url, BinaryHttpResponseHandler responseHandler) {
//        client.get(context, url, responseHandler);
//    }
//
//    @Override
//    public void post(Context context, String url, String jsonParams, AsyncHttpResponseHandler responseHandler) {
//        try {
//            StringEntity entity = new StringEntity(jsonParams, "UTF-8");
//            entity.setContentType("application/json");
//            client.post(context, url, entity, "application/json", responseHandler);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void cancelRequests(Context context) {
//        client.cancelRequests(context, true);
//    }
//
//    @Override
//    public void cancelAllRequests() {
//        client.cancelAllRequests(true);
//    }
//}
