package com.beanu.arad;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.widget.ImageView;

import com.beanu.arad.core.IImageLoader;

public class ImageLoader implements IImageLoader {

	private static ImageLoader instance;

	private FinalBitmap finalBitmap;

	private ImageLoader(Context ctx, String cacheFolder) {
		finalBitmap = FinalBitmap.create(ctx);// TODO 缓存的目录
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
		finalBitmap.configLoadingImage(null);
		finalBitmap.configLoadfailImage(null);
		finalBitmap.display(imageView, url);
	}

}
