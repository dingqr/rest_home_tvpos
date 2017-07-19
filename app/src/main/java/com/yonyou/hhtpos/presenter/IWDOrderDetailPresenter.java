package com.yonyou.hhtpos.presenter;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单详情
 */
public interface IWDOrderDetailPresenter {
    /**
     * 获取外带订单详情
     * @param tableBillId 桌台账单id
     */
    void requestOrderDetail(String tableBillId);
}
