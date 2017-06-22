package com.yonyou.framework.library.common.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 多次Toast，只显示一次
 * 作者：liushuofei on 2017/1/10 18:10
 * 邮箱：lsf@yonyou.com
 */
public class CustomToast {
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(Context mContext, String text, int duration){
        mHandler.removeCallbacks(r);
        if (mToast != null)
            mToast.setText(text);
        else
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, duration);
        mToast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }
}
