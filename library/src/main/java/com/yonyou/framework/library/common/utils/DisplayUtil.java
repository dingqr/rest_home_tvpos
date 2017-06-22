package com.yonyou.framework.library.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 作者：addison on 8/1/16 13:49
 * 邮箱：lsf@yonyou.com
 * 当前屏幕显示信息
 */
public class DisplayUtil {
    private static final String TAG = DisplayUtil.class.getSimpleName();

    /**
     * 获取 显示信息
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm;
    }

    /**
     * 打印 显示信息
     */
    public static DisplayMetrics printDisplayInfo(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
            StringBuilder sb = new StringBuilder();
            sb.append("_______  显示信息:  ");
            sb.append("\ndensity         :").append(dm.density);
            sb.append("\ndensityDpi      :").append(dm.densityDpi);
            sb.append("\nheightPixels    :").append(dm.heightPixels);
            sb.append("\nwidthPixels     :").append(dm.widthPixels);
            sb.append("\nscaledDensity   :").append(dm.scaledDensity);
            sb.append("\nxdpi            :").append(dm.xdpi);
            sb.append("\nydpi            :").append(dm.ydpi);
            Log.i(TAG, sb.toString());
        return dm;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
