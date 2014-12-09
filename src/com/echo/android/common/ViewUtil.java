package com.echo.android.common.util;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ViewUtil {
	private ViewUtil() {

	}

	/**
	 * take screen shot and return the absolute path of the file
	 * 
	 * @param view
	 * @return
	 */
	public static String takeScreenShot(View view) {
		View rootView = view.getRootView();
		rootView.setDrawingCacheEnabled(true);
		rootView.buildDrawingCache(true);
		Bitmap bitmap = rootView.getDrawingCache(true);
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			LogUtil.v("takeScreenShot", "take screen shot failed due to sdcard does not exist");
			return null;
		}
		File path = Environment.getExternalStorageDirectory();
		File file = new File(path, System.currentTimeMillis() + ".png");

		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		rootView.destroyDrawingCache();
		return file.getAbsolutePath();
	}

	/**
	 * calculate the listView's height. if you want to use listView in a
	 * scrollView, you need to set the listView's height manually
	 * 
	 * @param listView
	 * @return
	 */
	public static int getListViewHeight(ListView listView) {
		ListAdapter adapter;

		if (listView == null || (adapter = listView.getAdapter()) == null) {
			return -1;
		}

		int height = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View item = adapter.getView(i, null, listView);
			if (item instanceof ViewGroup) {
				item.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}

			item.measure(0, 0);
			height += item.getMeasuredHeight();
		}
		height += (listView.getPaddingBottom() + listView.getPaddingTop());

		height += listView.getDividerHeight() * (adapter.getCount() - 1);

		return height;
	}

	public static void setViewHeight(View view, int height) {
		if (view == null) {
			return;
		}

		LayoutParams params = view.getLayoutParams();
		params.height = height;
	}

	public static int getScreenWidth(Context context) {
		if (context == null) {
			return -1;
		}

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return metrics.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		if (context == null) {
			return -1;
		}

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return metrics.heightPixels;

	}
}
