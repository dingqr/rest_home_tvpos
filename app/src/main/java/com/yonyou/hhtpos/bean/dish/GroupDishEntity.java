package com.yonyou.hhtpos.bean.dish;

import com.yonyou.hhtpos.bean.DishDetailEntity;

import java.util.List;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-套餐类菜品实体类
 */
public class GroupDishEntity {

    /**
     * 分组名
     */
    public String name;
    /**
     * 可选菜品数量
     */
    public int allowDishNum;
    /**
     * 公司id
     */
    public String companyId;
    /**
     * 菜品组菜品明细
     */
    public List<DishDetailEntity> detail;
    /***/
    public String dishRelateId;
    /**
     * id
     */
    public String id;
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
