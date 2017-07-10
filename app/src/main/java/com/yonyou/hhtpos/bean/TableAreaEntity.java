package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/10.
 * 邮箱：zjuan@yonyou.com
 * 描述：餐区实体类-用于筛选桌台
 */
public class TableAreaEntity implements Serializable {
    /**
     * 唯一标识
     */
    public String id;

    /**
     * 餐区名称
     */
    public String name;

    public TableAreaEntity() {
    }

    @Override
    public String toString() {
        return "TableAreaEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
