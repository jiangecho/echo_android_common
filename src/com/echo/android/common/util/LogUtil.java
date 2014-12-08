package com.echo.android.common.util;

/**
 * you can insert the following lines to proguard-project.txt to remove all log related methods 
 * -assumenosideeffects class android.util.Log {
 *   public static *** d(...);
 *   public static *** v(...);
 *	}
 * @author jiangecho
 *
 */
public class LogUtil {

	private static boolean DEBUG = true;
	
	private LogUtil(){
		
	}

	public static void enableLog(boolean isDebug) {
		DEBUG = isDebug;
	}

	public static void v(String tag, String message) {
		if (DEBUG) {
			android.util.Log.v(tag, message);
		}
	}

	public static void v(String tag, String format, Object... args) {
		if (DEBUG) {
			android.util.Log.v(tag, String.format(format, args));
		}
	}

}
