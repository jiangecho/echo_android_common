package com.echo.android.common.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by jiangecho on 15/5/7.
 */
public class PackageManagerUtil {
    /**
     * you can use adb shell dumpsys activity activities to check the package name and class name
     * @param context
     * @param packageName
     * @param className
     * @return
     */
    public static boolean startApp(Context context, String packageName, String className){

        boolean result = false;
        if (context == null || TextUtils.isEmpty(packageName) || TextUtils.isEmpty(className)){
            return false;
        }

        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);

            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(packageName, className);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            context.startActivity(intent);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
