package com.yonyou.hhtpos.bean.wm;

import com.yonyou.framework.library.common.utils.StringUtil;

/**
 * Created by zj on 2017/7/21.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class WMDishDetailEntity {
    /**
     * 菜品名称
     */
    public String dishName;
    /**
     * 菜品价格
     */
    private String dishPrice;
    /**
     * 数量
     */
    public String quantity;

    public WMDishDetailEntity() {
    }

    public String getDishPrice() {
        return StringUtil.getFormattedMoney(dishPrice);
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public String toString() {
        return "WMDishDetailEntity{" +
                "dishName='" + dishName + '\'' +
                ", dishPrice='" + dishPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
