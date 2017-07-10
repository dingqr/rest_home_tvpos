package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/8.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class CanteenTableEntity implements Serializable {
    /**
     * 唯一标识-id
     */
    public String id;

    /**
     * 桌台名称
     */
    public String table_name;

    /**
     * 桌台可容纳最大人数
     */
    public int max_capacity;

    /**
     * 创建时间
     */
    public String create_time;
    /**
     * 是否选中
     */
    public String isCheck;

    public CanteenTableEntity() {
    }


    public void isCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "CanteenTableEntity{" +
                "id='" + id + '\'' +
                ", table_name='" + table_name + '\'' +
                ", max_capacity=" + max_capacity +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}