package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 外带列表实体类
 * 作者：liushuofei on 2017/7/5 10:44
 */
public class PackingListBean implements Serializable {

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
        return "PackingListBean{" +
                "isCheck=" + isCheck +
                '}';
    }
}
