package com.yonyou.hhtpos.bean.wd;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带-点菜明细列表实体类
 */
public class WDDishDetaiListlEntity implements Serializable {
    /**
     * 用于记录在同一时间下单的菜品的总数量标记
     */
    public String totalCount;
    /**
     * 下单时间
     */
    public Long orderTime;
    /**
     * 菜品名
     */
    public String dishName;

    /**
     * 菜品价格
     */
    private String dishPrice;
    /**
     * 会员价格
     */
    public String memberPrice;

    /**
     * 菜品Id
     */
    public String id;
    /**
     * 菜品类型
     */
    public String dishType;
    /**
     * 菜品数量
     */
    public String quantity;

    /**
     * 菜品规格
     */
    public String standardName;

    public String getDishPrice() {
        return StringUtil.getFormattedMoney(dishPrice);
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getMemberPrice() {
        return StringUtil.getFormattedMoney(memberPrice);
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    @Override
    public String toString() {
        return "WDDishDetaiListlEntity{" +
                "totalCount='" + totalCount + '\'' +
                ", orderTime=" + orderTime +
                ", dishName='" + dishName + '\'' +
                ", dishPrice='" + dishPrice + '\'' +
                ", memberPrice='" + memberPrice + '\'' +
                ", id='" + id + '\'' +
                ", dishType='" + dishType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", standardName='" + standardName + '\'' +
                '}';
    }
}
