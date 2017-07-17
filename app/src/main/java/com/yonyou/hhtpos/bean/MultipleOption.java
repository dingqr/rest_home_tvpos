package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/12.
 */

public class MultipleOption implements Serializable, Cloneable{
    private String dishType;
    private int selectedCount;
    private int totalCount;

    public MultipleOption(String dishType, int selectedCount, int totalCount) {
        this.dishType = dishType;
        this.selectedCount = selectedCount;
        this.totalCount = totalCount;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
