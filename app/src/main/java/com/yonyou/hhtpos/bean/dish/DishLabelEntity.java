package com.yonyou.hhtpos.bean.dish;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖-菜品标签实体类
 */
public class DishLabelEntity {
    /**
     * 编码
     */
    public String labelCode;
    /**
     * 名称
     */
    public String labelName;
    /**
     * 关联ID
     */
    public String relateId;


    public DishLabelEntity() {
    }

    @Override
    public String toString() {
        return "DishLabelEntity{" +
                "labelCode='" + labelCode + '\'' +
                ", labelName='" + labelName + '\'' +
                ", relateId='" + relateId + '\'' +
                '}';
    }
    public DishLabelEntity(String labelName){
        this.labelName = labelName;
    }
}
