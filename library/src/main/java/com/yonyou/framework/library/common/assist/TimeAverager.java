package com.yonyou.framework.library.common.assist;

/**
 * 作者：addison on 8/1/16 10:53
 * 邮箱：lsf@yonyou.com
 * 计时均值器， 统计耗时的同时，多次调用可得出其花费时间均值。
 */
public class TimeAverager {
    /**
     * 计时器
     */
    private TimeCounter tc = new TimeCounter();
    /**
     * 均值器
     */
    private Averager av = new Averager();

    /**
     * 一个计时开始
     */
    public long start() {
        return tc.start();
    }

    /**
     * 一个计时结束
     */
    public long end() {
        long time = tc.duration();
        av.add(time);
        return time;
    }

    /**
     * 一个计时结束,并且启动下次计时。
     */
    public long endAndRestart() {
        long time = tc.durationRestart();
        av.add(time);
        return time;
    }

    /**
     * 求全部计时均值
     */
    public Number average() {
        return av.getAverage();
    }

    /**
     * 打印全部时间值
     */
    public void print() {
        av.print();
    }

    /**
     * 清楚数据
     */
    public void clear() {
        av.clear();
    }

}
