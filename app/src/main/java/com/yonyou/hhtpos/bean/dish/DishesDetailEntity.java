package com.yonyou.hhtpos.bean.dish;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-点菜-菜品组菜品明细
 */
public class DishesDetailEntity {

    /**
     * 加价
     */
    public String addPrice;
    /**
     * 公司id
     */
    public String companyId;

    /**
     * 菜品名称
     */
    public String dishName;
    /**
     * 菜品关联id
     */
    public String dishRelateId;
    /**
     * dr
     */
    public String dr;
    /**
     * enable
     */
    public String enable;
    /**
     * groupRelateId
     */
    public String groupRelateId;
    /**
     * id
     */
    public String id;
    /**
     * 险点数量
     */
    public String maxNum;
    /**
     * shopId
     */
    public String shopId;
    /**
     * sortNo
     */
    public String sortNo;
    /**
     * 规格名称
     */
    public String specName;

    public DishesDetailEntity() {
    }

    @Override
    public String toString() {
        return "DishesDetailEntity{" +
                "addPrice='" + addPrice + '\'' +
                ", companyId='" + companyId + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishRelateId='" + dishRelateId + '\'' +
                ", dr='" + dr + '\'' +
                ", enable='" + enable + '\'' +
                ", groupRelateId='" + groupRelateId + '\'' +
                ", id='" + id + '\'' +
                ", maxNum='" + maxNum + '\'' +
                ", shopId='" + shopId + '\'' +
                ", sortNo='" + sortNo + '\'' +
                ", specName='" + specName + '\'' +
                '}';
    }
}
