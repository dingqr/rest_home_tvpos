package com.yonyou.hhtpos.bean.wm;

import java.io.Serializable;

/**
 * 订单列表请求实体类
 * 作者：liushuofei on 2017/7/20 13:42
 */
public class OrderListRequestEntity implements Serializable{

    /**就餐类型 */
    private String salesMode;
    /**门店id */
    private String shopId;
    /**订单状态 */
    private String dinnerStatus;
    /**市别 */
    private String scheduleNameId;
    /**外卖公司 */
    private String takeOutCompanyId;
    /**页数 */
    private String pageNum;
    /**每页数量 */
    private String pageSize;

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

    public String getDinnerStatus() {
        return dinnerStatus;
    }

    public void setDinnerStatus(String dinnerStatus) {
        this.dinnerStatus = dinnerStatus;
    }

    public String getScheduleNameId() {
        return scheduleNameId;
    }

    public void setScheduleNameId(String scheduleNameId) {
        this.scheduleNameId = scheduleNameId;
    }

    public String getTakeOutCompanyId() {
        return takeOutCompanyId;
    }

    public void setTakeOutCompanyId(String takeOutCompanyId) {
        this.takeOutCompanyId = takeOutCompanyId;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "OrderListRequestEntity{" +
                "salesMode='" + salesMode + '\'' +
                ", shopId='" + shopId + '\'' +
                ", dinnerStatus='" + dinnerStatus + '\'' +
                ", scheduleNameId='" + scheduleNameId + '\'' +
                ", takeOutCompanyId='" + takeOutCompanyId + '\'' +
                ", pageNum='" + pageNum + '\'' +
                ", pageSize='" + pageSize + '\'' +
                '}';
    }
}
