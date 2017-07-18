package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 作者：liushuofei on 2017/7/17 09:58
 * 邮箱：lsf@yonyou.com
 */
public class TakeOutListEntity implements Serializable {

    /**外卖类型 */
    private String takeOutCompanyId;

    /**下单时间 */
    private String createTime;

    /**订单金额 */
    private String billMoney;

    /**姓名 */
    private String name;

    /**手机号 */
    private String phone;

    /**订单状态 */
    private String dinnerState;

    /**选中状态 */
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTakeOutCompanyId() {
        return takeOutCompanyId;
    }

    public void setTakeOutCompanyId(String takeOutCompanyId) {
        this.takeOutCompanyId = takeOutCompanyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDinnerState() {
        return dinnerState;
    }

    public void setDinnerState(String dinnerState) {
        this.dinnerState = dinnerState;
    }

    @Override
    public String toString() {
        return "TakeOutListEntity{" +
                "takeOutCompanyId='" + takeOutCompanyId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", billMoney='" + billMoney + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", dinnerState='" + dinnerState + '\'' +
                '}';
    }
}