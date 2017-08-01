package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by zj on 2017/6/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：门店实体类
 */
public class StoreEntity implements Serializable {
    public StoreEntity() {
    }
    /**唯一标识*/
    public String id;
    /**门店名称*/
    public String store_name;
    /**门店地址*/
    public String store_loaction;

    /**门店名称*/
    public String shopName;
    /**门店id*/
    public String shopId;
    /**门店地址*/
    public String shopAddress;

    @Override
    public String toString() {
        return "StoreEntity{" +
                "id='" + id + '\'' +
                ", store_name='" + store_name + '\'' +
                ", store_loaction='" + store_loaction + '\'' +
                '}';
    }
}

