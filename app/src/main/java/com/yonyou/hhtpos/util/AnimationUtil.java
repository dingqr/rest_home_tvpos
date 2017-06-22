package com.yonyou.hhtpos.util;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * 渐显渐隐动画
 * 作者：liushuofei on 2017/1/11 14:39
 */
public class AnimationUtil {

    // 渐隐
    private static AlphaAnimation mHideAnimation = null;
    // 渐显
    private static AlphaAnimation mShowAnimation = null;

    /**
     * View渐隐动画效果
     */
    public static void setHideAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }

        if (null != mHideAnimation) {
            mHideAnimation.cancel();
        }

        mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
        mHideAnimation.setDuration(duration);
        mHideAnimation.setFillAfter(true);
        view.startAnimation(mHideAnimation);
    }

    /**
     * View渐现动画效果
     */
    public static void setShowAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }

        if (null != mShowAnimation) {
            mShowAnimation.cancel();
        }

        mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
        mShowAnimation.setDuration(duration);
        mShowAnimation.setFillAfter(true);
        view.startAnimation(mShowAnimation);
    }

}
