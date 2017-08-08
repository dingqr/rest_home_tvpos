package com.yonyou.hhtpos.bean.wd;

import java.io.Serializable;

/**
 * 开单成功实体类
 * 作者：liushuofei on 2017/8/8 11:06
 */
public class OpenOrderSuccessEntity implements Serializable {

    /**账单id */
    private String id;

    /**账单编号 */
    private String billNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    @Override
    public String toString() {
        return "OpenOrderSuccessEntity{" +
                "id='" + id + '\'' +
                ", billNo='" + billNo + '\'' +
                '}';
    }
}
