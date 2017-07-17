package com.yonyou.hhtpos.bean.wd;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单详细信息实体-
 */
public class OrderDetailEntity implements Serializable {
    /**
     * 待收金额
     */
    private String receiveAmount;
    /**
     * 账单编号
     */
    public String billNo;
    /**
     * 开单人姓名
     */
    public String creatorName;

    /**
     * 菜单
     */
    public ArrayList<DishDetaiListlEntity> dishListDetail;

    /**
     * 会员电话
     */
    public String memberPhone;

    /**
     * 开单时间
     */
    public String openTime;

    /**
     * 支付状态
     */
    public String payStatus;

    /**
     * 就餐人数
     */
    public String personName;

    /**
     * 优惠金额
     */
    private String reduceMoney;

    /**
     * 桌台账单Id
     */
    public String tableBillId;

    public OrderDetailEntity() {
    }

    public String getReduceMoney() {
        return StringUtil.getFormattedMoney(reduceMoney);
    }

    public void setReduceMoney(String reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public String getAmount() {
        return StringUtil.getFormattedMoney(receiveAmount);
    }

    public void setAmount(String amount) {
        this.receiveAmount = amount;
    }

    @Override
    public String toString() {
        return "OrderDetailEntity{" +
                "amount='" + receiveAmount + '\'' +
                ", billNo='" + billNo + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", dishListDetail='" + dishListDetail + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", openTime='" + openTime + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", personName='" + personName + '\'' +
                ", reduceMoney='" + reduceMoney + '\'' +
                ", tableBillId='" + tableBillId + '\'' +
                '}';
    }
}
