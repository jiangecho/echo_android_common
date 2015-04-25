package com.echo.android.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class ResourceUtil {
	private ResourceUtil() {

	}

	public static String readFileFromAssets(Context context, String fileName) {
		if (context == null || fileName == null) {
			return null;
		}

		BufferedReader bufferedReader = null;
		InputStream inputStream = null;
		StringBuilder sb = null;
		try {
			inputStream = context.getAssets().open(fileName);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			sb = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				sb.append(line);
				line = bufferedReader.readLine();
			}

			bufferedReader.close();
			inputStream.close();
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String readFileFromRaw(Context context, int resId) {
		if (context == null) {
			return null;
		}

		BufferedReader bufferedReader;
		InputStream inputStream;
		StringBuilder sb = new StringBuilder();

		try {
			inputStream = context.getResources().openRawResource(resId);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String line = bufferedReader.readLine();
			while (line != null) {
				sb.append(line);
				line = bufferedReader.readLine();
			}

			bufferedReader.close();
			inputStream.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
