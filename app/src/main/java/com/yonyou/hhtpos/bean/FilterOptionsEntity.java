package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 餐厅预定筛选选项实体类
 * 作者：liushuofei on 2017/1/17 18:30
 * 邮箱：lsf@yonyou.com
 */
public class FilterOptionsEntity implements Serializable, Cloneable{

    /**展示选项数据 */
    private String option;

    /**传递数据 */
    private String data;

    /**类型 */
    private int type;

    /**是否选中 */
    private boolean isCheck;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public FilterOptionsEntity clone() {
        FilterOptionsEntity bean = null;
        try {
            bean = (FilterOptionsEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public String toString() {
        return "FilterOptionsEntity{" +
                "option='" + option + '\'' +
                ", data='" + data + '\'' +
                ", type=" + type +
                ", isCheck=" + isCheck +
                '}';
    }

    public FilterOptionsEntity() {
    }
    public FilterOptionsEntity(String option, boolean isCheck) {
        this.option = option;
        this.isCheck = isCheck;
    }
    /**type:0 餐别 1 餐区 2 预定状态*/
    public FilterOptionsEntity(String option, int type, boolean isCheck) {
        this.option = option;
        this.type = type;
        this.isCheck = isCheck;
    }
}
