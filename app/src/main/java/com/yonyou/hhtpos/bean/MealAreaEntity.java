package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 餐区实体类
 * 作者：liushuofei on 2017/7/8 15:58
 */
public class MealAreaEntity implements Serializable {

    /**是否选中 */
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
