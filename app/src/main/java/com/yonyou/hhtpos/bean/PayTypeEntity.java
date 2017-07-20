package com.yonyou.hhtpos.bean;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;

/**
 * Created by zj on 2017/7/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：收款方式实体类
 */
public class PayTypeEntity implements Serializable {
    /**
     * 账单id
     */
    public String billId;
    /**
     * 收款方式唯一标识
     */
    public String id;
    /**
     * 支付金额
     */
    private String payAmount;

    /**
     * 支付时间
     */
    public String payTime;
    /**
     * 支付方式
     */
    public String payType;

    public PayTypeEntity() {

    }

    public String getPayAmount() {
        return StringUtil.getFormattedMoney(payAmount);
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    @Override
    public String toString() {
        return "PayTypeEntity{" +
                "billId='" + billId + '\'' +
                ", id='" + id + '\'' +
                ", payAmount='" + payAmount + '\'' +
                ", payTime='" + payTime + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }

}
