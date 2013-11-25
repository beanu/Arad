package com.beanu.arad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.beanu.arad.core.IImageLoader;
import com.beanu.arad.image.BitmapLruCache;
import com.beanu.arad.utils.AndroidUtil;

/**
 * @see com.squareup.picasso.Picasso instead of {@link com.beanu.arad.ImageLoader}
 */
@Deprecated
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
			mImageLoader.get(url, getImageListener(imageView, defaultImageResId,
                    defaultImageResId, true));

	}

	@Override
	public void display(String url, ImageView imageView) {
		if (!loadingWhen3G && !AndroidUtil.isWifiNetworkAvailable(context)) {
		} else {
			mImageLoader.get(url, getImageListener(imageView, 0, 0, true));
		}
	}

	@Override
	public void display(String url, ImageView imageView, int defaultImageResId, int maxWidth, int maxHeight) {
		if (!loadingWhen3G && !AndroidUtil.isWifiNetworkAvailable(context)) {
		} else {
			mImageLoader.get(url, getImageListener(imageView, 0, 0, true), maxWidth,
					maxHeight);
		}

	}

	@Override
	public void display(String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
		if (!loadingWhen3G && !AndroidUtil.isWifiNetworkAvailable(context))
			imageView.setImageResource(defaultImageResId);
		else
			mImageLoader.get(url, getImageListener(imageView, defaultImageResId, errorImageResId, true));

	}

	@Override
	public boolean setLoadingWhen3G(boolean load) {
		loadingWhen3G = load;
		return loadingWhen3G;
	}

    public static com.android.volley.toolbox.ImageLoader.ImageListener getImageListener(final ImageView view, final int defaultImageResId, final int errorImageResId, final boolean shouldAnimate) {

        final long ANIMATION_DURATION_MS = 300;
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            final Resources mResources = view.getContext().getResources();
//        }
        return new com.android.volley.toolbox.ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                }
            }

            @SuppressLint("NewApi")
            @Override
            public void onResponse(com.android.volley.toolbox.ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    if (shouldAnimate && !isImmediate) {
                        // Animation
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            view.setAlpha(0f);
                            view.setImageBitmap(response.getBitmap());
                            view.animate().alpha(1f).setDuration(ANIMATION_DURATION_MS);
                        } else {
                            TransitionDrawable td = new TransitionDrawable(new Drawable[] {
                                    new ColorDrawable(android.R.color.transparent),
                                    new BitmapDrawable(mResources, response.getBitmap())
                            });
                            view.setImageDrawable(td);
                            td.startTransition((int) ANIMATION_DURATION_MS);
                        }
                    } else {
                        view.setImageBitmap(response.getBitmap());
                    }
                } else if (defaultImageResId != 0) {
                    view.setImageResource(defaultImageResId);
                }
            }
        };
    }

}
