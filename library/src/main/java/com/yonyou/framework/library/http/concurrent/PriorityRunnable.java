package com.yonyou.framework.library.http.concurrent;

/**
 * 作者：addison on 15/12/15 18:16
 * 邮箱：lsf@yonyou.com
 */
public abstract class PriorityRunnable implements Runnable {

    int priority;

    protected PriorityRunnable(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}

