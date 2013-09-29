package com.beanu.arad.core;

import android.widget.ImageView;

public interface IImageLoader {

	public void display(String url, ImageView imageView);

	public void display(String url, ImageView imageView, int defaultImageResId);

	public void display(String url, ImageView imageView, int defaultImageResId, int errorImageResId);

	/**
	 * 当3G 2G的时候是否下载图片
	 * 
	 * @param load
	 * @return
	 */
	public boolean setLoadingWhen3G(boolean load);
}
