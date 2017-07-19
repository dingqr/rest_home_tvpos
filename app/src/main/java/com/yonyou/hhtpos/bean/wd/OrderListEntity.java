package com.yonyou.hhtpos.bean.wd;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;

/**
 * 外带列表实体类
 * 作者：liushuofei on 2017/7/5 10:44
 */
public class OrderListEntity implements Serializable {

    /**账单编号 */
    private String billNo;
    /**账单金额 */
    private String billMoney;
    /**支付状态（Y/N） */
    private String payStatus;
    /**开单时间 */
    private long opentime;
    /**结账时间 */
    private long billTime;
    /**是否选中 */
    private boolean isCheck;
    /**账单id*/
    public String id;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillMoney() {
        return StringUtil.getFormattedMoney(billMoney);
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public long getOpentime() {
        return opentime;
    }

    public void setOpentime(long opentime) {
        this.opentime = opentime;
    }

    public long getBillTime() {
        return billTime;
    }

    public void setBillTime(long billTime) {
        this.billTime = billTime;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderListEntity{" +
                "billNo='" + billNo + '\'' +
                ", billMoney='" + billMoney + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", opentime=" + opentime +
                ", billTime=" + billTime +
                ", isCheck=" + isCheck +
                ", id='" + id + '\'' +
                '}';
    }
}
