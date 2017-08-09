package com.yonyou.hhtpos.bean.wm;

import java.io.Serializable;

/**
 * 外卖开单实体类
 * 作者：liushuofei on 2017/7/15 17:44
 */
public class OpenOrderEntity implements Serializable {

    /**地址 */
    private String address;

    /**姓名 */
    private String name;

    /**就餐人数 */
    private String personNum;

    /**电话 */
    private String phone;

    /**预约时间 */
    private String reserveTime;

    /**是否立即送餐 */
    private String sendNow;

    /**门店id */
    private String shopId;

    /**外卖公司id */
    private String takeOutCompanyId;

    /**外卖编号 */
    private String takeOutNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTakeOutNumber() {
        return takeOutNumber;
    }

    public void setTakeOutNumber(String takeOutNumber) {
        this.takeOutNumber = takeOutNumber;
    }

    @Override
    public String toString() {
        return "OpenOrderEntity{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", personNum='" + personNum + '\'' +
                ", phone='" + phone + '\'' +
                ", reserveTime='" + reserveTime + '\'' +
                ", sendNow='" + sendNow + '\'' +
                ", shopId='" + shopId + '\'' +
                ", takeOutCompanyId='" + takeOutCompanyId + '\'' +
                ", takeOutNumber='" + takeOutNumber + '\'' +
                '}';
    }
}
