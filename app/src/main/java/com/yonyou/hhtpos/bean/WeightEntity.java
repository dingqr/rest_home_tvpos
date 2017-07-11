package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/11.
 * 重量、价格输入框提示
 */

public class WeightEntity implements Serializable {
    /**单位*/
    private String unit;
    /**提示语*/
    private String hint;

    public WeightEntity(String unit, String hint) {
        this.unit = unit;
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "WeightEntity{" +
                "unit='" + unit + '\'' +
                ", hint='" + hint + '\'' +
                '}';
    }
}
