package com.beanu.arad;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.beanu.arad.core.IImageLoader;
import com.beanu.arad.image.BitmapLruCache;

public class ImageLoader implements IImageLoader {

	private static final int MAX_IMAGE_CACHE_ENTIRES = 5 * 1024 * 1024;// 5MB
	private static ImageLoader instance;
	private RequestQueue mRequestQueue;
	private static com.android.volley.toolbox.ImageLoader mImageLoader;

	private ImageLoader(Context ctx) {
		mRequestQueue = Volley.newRequestQueue(ctx);
		mImageLoader = new com.android.volley.toolbox.ImageLoader(mRequestQueue, new BitmapLruCache(
				MAX_IMAGE_CACHE_ENTIRES));
	}

	public static ImageLoader getInstance(Context ctx) {
		if (instance == null) {
			instance = new ImageLoader(ctx);
		}
		return instance;
	}

	@Override
	public void display(String url, ImageView imageView, int defaultImageResId) {
		mImageLoader.get(url, com.android.volley.toolbox.ImageLoader.getImageListener(imageView, defaultImageResId,
				defaultImageResId));
	}

	@Override
	public void display(String url, ImageView imageView) {
		mImageLoader.get(url, com.android.volley.toolbox.ImageLoader.getImageListener(imageView, 0, 0));
	}

	@Override
	public void display(String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
		mImageLoader.get(url,
				com.android.volley.toolbox.ImageLoader.getImageListener(imageView, defaultImageResId, errorImageResId));

	}

}
