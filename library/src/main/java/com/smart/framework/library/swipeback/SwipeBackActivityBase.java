package com.smart.framework.library.swipeback;

/**
 * 作者：addison on 15/12/15 09:35
 * 邮箱：lsf@yonyou.com
 * 参考：https://github.com/ikew0ng/SwipeBackLayout
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
