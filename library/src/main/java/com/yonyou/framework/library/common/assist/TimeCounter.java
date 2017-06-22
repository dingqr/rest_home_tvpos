package com.yonyou.framework.library.common.assist;

import com.yonyou.framework.library.common.log.Elog;

/**
 * 作者：addison on 8/1/16 10:54
 * 邮箱：lsf@yonyou.com
 */
public class TimeCounter {
    private static final String TAG = TimeCounter.class.getSimpleName();
    private long t;

    public TimeCounter() {
        start();
    }

    /**
     * Count start.
     */
    public long start() {
        t = System.currentTimeMillis();
        return t;
    }

    /**
     * Get duration and restart.
     */
    public long durationRestart() {
        long now = System.currentTimeMillis();
        long d = now - t;
        t = now;
        return d;
    }

    /**
     * Get duration.
     */
    public long duration() {
        return System.currentTimeMillis() - t;
    }

    /**
     * Print duration.
     */
    public void printDuration(String tag) {
        Elog.i(TAG, tag + " :  " + duration());
    }

    /**
     * Print duration.
     */
    public void printDurationRestart(String tag) {
        Elog.i(TAG, tag + " :  " + durationRestart());
    }
}
