package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 外带开单接口
 * 作者：liushuofei on 2017/7/18 10:47
 */
public class WDOpenOrderEntity implements Serializable {

    /**会员手机号 */
    private String mobileNo;
    /**销售模式 */
    private String salesMode;
    /**门店Id */
    private String shopId;
    /**桌台Id */
    private String tableId;
    /**服务员Id */
    private String waiterId;
    /**服务员名称 */
    private String waiterName;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSalesMode() {
        return salesMode;
    }

    public void setSalesMode(String salesMode) {
        this.salesMode = salesMode;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    @Override
    public String toString() {
        return "WDOpenOrderEntity{" +
                "mobileNo='" + mobileNo + '\'' +
                ", salesMode='" + salesMode + '\'' +
                ", shopId='" + shopId + '\'' +
                ", tableId='" + tableId + '\'' +
                ", waiterId='" + waiterId + '\'' +
                ", waiterName='" + waiterName + '\'' +
                '}';
    }
}
