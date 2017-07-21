package com.yonyou.hhtpos.bean.dish;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-点菜-备注列表
 */
public class DishRemarkEntity {
    /**
     * 备注关联id
     */
    public String relateId;

    /**
     * 备注编码
     */
    public String remarkCode;

    /**
     * 备注名称
     */
    public String remarkName;

    public DishRemarkEntity() {
    }

    @Override
    public String toString() {
        return "DishRemarkEntity{" +
                "relateId='" + relateId + '\'' +
                ", remarkCode='" + remarkCode + '\'' +
                ", remarkName='" + remarkName + '\'' +
                '}';
    }

    public DishRemarkEntity(String remarkName) {
        this.remarkName = remarkName;
    }
}
