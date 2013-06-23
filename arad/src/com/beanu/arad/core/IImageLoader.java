package com.beanu.arad.core;

import android.widget.ImageView;

public interface IImageLoader {

	public void display(String url, ImageView imageView);

	public void display(String url, ImageView imageView, int loadingImage);
}
