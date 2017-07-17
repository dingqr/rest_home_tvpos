package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 桌台实体类
 * 作者：ybing on 2017/6/29 18:30
 * 邮箱：ybing@yonyou.com
 */
public class TableCapacityEntity implements Serializable, Cloneable{

    /**展示选项数据 */
    /**桌台容纳的人数*/
    private int capacity;
    /**桌台所在位置*/
    private String area;

    /**传递数据 */
    private String data;

    /**类型 */
    private int type;

    /**是否被选中 */
    private boolean isCheck;



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public TableCapacityEntity() {
    }
    public TableCapacityEntity(int capacity, int type, String area, boolean isCheck) {
        this.capacity = capacity;
        this.type = type;
        this.area = area;
        this.isCheck = isCheck;
    }
}
