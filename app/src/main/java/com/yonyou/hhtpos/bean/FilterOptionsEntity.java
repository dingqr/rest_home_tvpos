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

    /**选项id： 例如外卖公司id、外卖市别id等*/
    private String optionId;

    /**多项筛选*/
    private MultipleOption multipleOption;

    /**类型：例如外卖公司；市别；餐区等 */
    private int type;

    /**是否选中 */
    private boolean isCheck;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FilterOptionsEntity{" +
                "option='" + option + '\'' +
                ", optionId='" + optionId + '\'' +
                ", multipleOption=" + multipleOption +
                ", type=" + type +
                ", isCheck=" + isCheck +
                '}';
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public MultipleOption getMultipleOption() {
        return multipleOption;
    }

    public void setMultipleOption(MultipleOption multipleOption) {
        this.multipleOption = multipleOption;
    }

    public FilterOptionsEntity() {
    }
    public FilterOptionsEntity(String option, boolean isCheck) {
        this.option = option;
        this.isCheck = isCheck;
    }

    public FilterOptionsEntity(String option, int type, boolean isCheck) {
        this.option = option;
        this.type = type;
        this.isCheck = isCheck;
    }
    public FilterOptionsEntity(MultipleOption multipleOption,int type, boolean isCheck) {
        this.multipleOption = multipleOption;
        this.type = type;
        this.isCheck = isCheck;
    }
    public FilterOptionsEntity(MultipleOption multipleOption,  String optionId,int type, boolean isCheck) {
        this.multipleOption = multipleOption;
        this.optionId = optionId;
        this.type = type;
        this.isCheck = isCheck;
    }

    public FilterOptionsEntity(String option, String optionId, int type, boolean isCheck) {
        this.option = option;
        this.optionId = optionId;
        this.type = type;
        this.isCheck = isCheck;
    }
}
