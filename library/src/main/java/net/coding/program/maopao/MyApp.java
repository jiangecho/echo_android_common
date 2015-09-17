package net.coding.program.maopao;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import net.coding.program.maopao.common.Global;
import net.coding.program.maopao.third.MyImageDownloader;

import java.util.List;

/**
 * Created by cc191954 on 14-8-9.
 */
public class MyApp extends Application {

    public static float sScale;
    public static int sWidthDp;
    public static int sWidthPix;
    public static int sHeightPix;

    public static int sEmojiNormal;
    public static int sEmojiMonkey;

    public static boolean sMainCreate = false;
    public static boolean sMainActivityInForeground = false;

    private static Application sInstance;

    public static void setMainActivityState(boolean create) {
        sMainCreate = create;
    }

    public static boolean getMainActivityState() {
        return sMainCreate;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(this);

        // TODO now do not support location related feature, so ...
//        if (!PhoneType.isX86()) {
//            // x86的机器上会抛异常，因为百度没有提供x86的.so文件
//            // 只在主进程初始化lbs
//            if (this.getPackageName().equals(getProcessName(this))) {
//                SDKInitializer.initialize(this);
//            }
//        }

        sScale = getResources().getDisplayMetrics().density;
        sWidthPix = getResources().getDisplayMetrics().widthPixels;
        sHeightPix = getResources().getDisplayMetrics().heightPixels;
        sWidthDp = (int) (sWidthPix / sScale);

        sEmojiNormal = getResources().getDimensionPixelSize(R.dimen.emoji_normal);
        sEmojiMonkey = getResources().getDimensionPixelSize(R.dimen.emoji_monkey);

        sInstance = this;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean showAd = sharedPreferences.getBoolean("showAd", true);
        if (showAd){
            sharedPreferences.edit().putBoolean("showAd", false).commit();
        }

    }

    public static DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().considerExifParams(true)
            .cacheInMemory(true)
            .cacheOnDisk(true)  // Attention: enable cache in memory and disk
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
                    // TODO
                    //            .showImageForEmptyUri(R.drawable.image_not_exist)
                    //            .showImageOnFail(R.drawable.image_not_exist)
            //.showImageOnLoading(R.drawable.ic_launcher)
            .displayer(new FadeInBitmapDisplayer(300))
            .resetViewBeforeLoading(false)
            .considerExifParams(true)
            .build();

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .diskCacheFileCount(300)
                .imageDownloader(new MyImageDownloader(context))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .writeDebugLogs() // Remove for release app
                .defaultDisplayImageOptions(displayImageOptions)
                .diskCacheExtraOptions(sWidthPix / 3, sWidthPix / 3, null)
                .build();

        ImageLoader.getInstance().init(config);
    }


    private static String getProcessName(Context context) {
        ActivityManager actMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appList = actMgr.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : appList) {
            if (info.pid == android.os.Process.myPid()) {
                return info.processName;
            }
        }
        return "";
    }

    public static Application getInstance(){
        return sInstance;
    }

    public static void enable(boolean debug){
        if (debug){
            Global.HOST = Global.HOST_DEBUG;
        }else {
            Global.HOST = Global.HOST_CODING;
        }

    }

}
