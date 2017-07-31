package com.yonyou.hhtpos.bean.dish;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-点菜-菜品规格实体类
 */
public class DishStandardEntity {
    /**
     * 规格关联id
     */
    public String relateId;
    /**
     * 菜品展示名称
     */
    public String showName;
    /**
     * 规格编码
     */
    public String standardCode;
    /**
     * 规格名称
     */
    public String standardName;
    /**
     * 选中状态
     */
    public boolean isCheck;

    public DishStandardEntity() {
    }

    @Override
    public String toString() {
        return "DishStandardEntity{" +
                "relateId='" + relateId + '\'' +
                ", showName='" + showName + '\'' +
                ", standardCode='" + standardCode + '\'' +
                ", standardName='" + standardName + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }

    public DishStandardEntity(String standardName) {
        this.standardName = standardName;
    }
}
