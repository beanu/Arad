package com.beanu.arad.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

/**
 * 用来提供文件上传的http访问
 * 
 * @author beanu
 * 
 */
public class FileUploadHttp extends AsyncTask<String, Void, String> {

	private HttpEntity mEntity;
	private AjaxCallBack<String> callBack;

	public FileUploadHttp(HttpEntity entity, AjaxCallBack<String> callBack) {
		this.mEntity = entity;
		this.callBack = callBack;
	}

	@Override
	protected String doInBackground(String... params) {
		HttpPost post = new HttpPost(params[0]);
		HttpClient client = new DefaultHttpClient();
		post.setEntity(mEntity);
		String result = null;

		try {
			HttpResponse response = client.execute(post);
			result = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null) {
			callBack.onSuccess(result);
		} else {
			callBack.onFailure(null, 0, "请求错误");
		}

	}
}
