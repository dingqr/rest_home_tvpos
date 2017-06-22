package com.yonyou.framework.library.widgets.cart.mode;

import java.io.Serializable;

/**
 * 作者：liushuofei on 2016/12/14 18:35
 * 邮箱：lsf@yonyou.com
 */
public class Category implements Serializable{

    private String dishesId;

    private String dishesName;

    private boolean isCheck;

    public String getDishesId() {
        return dishesId;
    }

    public String getDishesName() {
        return dishesName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setDishesId(String dishesId) {
        this.dishesId = dishesId;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
