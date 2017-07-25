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
    private String  listShowPractice;
    /**做法id*/
    private String  practices;
    /**口味*/
    private String  dishTaste;
    /**备注*/
    private String  listShowRemark;
    /**备注id*/
    private String  remarks;
    /**手写备注*/
    private String remark;

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

    public String getDishTaste() {
        return dishTaste;
    }

    public void setDishTaste(String dishTaste) {
        this.dishTaste = dishTaste;
    }


    public String getDishStandardId() {
        return dishStandardId;
    }

    public void setDishStandardId(String dishStandardId) {
        this.dishStandardId = dishStandardId;
    }

    public String getListShowPractice() {
        return listShowPractice;
    }

    public void setListShowPractice(String listShowPractice) {
        this.listShowPractice = listShowPractice;
    }

    public String getPractices() {
        return practices;
    }

    public void setPractices(String practices) {
        this.practices = practices;
    }

    public String getListShowRemark() {
        return listShowRemark;
    }

    public void setListShowRemark(String listShowRemark) {
        this.listShowRemark = listShowRemark;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DishCallBackEntity{" +
                "dishCount='" + dishCount + '\'' +
                ", dishStandard='" + dishStandard + '\'' +
                ", dishStandardId='" + dishStandardId + '\'' +
                ", dishWeight=" + dishWeight +
                ", dishPrice='" + dishPrice + '\'' +
                ", listShowPractice='" + listShowPractice + '\'' +
                ", practices='" + practices + '\'' +
                ", dishTaste='" + dishTaste + '\'' +
                ", listShowRemark='" + listShowRemark + '\'' +
                ", remarks='" + remarks + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
