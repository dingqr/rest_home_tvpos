package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/13.
 */

public class SetMealGridEntity implements Serializable, Cloneable{
    private String dishPart;
    private String dishName;
    private int dishLimitCount;
    private float dishAddPrice;
    private boolean isCheckAble;

    public SetMealGridEntity(String dishPart, String dishName, int dishLimitCount, float dishAddPrice, boolean isCheckAble) {
        this.dishPart = dishPart;
        this.dishName = dishName;
        this.dishLimitCount = dishLimitCount;
        this.dishAddPrice = dishAddPrice;
        this.isCheckAble = isCheckAble;
    }

    public String getDishPart() {
        return dishPart;
    }

    public void setDishPart(String dishPart) {
        this.dishPart = dishPart;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishLimitCount() {
        return dishLimitCount;
    }

    public void setDishLimitCount(int dishLimitCount) {
        this.dishLimitCount = dishLimitCount;
    }

    public float getDishAddPrice() {
        return dishAddPrice;
    }

    public void setDishAddPrice(float dishAddPrice) {
        this.dishAddPrice = dishAddPrice;
    }


    public boolean isCheckAble() {
        return isCheckAble;
    }

    public void setCheckAble(boolean checkAble) {
        this.isCheckAble = checkAble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SetMealGridEntity)) return false;

        SetMealGridEntity that = (SetMealGridEntity) o;

        if (getDishLimitCount() != that.getDishLimitCount()) return false;
        if (getDishAddPrice() != that.getDishAddPrice()) return false;
        if (getDishPart() != null ? !getDishPart().equals(that.getDishPart()) : that.getDishPart() != null)
            return false;
        return getDishName() != null ? getDishName().equals(that.getDishName()) : that.getDishName() == null;

    }

    @Override
    public int hashCode() {
        int result = getDishPart() != null ? getDishPart().hashCode() : 0;
        result = 31 * result + (getDishName() != null ? getDishName().hashCode() : 0);
        result = 31 * result + (int)getDishAddPrice();
        result = 31 * result + (isCheckAble() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SetMealGridEntity{" +
                "dishPart='" + dishPart + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishLimitCount=" + dishLimitCount +
                ", dishAddPrice=" + dishAddPrice +
                ", isCheckAble=" + isCheckAble +
                '}';
    }
}
