package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 餐厅预定筛选控件化数据实体类
 * 作者：liushuofei on 2017/1/17 18:30
 * 邮箱：lsf@yonyou.com
 */
public class FilterDataEntity implements Serializable, Cloneable{

    /**展示数据 */
    private String txt;

    /**传递数据 */
    private String data;

    /**类型 */
    private int type;

    /**是否选中 */
    private boolean isCheck;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
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

    public FilterDataEntity clone() {
        FilterDataEntity bean = null;
        try {
            bean = (FilterDataEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public String toString() {
        return "FilterDataEntity{" +
                "txt='" + txt + '\'' +
                ", data='" + data + '\'' +
                ", type=" + type +
                ", isCheck=" + isCheck +
                '}';
    }
    public FilterDataEntity() {
    }
    public FilterDataEntity(String txt,boolean isCheck) {
        this.txt = txt;
        this.isCheck = isCheck;
    }
    /**type:0 餐别 1 餐区 2 预定状态*/
    public FilterDataEntity(String txt,int type,boolean isCheck) {
        this.txt = txt;
        this.type = type;
        this.isCheck = isCheck;
    }
}
