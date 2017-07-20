package com.yonyou.hhtpos.bean;

/**
 * Created by zj on 2017/7/18.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖开单配送时间实体类
 */
public class DistributeTimeEntity {
    /**
     * 时
     */
    public String hour;
    /**
     * 分
     */
    public String minute;

    public DistributeTimeEntity() {
    }

    @Override
    public String toString() {
        return "DistributeTimeEntity{" +
                "hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                '}';
    }
}
