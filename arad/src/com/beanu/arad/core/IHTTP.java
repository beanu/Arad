package com.beanu.arad.core;

import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;

public interface IHTTP {

	public void get(String url, AjaxParams params, AjaxCallBack<String> callBack);

	public void post(String url, AjaxParams params, AjaxCallBack<String> callBack);

}
