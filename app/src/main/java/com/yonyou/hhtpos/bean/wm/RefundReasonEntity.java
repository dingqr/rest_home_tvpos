package com.yonyou.hhtpos.bean.wm;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/22.
 * 外卖退款原因
 */

public class RefundReasonEntity implements Serializable {
    private String reasonName;

    public RefundReasonEntity() {
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    @Override
    public String toString() {
        return "RefundReasonEntity{" +
                "reasonName='" + reasonName + '\'' +
                '}';
    }
}
