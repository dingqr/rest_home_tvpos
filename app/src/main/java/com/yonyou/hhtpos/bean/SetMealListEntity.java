package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/13.
 */

public class SetMealListEntity implements Serializable, Cloneable{
    private String dishName;
    private int dishCount;
    private int dishAddPrice;

    public SetMealListEntity(String dishName, int dishCount, int dishAddPrice) {
        this.dishName = dishName;
        this.dishCount = dishCount;
        this.dishAddPrice = dishAddPrice;
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
                ", dishCount=" + dishCount +
                ", dishAddPrice=" + dishAddPrice +
                '}';
    }


}
