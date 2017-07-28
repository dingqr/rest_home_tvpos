package com.yonyou.hhtpos.bean.check;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2017/7/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：结账详细信息实体类
 */
public class SettleAccountDataEntity implements Serializable {
    /**
     * 折让金额
     */
    private String allowanceMoney;
    /**
     * 订单来源
     */
    public String billResource;
    /**
     * 折扣金额
     */
    private String discountMoney;
    /**
     * 菜品消费
     */
    private String dishCharge;
    /**
     * 菜品消费详情
     */
    public DishChargeDetail dishChargeDetail;
    /**
     * 自动抹零金额
     */
    private String ignoreMoney;
    /**
     * 开台时间
     */
    public Long openTime;
    /**
     * 下单菜品列表
     */
    public List<CheckOrderListEntity> orderDishes;
    /**
     * 已支付金额
     */
    private String paidMoney;
    /**
     * 消费人数
     */
    public String personNum;
    /**
     * 服务费
     */
    public String serviceCharge;
    /**
     * 服务费详情
     */
    public ServiceChargeDetail serviceChargeDetail;
    /**
     * 消费总计
     */
    private String totalCharge;
    /**
     * 未支付金额
     */
    private String unpaidMoney;

    private class DishChargeDetail {
        public DishChargeDetail() {
        }

        /**
         * 菜品总价
         */
        public String dishCount;
        /**
         * 菜品赠送金额
         */
        public String dishPresent;

        @Override
        public String toString() {
            return "DishChargeDetail{" +
                    "dishCount='" + dishCount + '\'' +
                    ", dishPresent='" + dishPresent + '\'' +
                    '}';
        }
    }

    private class ServiceChargeDetail {
        /**
         * 桌台服务费
         */
        public String tableCharge;

        public ServiceChargeDetail() {
        }

        @Override
        public String toString() {
            return "ServiceChargeDetail{" +
                    "tableCharge='" + tableCharge + '\'' +
                    '}';
        }
    }

    public DishChargeDetail getDishChargeDetail() {
        return dishChargeDetail;
    }

    public void setDishChargeDetail(DishChargeDetail dishChargeDetail) {
        this.dishChargeDetail = dishChargeDetail;
    }

    public ServiceChargeDetail getServiceChargeDetail() {
        return serviceChargeDetail;
    }

    public void setServiceChargeDetail(ServiceChargeDetail serviceChargeDetail) {
        this.serviceChargeDetail = serviceChargeDetail;
    }

    public String getAllowanceMoney() {
        return StringUtil.getFormattedMoney(allowanceMoney);
    }

    public void setAllowanceMoney(String allowanceMoney) {
        this.allowanceMoney = allowanceMoney;
    }

    public String getDiscountMoney() {
        return StringUtil.getFormattedMoney(discountMoney);
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getDishCharge() {
        return StringUtil.getFormattedMoney(dishCharge);
    }

    public void setDishCharge(String dishCharge) {
        this.dishCharge = dishCharge;
    }

    public String getIgnoreMoney() {
        return StringUtil.getFormattedMoney(ignoreMoney);
    }

    public void setIgnoreMoney(String ignoreMoney) {
        this.ignoreMoney = ignoreMoney;
    }

    public String getPaidMoney() {
        return StringUtil.getFormattedMoney(paidMoney);
    }

    public void setPaidMoney(String paidMoney) {
        this.paidMoney = paidMoney;
    }

    public String getUnpaidMoney() {
        return StringUtil.getFormattedMoney(unpaidMoney);
    }

    public void setUnpaidMoney(String unpaidMoney) {
        this.unpaidMoney = unpaidMoney;
    }

    public String getTotalCharge() {
        return StringUtil.getFormattedMoney(totalCharge);
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

    @Override
    public String toString() {
        return "SettleAccountDataEntity{" +
                "allowanceMoney='" + allowanceMoney + '\'' +
                ", billResource='" + billResource + '\'' +
                ", discountMoney='" + discountMoney + '\'' +
                ", dishCharge='" + dishCharge + '\'' +
                ", dishChargeDetail=" + dishChargeDetail +
                ", ignoreMoney='" + ignoreMoney + '\'' +
                ", openTime='" + openTime + '\'' +
                ", orderDishes=" + orderDishes +
                ", paidMoney='" + paidMoney + '\'' +
                ", personNum='" + personNum + '\'' +
                ", serviceCharge='" + serviceCharge + '\'' +
                ", serviceChargeDetail=" + serviceChargeDetail +
                ", totalCharge='" + totalCharge + '\'' +
                ", unpaidMoney='" + unpaidMoney + '\'' +
                '}';
    }
}
