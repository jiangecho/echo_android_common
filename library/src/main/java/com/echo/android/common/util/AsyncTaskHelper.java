package com.echo.android.common.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

public class AsyncTaskHelper {

	public static <Params, T extends AsyncTask<Params, ?, ?>> void executeParallely(T task) {
		executeParallely(task, (Params[]) null);
	}

	@SuppressLint("NewApi")
	public static <Params, T extends AsyncTask<Params, ?, ?>> void executeParallely(T task, Params... params) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			task.execute(params);
		}
	}

	public static <Params, T extends AsyncTask<Params, ?, ?>> void executeSerially(T task) {
		executeSerially(task, (Params[]) null);
	}

	@SuppressLint("NewApi")
	public static <Params, T extends AsyncTask<Params, ?, ?>> void executeSerially(T task, Params... params) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
			task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
		} else {
			task.execute(params);
		}

	}


}
