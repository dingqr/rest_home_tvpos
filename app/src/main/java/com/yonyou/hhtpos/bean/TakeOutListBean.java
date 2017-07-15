package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 外卖列表bean
 * 作者：liushuofei on 2017/7/6 15:14
 */
public class TakeOutListBean implements Serializable {

    /**是否选中 */
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "TakeOutListBean{" +
                "isCheck=" + isCheck +
                '}';
    }
}
