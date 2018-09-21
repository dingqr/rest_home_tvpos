package com.smart.tvpos.bean;

import com.smart.tvpos.interfaces.BarChartEntity;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：
 */
public class ChartCommonEntity implements BarChartEntity {
    public String keyName;
    public int value;
    //饼状图显示的文字
    public String showTxt;
    public int totalCount;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getShowTxt() {
        return showTxt;
    }

    public void setShowTxt(String showTxt) {
        this.showTxt = showTxt;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int getDataNum() {
        return value;
    }
}
