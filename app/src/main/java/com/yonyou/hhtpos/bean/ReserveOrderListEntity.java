package com.yonyou.hhtpos.bean;

import java.io.Serializable;

/**
 * 订单列表实体类
 * 作者：ybing on 2017/6/29 18:30
 * 邮箱：ybing@yonyou.com
 */
public class ReserveOrderListEntity implements Serializable, Cloneable{

    /** 订单状态 */
    private String orderStatus;

    /** 餐别 */
    private String dishType;

    /** 订单时间 */
    private long orderTime;

    /** 客人电话*/
    private String customerPhone;

    /** 客人尊称 */
    private String customerHonorific;

    /** 用餐人数 */
    private String diningNumber;

    /** 订单备注 */
    private String orderRemark;

    /** 订单Id*/
     private String orderId;

    /**是否选中*/
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public ReserveOrderListEntity(){}

    public ReserveOrderListEntity(String orderId,String orderStatus, String dishType,
                                  long orderTime, String customerPhone,
                                  String customerHonorific, String diningNumber, String orderRemark) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.dishType = dishType;
        this.orderTime = orderTime;
        this.customerPhone = customerPhone;
        this.customerHonorific = customerHonorific;
        this.diningNumber = diningNumber;
        this.orderRemark = orderRemark;
    }
    public ReserveOrderListEntity(String orderId,String orderStatus, String dishType,
                                  long orderTime, String customerPhone,
                                  String customerHonorific, String diningNumber, String orderRemark,boolean isCheck) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.dishType = dishType;
        this.orderTime = orderTime;
        this.customerPhone = customerPhone;
        this.customerHonorific = customerHonorific;
        this.diningNumber = diningNumber;
        this.orderRemark = orderRemark;
        this.isCheck = isCheck;
    }
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }


    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerHonorific() {
        return customerHonorific;
    }

    public void setCustomerHonorific(String customerHonorific) {
        this.customerHonorific = customerHonorific;
    }

    public String getDiningNumber() {
        return diningNumber;
    }

    public void setDiningNumber(String diningNumber) {
        this.diningNumber = diningNumber;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ReserveOrderListEntity{" +
                "orderStatus='" + orderStatus + '\'' +
                ", dishType='" + dishType + '\'' +
                ", orderTime=" + orderTime +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerHonorific='" + customerHonorific + '\'' +
                ", diningNumber='" + diningNumber + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
