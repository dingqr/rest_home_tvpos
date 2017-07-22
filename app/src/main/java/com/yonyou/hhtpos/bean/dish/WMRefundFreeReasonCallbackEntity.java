package com.yonyou.hhtpos.bean.dish;

import java.io.Serializable;

/**
 * Created by ybing on 2017/7/21.
 * 邮箱：ybing@yonyou.com
 * 描述：退款或者免单弹框选择原因的回调数据实体
 */

public class WMRefundFreeReasonCallbackEntity implements Serializable {
    /**退款原因*/
    private String  refundReason;
    /**免单原因*/
    private String  freeOrderReason;
    /**其他原因*/
    private String  otherReason;

    public WMRefundFreeReasonCallbackEntity() {
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getFreeOrderReason() {
        return freeOrderReason;
    }

    public void setFreeOrderReason(String freeOrderReason) {
        this.freeOrderReason = freeOrderReason;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    @Override
    public String toString() {
        return "WMRefundFreeReasonCallbackEntity{" +
                "refundReason='" + refundReason + '\'' +
                ", freeOrderReason='" + freeOrderReason + '\'' +
                ", otherReason=" + otherReason +
                '}';
    }
}
