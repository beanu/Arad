package com.beanu.arad.core;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;



public interface IHTTP {

	public void get(String url, AjaxParams params, AjaxCallBack<String> callBack);

	public void post(String url, AjaxParams params, AjaxCallBack<String> callBack);

}
