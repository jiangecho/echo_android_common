package com.echo.android.common.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.ImageView;

public class VolleyManager {

	private static VolleyManager mInstance;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private ImageLoaderListner mImageLoaderListner;

	private static Context mContext;
	private static int mImageCacheSize;

	private VolleyManager(Context context, int imageCacheSize) {
		mContext = context.getApplicationContext();
		init();
	}

	public static synchronized VolleyManager getInstance(Context context) {

		mImageCacheSize = 1024 * 1024 * ((ActivityManager) (context.getSystemService(Context.ACTIVITY_SERVICE)))
				.getMemoryClass() / 3;
		return getInstance(context, mImageCacheSize);
	}

	public static synchronized VolleyManager getInstance(Context context, int imageCacheSize) {
		if (mInstance == null) {
			mInstance = new VolleyManager(context, imageCacheSize);
		}
		return mInstance;

	}

	private void init() {
		mRequestQueue = Volley.newRequestQueue(mContext);
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(mImageCacheSize));
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> request) {
		if (request != null) {
			mRequestQueue.add(request);
		}
	}

	public <T> void addToRequestQueue(Request<T> request, Object tag) {
		if (request != null) {
			request.setTag(tag);
			mRequestQueue.add(request);
		}
	}

	public void callAllRequest(Object tag) {
		mRequestQueue.cancelAll(tag);
	}

	/**
	 * you need to implement your own imageListner
	 * 
	 * @return
	 */
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public void loadImageToView(ImageView imageView, String url, int defaultImageResId, int errorImageResId) {
		if (mImageLoaderListner == null) {
			mImageLoaderListner = new ImageLoaderListner(mContext, imageView, defaultImageResId, errorImageResId);
		} else {
			mImageLoaderListner.setImageView(imageView);
			mImageLoaderListner.setDefaultImageResId(defaultImageResId);
			mImageLoaderListner.setErrorImageResId(errorImageResId);
		}

		mImageLoader.get(url, mImageLoaderListner);
	}
	
	/**
	 * need to implement your own ImageListner
	 * do not suggest to use this method
	 * @param url
	 * @param listener
	 */
	public void loadImage(String url, ImageListener listener){
		mImageLoader.get(url, listener);
	}

}
