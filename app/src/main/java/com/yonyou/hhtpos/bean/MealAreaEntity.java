package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 餐区实体类
 * 作者：liushuofei on 2017/7/8 15:58
 */
public class MealAreaEntity implements Serializable {

    /**是否选中 */
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
    /**餐区名称*/
    private String diningAreaName;
    /**餐区id*/
    private String id;
    /**餐区关联id*/
    private String relateId;
    /**门店id*/
    private String shopId;
    @Override
    public String toString() {
        return "MealAreaEntity{" +
                "isCheck=" + isCheck +
                '}';
    }

    public MealAreaEntity() {

    }

    public MealAreaEntity(boolean isCheck, String diningAreaName, String shopId,String relateId) {
        this.isCheck = isCheck;
        this.diningAreaName = diningAreaName;
        this.shopId = shopId;
        this.relateId = relateId;
    }

    public String getDiningAreaName() {
        return diningAreaName;
    }

    public void setDiningAreaName(String diningAreaName) {
        this.diningAreaName = diningAreaName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
