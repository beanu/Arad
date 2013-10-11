package com.beanu.arad.http;

/**
 * Activity 访问网络接口 MVC模式
 * 
 * @author beanu
 * 
 */
public interface INetResult {

	/**
	 * 访问网络成功后更新UI
	 * 
	 * @param type
	 *            第几个网络请求
	 * 
	 */
	public void onSuccess(int type);

	public void onFaild(String errorMessage);
}
