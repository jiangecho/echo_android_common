package com.echo.android.common.volley;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.echo.android.common.util.LogUtil;

public class ImageLoaderListner implements ImageListener {
	private Context mContext;
	private ImageView mImageView;
	private int mDefaultImageResId;
	private int mErrorImageResId;
	
	private static final String TAG = "ImageLoaderListner";

	/**
	 * you can re-use the instance of the this class, but need to call setXXX
	 * method
	 * 
	 * @param imageView
	 * @param defaultImageResId
	 * @param errorImageResId
	 */
	public ImageLoaderListner(Context context, ImageView imageView,
			int defaultImageResId, int errorImageResId) {
		mContext = context;
		mImageView = imageView;
		mDefaultImageResId = defaultImageResId;
		mErrorImageResId = errorImageResId;
	}

	public void setImageView(ImageView imageView) {
		this.mImageView = imageView;
	}

	public void setDefaultImageResId(int defaultImageResId) {
		this.mDefaultImageResId = defaultImageResId;
	}

	public void setErrorImageResId(int errorImageResId) {
		this.mErrorImageResId = errorImageResId;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if (mErrorImageResId != 0) {
			LogUtil.v("ImageLoaderListner", error.getMessage());
			mImageView.setImageResource(mErrorImageResId);
		}
		mImageView = null;
	}

	@Override
	public void onResponse(ImageContainer response, boolean isImmediate) {
		if (response.getBitmap() != null) {
			if (isImmediate && mDefaultImageResId != 0) {
				Drawable drawable = mContext.getResources().getDrawable(mDefaultImageResId);
				TransitionDrawable transitionDrawable = new TransitionDrawable(
						new Drawable[] {
								drawable,
								new BitmapDrawable(mContext.getResources(),
										response.getBitmap()) });
				transitionDrawable.setCrossFadeEnabled(true);
				mImageView.setImageDrawable(transitionDrawable);
				transitionDrawable.startTransition(100);
			}else {
				mImageView.setImageBitmap(response.getBitmap());
			}
		}else {
			if (mDefaultImageResId != 0) {
				LogUtil.v(TAG, "load image fail");
				mImageView.setImageResource(mDefaultImageResId);
			}
		}
		
		mImageView = null;
	}

}
