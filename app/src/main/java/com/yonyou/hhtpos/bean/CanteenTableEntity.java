package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/8.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-桌台实体类
 */
public class CanteenTableEntity implements Serializable {
    /**
     * relReceiveAmount
     */
    public String relReceiveAmount;
    /**
     * 桌台账单-id
     */
    public String tableBillId;
    /**
     * 桌台id
     */
    public String tableId;

    /**
     * 桌台名称
     */
    public String tableName;

    /**
     * 桌台状态
     */
    public String tableStatus;

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

}