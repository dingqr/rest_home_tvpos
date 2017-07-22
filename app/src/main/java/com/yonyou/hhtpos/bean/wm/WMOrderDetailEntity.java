package com.yonyou.hhtpos.bean.wm;

import com.yonyou.framework.library.common.utils.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zj on 2017/7/21.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单详细信息实体-
 */
public class WMOrderDetailEntity implements Serializable {

    /**
     * 地址
     */
    public String address;
    /**
     * 手机
     */
    public String phone;
    /**
     * 收餐人姓名
     */
    public String name;
    /**
     * 到达时间
     */
    public String arriveTime;
    /**
     * 账单金额
     */
    private String billMoney;
    /**
     * 优惠金额
     */
    private String reduceMoney;
    /**
     * 菜品明细列表
     */
    public List<WMDishDetailEntity> dishList;
    /**
     * 订单状态：开单1，下单2，结账3，退款4
     */
    public String orderState;
    /**
     * 订单时间
     */
    public String orderTime;
    /**
     * 是否立即送餐
     */
    public String sendNow;
    /**
     * 外卖公司id
     */
    public String takeOutCompanyId;

    public WMOrderDetailEntity() {
    }

    public String getBillMoney() {
        return StringUtil.getFormattedMoney(billMoney);
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public String getReduceMoney() {
        return StringUtil.getFormattedMoney(reduceMoney);
    }

    public void setReduceMoney(String reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    @Override
    public String toString() {
        return "WMOrderDetailEntity{" +
                "address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", arriveTime='" + arriveTime + '\'' +
                ", billMoney='" + billMoney + '\'' +
                ", reduceMoney='" + reduceMoney + '\'' +
                ", dishList=" + dishList +
                ", orderState='" + orderState + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", sendNow='" + sendNow + '\'' +
                ", takeOutCompanyId='" + takeOutCompanyId + '\'' +
                '}';
    }
}
