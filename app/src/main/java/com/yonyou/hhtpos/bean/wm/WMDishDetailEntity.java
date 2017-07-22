package com.yonyou.hhtpos.bean.wm;

import com.yonyou.framework.library.common.utils.StringUtil;

/**
 * Created by zj on 2017/7/21.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖订单详情实体类
 */
public class WMDishDetailEntity {

    /**
     * 菜品id
     */
    public String dishId;
    /**
     * 菜品名称
     */
    public String dishName;

    public String dishStatus;
    /**
     * 订单时间
     */
    public Long orderTime;

    /**
     * 菜品类型
     */
    public String dishType;

    /**
     * id
     */
    public String id;
    /**
     * 菜品价格
     */
    private String dishPrice;
    /**
     * 订单状态：开单1，下单2，结账3，退款4
     */
    /**
     * 会员价格
     */
    private String memberPrice;
    /**
     * isPrint
     */
    public String isPrint;
    /**
     * dishAbnormalStatus
     */
    public String dishAbnormalStatus;
    /**
     * dishClassId
     */
    public String dishClassId;

    /**
     * shopId
     */
    public String shopId;
    /**
     * 备注
     */
    public String remark;
    /**
     * 数量
     */
    public String quantity;
    /**
     * 规格id
     */
    public String standardId;
    /**
     * 桌台账单id
     */
    public String tableBillId;

    public WMDishDetailEntity() {
    }

    public String getDishPrice() {
        return StringUtil.getFormattedMoney(dishPrice);
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getMemberPrice() {
        return StringUtil.getFormattedMoney(memberPrice);
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }
}
