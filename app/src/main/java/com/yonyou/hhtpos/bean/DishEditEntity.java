package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 菜品编辑实体类
 * 作者：liushuofei on 2017/7/12 19:12
 */
public class DishEditEntity implements Serializable {

    /**是否选中 */
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
