package net.coding.program.maopao.common.enter;

import android.graphics.drawable.Drawable;
import cn.pocdoc.callme.MainApplication;

/**
 * Created by chaochen on 14-11-12.
 */
public class DrawableTool {
    public static void zoomDrwable(Drawable drawable, boolean isMonkey) {
        int width = isMonkey ? MainApplication.sEmojiMonkey : MainApplication.sEmojiNormal;
        drawable.setBounds(0, 0, width, width);
    }
}
