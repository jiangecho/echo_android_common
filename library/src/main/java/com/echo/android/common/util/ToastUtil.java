package com.echo.android.common.util;

import android.content.Context;

/**
 * the default length is short
 * @author jiangecho
 *
 */
public class ToastUtil {

	private ToastUtil() {

	}

	public static final int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;
	public static final int LENGHT_SHORT = android.widget.Toast.LENGTH_SHORT;

	public static void show(Context context, int resId) {
		show(context, resId, LENGHT_SHORT);
	}

	public static void show(Context context, int resId, int duration) {
		android.widget.Toast.makeText(context, resId, duration).show();
	}

	public static void show(Context context, String msg) {
		show(context, msg, LENGHT_SHORT);
	}

	public static void show(Context context, String msg, int duration) {
		android.widget.Toast.makeText(context, msg, duration).show();
	}

	public static void show(Context context, String format, Object... args) {
		show(context, String.format(format, args));
	}

	public static void show(Context context, int duration, String format,
			Object... args) {
		show(context, duration, String.format(format, args));
	}

}
