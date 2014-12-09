package com.echo.android.common.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageUtil {

	private ImageUtil() {

	}

	public static int getImageSize(Bitmap bitmap) {
		// TODO
		return 0;
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
