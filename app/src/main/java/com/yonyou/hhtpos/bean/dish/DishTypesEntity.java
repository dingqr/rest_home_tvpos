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
     * 菜类id
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
    /**
     * 角标数量
     */
    public int count;

    public DishTypesEntity() {
    }

    @Override
    public String toString() {
        return "DishTypesEntity{" +
                "companyId='" + companyId + '\'' +
                ", dishTypeCode='" + dishTypeCode + '\'' +
                ", dishTypeName='" + dishTypeName + '\'' +
                ", dishes=" + dishes +
                ", relateId='" + relateId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", sortNo='" + sortNo + '\'' +
                ", count=" + count +
                '}';
    }
}
