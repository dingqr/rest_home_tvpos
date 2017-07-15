package com.yonyou.hhtpos.bean.dish;

import java.util.List;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-右侧导航栏菜品类型实体类
 */
public class DishTypesEntity {
    /**
     * 公司id
     */
    public String companyId;

    /**
     * 菜类编码
     */
    public String dishTypeCode;
    /**
     * 菜类名称
     */
    public String dishTypeName;
    /**
     * 菜品列表
     */
    public List<DishesEntity> dishes;
    /**
     * 关联id
     */
    public String relateId;
    /**
     * 门店id
     */
    public String shopId;
    /**
     * 排序号
     */
    public String sortNo;
}
