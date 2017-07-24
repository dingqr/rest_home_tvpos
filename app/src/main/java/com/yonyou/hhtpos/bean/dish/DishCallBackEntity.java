package com.yonyou.hhtpos.bean.dish;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/21.
 * 邮箱：ybing@yonyou.com
 * 描述：点菜菜品选择规格、数量、重量、价格\做法、口味后的回调实体
 */

public class DishCallBackEntity implements Serializable {
    /**数量*/
    private String  dishCount;
    /**规格*/
    private String  dishStandard;
    /**规格id*/
    private String  dishStandardId;
    /**重量*/
    private double  dishWeight;
    /**价格，时价*/
    private String  dishPrice;
    /**做法*/
    private String  dishCookery;
    /**做法id*/
    private String  dishCookeryId;
    /**口味*/
    private String  dishTaste;
    /**备注*/
    private String  dishRemark;
    /**备注id*/
    private String  dishRemarkId;

    public DishCallBackEntity(){}

    public String getDishCount() {
        return dishCount;
    }

    public void setDishCount(String dishCount) {
        this.dishCount = dishCount;
    }

    public String getDishStandard() {
        return dishStandard;
    }

    public void setDishStandard(String dishStandard) {
        this.dishStandard = dishStandard;
    }

    public double getDishWeight() {
        return dishWeight;
    }

    public void setDishWeight(double dishWeight) {
        this.dishWeight = dishWeight;
    }

    public String getDishPrice() {
        return StringUtil.getFormattedMoney(dishPrice);
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishCookery() {
        return dishCookery;
    }

    public void setDishCookery(String dishCookery) {
        this.dishCookery = dishCookery;
    }

    public String getDishTaste() {
        return dishTaste;
    }

    public void setDishTaste(String dishTaste) {
        this.dishTaste = dishTaste;
    }

    public String getDishRemark() {
        return dishRemark;
    }

    public void setDishRemark(String dishRemark) {
        this.dishRemark = dishRemark;
    }

    public String getDishCookeryId() {
        return dishCookeryId;
    }

    public void setDishCookeryId(String dishCookeryId) {
        this.dishCookeryId = dishCookeryId;
    }

    public String getDishRemarkId() {
        return dishRemarkId;
    }

    public void setDishRemarkId(String dishRemarkId) {
        this.dishRemarkId = dishRemarkId;
    }

    public String getDishStandardId() {
        return dishStandardId;
    }

    public void setDishStandardId(String dishStandardId) {
        this.dishStandardId = dishStandardId;
    }

    @Override
    public String toString() {
        return "DishCallBackEntity{" +
                "dishCount='" + dishCount + '\'' +
                ", dishStandard='" + dishStandard + '\'' +
                ", dishStandardId='" + dishStandardId + '\'' +
                ", dishWeight=" + dishWeight +
                ", dishPrice='" + dishPrice + '\'' +
                ", dishCookery='" + dishCookery + '\'' +
                ", dishCookeryId='" + dishCookeryId + '\'' +
                ", dishTaste='" + dishTaste + '\'' +
                ", dishRemark='" + dishRemark + '\'' +
                ", dishRemarkId='" + dishRemarkId + '\'' +
                '}';
    }
}
