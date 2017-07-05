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

    public TableCapacityEntity clone() {
        TableCapacityEntity bean = null;
        try {
            bean = (TableCapacityEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
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
    public TableCapacityEntity(int capacity, boolean isCheck) {
        this.capacity = capacity;
        this.isCheck = isCheck;
    }

    public TableCapacityEntity(int capacity, int type, boolean isCheck) {
        this.capacity = capacity;
        this.type = type;
        this.isCheck = isCheck;
    }
    public TableCapacityEntity(int capacity, int type, String area, boolean isCheck) {
        this.capacity = capacity;
        this.type = type;
        this.area = area;
        this.isCheck = isCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableCapacityEntity)) return false;

        TableCapacityEntity that = (TableCapacityEntity) o;

        if (getCapacity() != that.getCapacity()) return false;
        if (getType() != that.getType()) return false;
        if (isCheck() != that.isCheck()) return false;
        if (!getArea().equals(that.getArea())) return false;
        return getData() != null ? getData().equals(that.getData()) : that.getData() == null;

    }

    @Override
    public int hashCode() {
        int result = getCapacity();
        result = 31 * result + getArea().hashCode();
        result = 31 * result + (getData() != null ? getData().hashCode() : 0);
        result = 31 * result + getType();
        result = 31 * result + (isCheck() ? 1 : 0);
        return result;
    }
}
