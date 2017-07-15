package com.yonyou.hhtpos.bean.dish;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-点菜页面-做法实体类
 */
public class DishPriceEntity {
    /**
     * 排序号
     */
    public String sortNo;

    /**
     * 做法编码
     */
    public String practiceCode;

    /**
     * 做法名称
     */
    public String practiceName;

    /**
     * 做法关联iD
     */
    public String relateId;

    /**
     * 做法备注
     */
    public String remark;

    public DishPriceEntity() {
    }

    @Override
    public String toString() {
        return "DishPriceEntity{" +
                "sortNo='" + sortNo + '\'' +
                ", practiceCode='" + practiceCode + '\'' +
                ", practiceName='" + practiceName + '\'' +
                ", relateId='" + relateId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
