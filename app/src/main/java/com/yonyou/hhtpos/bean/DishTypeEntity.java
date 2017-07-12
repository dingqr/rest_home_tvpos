package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：菜品实体类
 */
public class DishTypeEntity implements Serializable {
    /**
     * 唯一标识
     */
    public String id;
    /**
     * 菜品名称
     */
    public String name;

    /**
     * 是否选中
     */
    public boolean isCheck;

    public DishTypeEntity() {
    }

    @Override
    public String toString() {
        return "DishTypeEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
