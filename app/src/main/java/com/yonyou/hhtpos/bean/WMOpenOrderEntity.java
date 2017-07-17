package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 外卖开单实体类
 * 作者：liushuofei on 2017/7/15 17:44
 */
public class WMOpenOrderEntity implements Serializable {

    /**地址 */
    private String address;

    /**公司id */
    private String companyId;

    /**姓名 */
    private String name;

    /**就餐人数 */
    private String personNum;

    /**电话 */
    private String phone;

    /**预约时间 */
    private String reserveTime;

    /**市别 */
    private String scheduleNameId;

    /**是否立即送餐 */
    private String sendNow;

    /**门店id */
    private String shopId;

    /**外卖公司id */
    private String takeOutCompanyId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getScheduleNameId() {
        return scheduleNameId;
    }

    public void setScheduleNameId(String scheduleNameId) {
        this.scheduleNameId = scheduleNameId;
    }

    public String getSendNow() {
        return sendNow;
    }

    public void setSendNow(String sendNow) {
        this.sendNow = sendNow;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTakeOutCompanyId() {
        return takeOutCompanyId;
    }

    public void setTakeOutCompanyId(String takeOutCompanyId) {
        this.takeOutCompanyId = takeOutCompanyId;
    }
}
