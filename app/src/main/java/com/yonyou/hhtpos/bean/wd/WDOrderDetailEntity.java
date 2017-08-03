package com.yonyou.hhtpos.bean.wd;

import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.bean.PayTypeEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单详细信息实体-
 */
public class WDOrderDetailEntity implements Serializable {
    /**
     * 桌台账单Id
     */
    public String tableBillId;
    /**
     * 开单服务员
     */
    public String waiterName;

    /**
     * 就餐人数
     */
    public String personNum;

    /**
     * 折扣金额
     */
    private String discountMoney;

    /**
     * 实收金额
     */
    private String realReceiveAmount;
    /**
     * 待收金额
     */
    private String receiveAmount;

    /**
     * 会员电话
     */
    public String memberPhone;

    /**
     * 优惠金额
     */
    private String reduceMoney;

    /**
     * 抹零金额
     */
    public String ignoreMoney;
    /**
     * 开单时间
     */
    public Long openTime;


    /**
     * 账单编号
     */
    public String billNo;

    /**
     * 点菜明细
     */
    public ArrayList<WDDishDetaiListlEntity> dishListDetail;
    /**
     * 支付方式
     */
    public ArrayList<PayTypeEntity> payTypeList;

    /**
     * 支付状态:目前两种（Y=已结账，N=未结账）
     */
    public String payStatus;

    public WDOrderDetailEntity() {
    }

    public String getReduceMoney() {
        return StringUtil.getFormattedMoney(reduceMoney);
    }

    public void setReduceMoney(String reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public String getIgnoreMoney() {
        return StringUtil.getFormattedMoney(ignoreMoney);
    }

    public void setIgnoreMoney(String ignoreMoney) {
        this.ignoreMoney = ignoreMoney;
    }

    public String getDiscountMoney() {
        return StringUtil.getFormattedMoney(discountMoney);
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getRealReceiveAmount() {
        return StringUtil.getFormattedMoney(realReceiveAmount);
    }

    public void setRealReceiveAmount(String realReceiveAmount) {
        this.realReceiveAmount = realReceiveAmount;
    }

    public String getReceiveAmount() {
        return StringUtil.getFormattedMoney(receiveAmount);
    }

    public void setReceiveAmount(String receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    @Override
    public String toString() {
        return "WDOrderDetailEntity{" +
                "tableBillId='" + tableBillId + '\'' +
                ", waiterName='" + waiterName + '\'' +
                ", personNum='" + personNum + '\'' +
                ", discountMoney='" + discountMoney + '\'' +
                ", realReceiveAmount='" + realReceiveAmount + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", reduceMoney='" + reduceMoney + '\'' +
                ", ignoreMoney='" + ignoreMoney + '\'' +
                ", openTime=" + openTime +
                ", billNo='" + billNo + '\'' +
                ", dishListDetail=" + dishListDetail +
                ", payTypeList=" + payTypeList +
                ", payStatus='" + payStatus + '\'' +
                '}';
    }
}
