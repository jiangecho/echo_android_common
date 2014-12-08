package com.echo.android.common.util;

import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

	private SharedPreferences mSharedPreferences = null;
	private static SharedPreferencesUtil mInstance;

	private SharedPreferencesUtil(Context context, String name, int mode) {
		mSharedPreferences = context.getSharedPreferences(name, mode);
	}

	/**
	 * create a default sharedPreferences, the name is the package name and the
	 * mode is Context.MODE_PRIVATE
	 * 
	 * @param context
	 * @return
	 */
	public static SharedPreferencesUtil getInstance(Context context) {
		return getInstance(context, context.getApplicationContext().getPackageName(),
				Context.MODE_PRIVATE);
	}

	public static SharedPreferencesUtil getInstance(Context context,
			String name, int mode) {
		if (mInstance == null) {
			mInstance = new SharedPreferencesUtil(context, name, mode);
		}
		return mInstance;
	}

	public void putBoolean(String key, boolean value) {
		mSharedPreferences.edit().putBoolean(key, value).commit();
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return mSharedPreferences.getBoolean(key, defaultValue);
	}

	public void putInt(String key, int value) {
		mSharedPreferences.edit().putInt(key, value).commit();
	}

	public int getInt(String key, int defaultValue) {
		return mSharedPreferences.getInt(key, defaultValue);
	}

	public void putLong(String key, long value) {
		mSharedPreferences.edit().putLong(key, value).commit();
	}

	public long getLong(String key, long defaultValue) {
		return mSharedPreferences.getLong(key, defaultValue);
	}

	public void putFloat(String key, float value) {
		mSharedPreferences.edit().putFloat(key, value).commit();
	}

	public float getFloat(String key, float defaultValue) {
		return mSharedPreferences.getFloat(key, defaultValue);
	}

	public void putString(String key, String value) {
		mSharedPreferences.edit().putString(key, value).commit();
	}

	public String getString(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}

	@SuppressLint("NewApi")
	public void putStringSet(String key, Set<String> values) {
		mSharedPreferences.edit().putStringSet(key, values).commit();
	}

	@SuppressLint("NewApi")
	public Set<String> getStringSet(String key, Set<String> defaultValues) {
		return mSharedPreferences.getStringSet(key, defaultValues);
	}

}
