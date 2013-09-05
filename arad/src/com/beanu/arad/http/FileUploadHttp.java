package com.beanu.arad.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
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
public class FileUploadHttp extends AsyncTask<String, Void, HttpResponse> {

	private HttpEntity mEntity;
	private AjaxCallBack<String> callBack;

	public FileUploadHttp(HttpEntity entity, AjaxCallBack<String> callBack) {
		this.mEntity = entity;
		this.callBack = callBack;
	}

	@Override
	protected HttpResponse doInBackground(String... params) {
		HttpPost post = new HttpPost(params[0]);
		HttpClient client = new DefaultHttpClient();
		post.setEntity(mEntity);
		HttpResponse response = null;

		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	protected void onPostExecute(HttpResponse result) {
		if (result != null) {
			String response = "";
			try {
				response = EntityUtils.toString(result.getEntity());
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			callBack.onSuccess(response);
		} else {
			callBack.onFailure(null, 0, "请求错误");
		}

	}
}
