package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 预定列表实体
 * 作者：liushuofei on 2017/6/30 16:06
 */
public class PreviewListEntity implements Serializable {

    /**是否选中 */
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
