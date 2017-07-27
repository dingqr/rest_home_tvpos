package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/13.
 */

public class SetMealListEntity implements Serializable{
    private String dishName;
    private String dishCookery;
    private int dishCount;
    private int dishAddPrice;

    public SetMealListEntity(){}

    public SetMealListEntity(String dishName, int dishCount, int dishAddPrice) {
        this.dishName = dishName;
        this.dishCount = dishCount;
        this.dishAddPrice = dishAddPrice;
    }
    public SetMealListEntity(String dishName,String dishCookery, int dishCount, int dishAddPrice) {
        this.dishName = dishName;
        this.dishCookery = dishCookery;
        this.dishCount = dishCount;
        this.dishAddPrice = dishAddPrice;
    }
    public String getDishCookery() {
        return dishCookery;
    }

    public void setDishCookery(String dishCookery) {
        this.dishCookery = dishCookery;
    }
    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishCount() {
        return dishCount;
    }

    public void setDishCount(int dishCount) {
        this.dishCount = dishCount;
    }

    public int getDishAddPrice() {
        return dishAddPrice;
    }

    public void setDishAddPrice(int dishAddPrice) {
        this.dishAddPrice = dishAddPrice;
    }

    @Override
    public String toString() {
        return "SetMealListEntity{" +
                "dishName='" + dishName + '\'' +
                ", dishCookery='" + dishCookery + '\'' +
                ", dishCount=" + dishCount +
                ", dishAddPrice=" + dishAddPrice +
                '}';
    }
}
