package com.beanu.arad;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.widget.ImageView;

import com.beanu.arad.core.IImageLoader;

public class ImageLoader implements IImageLoader {

	private static ImageLoader instance;

	private FinalBitmap finalBitmap;

	private ImageLoader(Context ctx) {
		finalBitmap = FinalBitmap.create(ctx);
	}

	public static ImageLoader getInstance(Context ctx) {
		if (instance == null) {
			instance = new ImageLoader(ctx);
		}
		return instance;
	}

	@Override
	public void display(String url, ImageView imageView, int loadingImage) {
		finalBitmap.configLoadingImage(loadingImage);
		finalBitmap.display(imageView, url);
	}

}
