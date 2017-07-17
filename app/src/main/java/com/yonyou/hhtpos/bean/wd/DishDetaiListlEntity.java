package com.yonyou.hhtpos.bean.wd;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带-点菜明细列表实体类
 */
public class DishDetaiListlEntity implements Serializable {
    /**下单时间*/
    public String orderTime;
    /**
     * 菜品名
     */
    public String dishName;

    /**
     * 菜品价格
     */
    public String dishPrice;

    /**
     * 菜品Id
     */
    public String id;

    /**
     * 菜品数量
     */
    public String quantity;
}
