package com.yonyou.hhtpos.bean.dish;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-点菜-菜品口味实体类
 */
public class DishTastesEntity {
    /**
     * 是否默认
     */
    public String isDefault;
    /**
     * 关联id
     */
    public String relateId;
    /**
     * 备注
     */
    public String remark;
    /**
     * 编码
     */
    public String tasteCode;
    /**
     * 口味名称
     */
    public String tasteName;

    public DishTastesEntity() {
    }

    @Override
    public String toString() {
        return "DishTastesEntity{" +
                "isDefault='" + isDefault + '\'' +
                ", relateId='" + relateId + '\'' +
                ", remark='" + remark + '\'' +
                ", tasteCode='" + tasteCode + '\'' +
                ", tasteName='" + tasteName + '\'' +
                '}';
    }
}
