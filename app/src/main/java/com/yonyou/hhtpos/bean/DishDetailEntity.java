package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/5.
 * 邮箱：zjuan@yonyou.com
 * 描述：点菜明细实体类
 */
public class DishDetailEntity implements Serializable{
    /**下单时间*/
    public String order_time;
    /**菜品名称*/
    public String dishes_name;
}
