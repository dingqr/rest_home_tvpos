package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 桌台实体类
 * 作者：ybing on 2017/6/29 18:30
 * 邮箱：ybing@yonyou.com
 */
public class TableReserveEntity implements Serializable, Cloneable{
    /**午餐订单数量*/
    private int lunchNum;

    /**晚餐订单数量*/
    private int supperNum;

    /** 包房名称 */
    private String roomName;

    /**是否被选中 */
    private boolean isCheck;

    public TableReserveEntity clone() {
        TableReserveEntity bean = null;
        try {
            bean = (TableReserveEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public int getLunchNum() {
        return lunchNum;
    }

    public void setLunchNum(int lunchNum) {
        this.lunchNum = lunchNum;
    }

    public int getSupperNum() {
        return supperNum;
    }

    public void setSupperNum(int supperNum) {
        this.supperNum = supperNum;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public TableReserveEntity() {

    }

    public TableReserveEntity(boolean isCheck, String roomName, int lunchNum, int supperNum) {
        this.isCheck = isCheck;
        this.roomName = roomName;
        this.lunchNum = lunchNum;
        this.supperNum = supperNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableReserveEntity)) return false;

        TableReserveEntity that = (TableReserveEntity) o;

        if (getLunchNum() != that.getLunchNum()) return false;
        if (getSupperNum() != that.getSupperNum()) return false;
        if (isCheck() != that.isCheck()) return false;
        return getRoomName() != null ? getRoomName().equals(that.getRoomName()) : that.getRoomName() == null;

    }

    @Override
    public int hashCode() {
        int result = getLunchNum();
        result = 31 * result + getSupperNum();
        result = 31 * result + (getRoomName() != null ? getRoomName().hashCode() : 0);
        result = 31 * result + (isCheck() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TableReserveEntity{" +
                "lunchNum=" + lunchNum +
                ", supperNum='" + supperNum + '\'' +
                ", roomName='" + roomName + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
