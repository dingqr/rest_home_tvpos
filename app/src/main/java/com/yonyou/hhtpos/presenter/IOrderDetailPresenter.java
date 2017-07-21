package com.yonyou.hhtpos.presenter;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带和外卖的订单详情
 */
public interface IOrderDetailPresenter {
    /**
     * 获取外带订单详情
     * @param tableBillId 桌台账单id
     */
    void requestWDOrderDetail(String tableBillId);

    /**
     * 获取外卖订单详情
     * @param tableBillId
     */
    void requestWMOrderDetail(String tableBillId);
}
