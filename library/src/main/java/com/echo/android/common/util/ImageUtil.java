package com.echo.android.common.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;

public class ImageUtil {

	private ImageUtil() {

	}

	@SuppressLint("NewApi")
	public static int getImageSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		} else {
			return bitmap.getRowBytes() * bitmap.getHeight();
		}
	}

	public static Bitmap scaleTo(Bitmap bitmap, int width, int height) {
		return scale(bitmap, (float) width / bitmap.getWidth(), (float) height / bitmap.getHeight());
	}

	public static Bitmap scale(Bitmap bitmap, float scaleWidth, float scaleHeight) {
		if (bitmap == null) {
			return null;
		}

		Matrix matrix = new Matrix();
		matrix.postScale(scaleHeight, scaleHeight);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
}
