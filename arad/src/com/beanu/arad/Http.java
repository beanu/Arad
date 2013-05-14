package com.beanu.arad;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.beanu.arad.core.IHTTP;

public class Http implements IHTTP {

	private static Http instance;

	private FinalHttp finalHttp;

	private Http() {
		finalHttp = new FinalHttp();
	}

	public static Http create() {
		if (instance == null)
			instance = new Http();
		return instance;
	}

	@Override
	public void get(String url, AjaxParams params, AjaxCallBack<? extends Object> callBack) {
		finalHttp.get(url, params, callBack);
	}

	@Override
	public void post(String url, AjaxParams params, AjaxCallBack<? extends Object> callBack) {
		finalHttp.post(url, params, callBack);

	}

}
