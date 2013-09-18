package com.beanu.arad.http;

/**
 * Activity 访问网络接口 MVC模式
 * 
 * @author beanu
 * 
 */
public interface INetResult {

	/**
	 * 
	 * @param success
	 *            网络访问成功还是失败
	 */
	public void onSuccess(int type);

	public void onFaild(String errorMessage);
}
