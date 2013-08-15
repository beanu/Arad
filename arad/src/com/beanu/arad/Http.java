package com.beanu.arad;

import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beanu.arad.core.IHTTP;
import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;
import com.beanu.arad.http.OkHttpStack;
import com.beanu.arad.utils.Log;

public class Http implements IHTTP {

	private static Http instance;

	private final RequestQueue requestQueue;

	private Http(Context context) {
		requestQueue = Volley.newRequestQueue(context, new OkHttpStack());
	}

	public static Http create(Context context) {
		if (instance == null)
			instance = new Http(context);
		return instance;
	}

	@Override
	public void get(String url, AjaxParams params, final AjaxCallBack<String> callBack) {

		requestQueue.add(new StringRequest(Method.GET, getUrlWithQueryString(url, params), new Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.d(response);
				callBack.onSuccess(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				int statusCode = error.networkResponse == null ? 0 : error.networkResponse.statusCode;
				callBack.onFailure(error, statusCode, error.getMessage());
			}
		}));
	}

	@Override
	public void post(String url, final AjaxParams params, final AjaxCallBack<String> callBack) {
		requestQueue.add(new StringRequest(Method.POST, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				callBack.onSuccess(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				int statusCode = error.networkResponse == null ? 0 : error.networkResponse.statusCode;
				callBack.onFailure(error, statusCode, error.getMessage());
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params.getUrlParams();
			}
		});
	}

	private String getUrlWithQueryString(String url, AjaxParams params) {
		if (params != null) {
			String paramString = params.getParamString();
			url += "?" + paramString;
		}
		Log.d(url);
		return url;
	}

}
