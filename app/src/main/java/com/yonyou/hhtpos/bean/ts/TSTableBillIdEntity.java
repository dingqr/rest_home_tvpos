package com.yonyou.hhtpos.bean.ts;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/28.
 */

public class TSTableBillIdEntity implements Serializable {
    private String tableBillId;

    public String getTableBillId() {
        return tableBillId;
    }

    public void setTableBillId(String tableBillId) {
        this.tableBillId = tableBillId;
    }

    @Override
    public String toString() {
        return "TSTableBillIdEntity{" +
                "tableBillId='" + tableBillId + '\'' +
                '}';
    }
}
