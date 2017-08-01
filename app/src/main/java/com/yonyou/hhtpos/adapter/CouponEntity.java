package com.yonyou.hhtpos.adapter;

import com.yonyou.framework.library.common.utils.StringUtil;

/**
 * Created by zj on 2017/7/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：优惠券实体类
 */
public class CouponEntity {
    /**
     * 券编号
     */
    public String couponCode;
    /**
     * 折扣金额
     */
    private String couponMoney;
    /**
     * 券类型
     */
    public String couponType;

    public String getCouponMoney() {
        return StringUtil.getFormattedMoney(couponMoney);
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }
}
