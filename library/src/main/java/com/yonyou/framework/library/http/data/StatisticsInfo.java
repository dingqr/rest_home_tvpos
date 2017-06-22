package com.yonyou.framework.library.http.data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 作者：addison on 15/12/15 18:24
 * 邮箱：lsf@yonyou.com
 */
public class StatisticsInfo {
    private AtomicLong connectTime = new AtomicLong();
    private AtomicLong dataLength  = new AtomicLong();

    public void addConnectTime(long time) {
        connectTime.addAndGet(time);
    }

    public void addDataLength(long len) {
        dataLength.addAndGet(len);
    }

    public long getConnectTime() {
        return connectTime.longValue();
    }

    public long getDataLength() {
        return dataLength.longValue();
    }

    @Override
    public String toString() {
        return "StatisticsInfo{" +
                "connectTime=" + connectTime +
                ", dataLength=" + dataLength +
                '}';
    }
}
