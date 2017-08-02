package com.yonyou.hhtpos.bean.wm;

import com.yonyou.framework.library.common.utils.StringUtil;

/**
 * Created by zj on 2017/7/21.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖订单明细列表实体类
 */
public class WMDishDetailListEntity {
    /**
     * 用于记录在同一时间下单的菜品的总数量标记
     */
    public String totalCount;
    /**
     * 菜品id
     */
    public String dishId;
    /**
     * 菜品名称
     */
    public String dishName;
    /**
     * 菜品状态：菜品状态:赠菜3，退菜4
     */
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
    public String listShowRemark;
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

    public WMDishDetailListEntity() {
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
