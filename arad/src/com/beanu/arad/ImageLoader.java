package com.beanu.arad;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.bitmap.core.BitmapCommonUtils;
import android.content.Context;
import android.widget.ImageView;

import com.beanu.arad.core.IImageLoader;

public class ImageLoader implements IImageLoader {

	private static ImageLoader instance;

	private FinalBitmap finalBitmap;

	private ImageLoader(Context ctx, String cacheFolder) {
		finalBitmap = FinalBitmap.create(ctx, BitmapCommonUtils.getDiskCacheDir(ctx, cacheFolder).getAbsolutePath());
	}

	public static ImageLoader getInstance(Context ctx, String cacheFolder) {
		if (instance == null) {
			instance = new ImageLoader(ctx, cacheFolder);
		}
		return instance;
	}

	@Override
	public void display(String url, ImageView imageView, int loadingImage) {
		finalBitmap.configLoadingImage(loadingImage);
		finalBitmap.configLoadfailImage(loadingImage);
		finalBitmap.display(imageView, url);
	}

	@Override
	public void display(String url, ImageView imageView) {
		finalBitmap.display(imageView, url);
	}

}
