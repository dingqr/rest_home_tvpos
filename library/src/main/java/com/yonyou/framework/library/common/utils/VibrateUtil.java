package com.yonyou.framework.library.common.utils;

import android.content.Context;
import android.os.Vibrator;

/**
 * 作者：addison on 8/1/16 15:02
 * 邮箱：lsf@yonyou.com
 *震动
 */
public class VibrateUtil {
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    public static void vibrate(Context context, long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeat);
    }
}
