package com.beanu.arad;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.beanu.arad.core.IImageLoader;
import com.beanu.arad.image.BitmapLruCache;
import com.beanu.arad.utils.AndroidUtil;

public class ImageLoader implements IImageLoader {

	private static final int MAX_IMAGE_CACHE_ENTIRES = 5 * 1024 * 1024;// 5MB
	private static ImageLoader instance;
	public com.android.volley.toolbox.ImageLoader mImageLoader;
	private boolean loadingWhen3G = true;
	private Context context;
	private BitmapLruCache bitmapCache;

	private ImageLoader(Context ctx, RequestQueue rq) {
		context = ctx;
		bitmapCache = new BitmapLruCache(MAX_IMAGE_CACHE_ENTIRES);
		mImageLoader = new com.android.volley.toolbox.ImageLoader(rq, bitmapCache);
	}

	public static ImageLoader getInstance(Context ctx, RequestQueue rq) {
		if (instance == null) {
			instance = new ImageLoader(ctx, rq);
		}
		return instance;
	}

	public void clearAll() {
		if (bitmapCache != null)
			bitmapCache.evictAll();
	}

	@Override
	public void display(String url, ImageView imageView, int defaultImageResId) {
		if (!loadingWhen3G && !AndroidUtil.isWifiNetworkAvailable(context))
			imageView.setImageResource(defaultImageResId);
		else
			mImageLoader.get(url, com.android.volley.toolbox.ImageLoader.getImageListener(imageView, defaultImageResId,
					defaultImageResId));

	}

	@Override
	public void display(String url, ImageView imageView) {
		if (!loadingWhen3G && !AndroidUtil.isWifiNetworkAvailable(context)) {
		} else {
			mImageLoader.get(url, com.android.volley.toolbox.ImageLoader.getImageListener(imageView, 0, 0));
		}
	}

	@Override
	public void display(String url, ImageView imageView, int defaultImageResId, int maxWidth, int maxHeight) {
		if (!loadingWhen3G && !AndroidUtil.isWifiNetworkAvailable(context)) {
		} else {
			mImageLoader.get(url, com.android.volley.toolbox.ImageLoader.getImageListener(imageView, 0, 0), maxWidth,
					maxHeight);
		}

	}

	@Override
	public void display(String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
		if (!loadingWhen3G && !AndroidUtil.isWifiNetworkAvailable(context))
			imageView.setImageResource(defaultImageResId);
		else
			mImageLoader.get(url, com.android.volley.toolbox.ImageLoader.getImageListener(imageView, defaultImageResId,
					errorImageResId));

	}

	@Override
	public boolean setLoadingWhen3G(boolean load) {
		loadingWhen3G = load;
		return loadingWhen3G;
	}

}
