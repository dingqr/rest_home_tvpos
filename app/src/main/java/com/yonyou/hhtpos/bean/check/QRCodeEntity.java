package com.yonyou.hhtpos.bean.check;

import java.io.Serializable;

/**
 * 扫顾客码/顾客扫码弹窗实体类
 * 作者：liushuofei on 2017/8/9 10:45
 */
public class QRCodeEntity implements Serializable {

    /**账单id */
    private String tableBillId;
    /**付款方式名称 */
    private String payType;
    /**生成二维码链接 */
    private String qrCode;
    /**未付款金额 */
    private String unpaidMoney;

    public String getTableBillId() {
        return tableBillId;
    }

    public void setTableBillId(String tableBillId) {
        this.tableBillId = tableBillId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUnpaidMoney() {
        return unpaidMoney;
    }

    public void setUnpaidMoney(String unpaidMoney) {
        this.unpaidMoney = unpaidMoney;
    }

    @Override
    public String toString() {
        return "QRCodeEntity{" +
                "tableBillId='" + tableBillId + '\'' +
                ", payType='" + payType + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", unpaidMoney='" + unpaidMoney + '\'' +
                '}';
    }
}
