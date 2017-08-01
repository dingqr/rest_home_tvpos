package com.yonyou.hhtpos.bean.check;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.adapter.CouponEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2017/7/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：结账详细信息实体类
 */
public class SettleAccountDataEntity implements Serializable {

    public SettleAccountDataEntity() {
    }

    /**
     * 折让金额
     */
    private String allowanceMoney;
    /**
     * 优惠券使用列表
     */
    public List<CouponEntity> couponUseds;
    /**
     * 支付记录
     */
    public List<PaidHistory> paidHistory;
    /**
     * 合计优惠
     */
    private String discountTotal;
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
     * 服务员名称
     */
    public String waiterName;
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
    public List<ServiceChargeDetail> serviceChargeDetail;
    /**
     * 消费总计
     */
    private String totalCharge;
    /**
     * 未支付金额
     */
    private String unpaidMoney;
//    /**
//     * 是否结算完成
//     */
//    public boolean settleComplete;
    /**
     * 账单支付状态：1-部分支付，2-支付完成，3-未支付，4-已退款
     */
    public String payStatus;

    public class PaidHistory implements Serializable {
        public PaidHistory() {
        }

        /**
         * 支付金额
         */
        private String payAmount;
        /**
         * 支付时间
         */
        private String payTime;
        /**
         * 支付方式
         */
        private String payType;

        public String getPayAmount() {
            return StringUtil.getFormattedMoney(payAmount);
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }
    }

    public class DishChargeDetail implements Serializable {
        public DishChargeDetail() {
        }

        /**
         * 菜品总价
         */
        private String dishCount;
        /**
         * 菜品赠送金额
         */
        private String dishPresent;

        public String getDishCount() {
            return dishCount;
        }

        public void setDishCount(String dishCount) {
            this.dishCount = dishCount;
        }

        public String getDishPresent() {
            return dishPresent;
        }

        public void setDishPresent(String dishPresent) {
            this.dishPresent = dishPresent;
        }

        @Override
        public String toString() {
            return "DishChargeDetail{" +
                    "dishCount='" + dishCount + '\'' +
                    ", dishPresent='" + dishPresent + '\'' +
                    '}';
        }
    }

    public class ServiceChargeDetail implements Serializable {
        /**
         * 桌台服务费
         */
        private String money;
        private String name;

        public ServiceChargeDetail() {
        }

        public String getMoney() {
            return StringUtil.getFormattedMoney(money);
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ServiceChargeDetail{" +
                    "money='" + money + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
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

    public String getDiscountTotal() {
        return StringUtil.getFormattedMoney(discountTotal);
    }

    public void setDiscountTotal(String discountTotal) {
        this.discountTotal = discountTotal;
    }

    public List<ServiceChargeDetail> getServiceChargeDetail() {
        return serviceChargeDetail;
    }

    public void setServiceChargeDetail(List<ServiceChargeDetail> serviceChargeDetail) {
        this.serviceChargeDetail = serviceChargeDetail;
    }


    public List<CheckOrderListEntity> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<CheckOrderListEntity> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public List<PaidHistory> getPaidHistory() {
        return paidHistory;
    }

    public void setPaidHistory(List<PaidHistory> paidHistory) {
        this.paidHistory = paidHistory;
    }

    public List<CouponEntity> getCouponUseds() {
        return couponUseds;
    }

    public void setCouponUseds(List<CouponEntity> couponUseds) {
        this.couponUseds = couponUseds;
    }

    public DishChargeDetail getDishChargeDetail() {
        return dishChargeDetail;
    }

    public void setDishChargeDetail(DishChargeDetail dishChargeDetail) {
        this.dishChargeDetail = dishChargeDetail;
    }

    @Override
    public String toString() {
        return "SettleAccountDataEntity{" +
                "allowanceMoney='" + allowanceMoney + '\'' +
                ", couponUseds=" + couponUseds +
                ", paidHistory=" + paidHistory +
                ", discountTotal='" + discountTotal + '\'' +
                ", billResource='" + billResource + '\'' +
                ", discountMoney='" + discountMoney + '\'' +
                ", dishCharge='" + dishCharge + '\'' +
                ", dishChargeDetail=" + dishChargeDetail +
                ", ignoreMoney='" + ignoreMoney + '\'' +
                ", openTime=" + openTime +
                ", orderDishes=" + orderDishes +
                ", paidMoney='" + paidMoney + '\'' +
                ", waiterName='" + waiterName + '\'' +
                ", personNum='" + personNum + '\'' +
                ", serviceCharge='" + serviceCharge + '\'' +
                ", serviceChargeDetail=" + serviceChargeDetail +
                ", totalCharge='" + totalCharge + '\'' +
                ", unpaidMoney='" + unpaidMoney + '\'' +
                ", payStatus='" + payStatus + '\'' +
                '}';
    }
}
