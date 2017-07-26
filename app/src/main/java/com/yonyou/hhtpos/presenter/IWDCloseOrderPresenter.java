package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/7/25 19:00
 * 邮箱：lsf@yonyou.com
 */
public interface IWDCloseOrderPresenter {

    /**
     * 取消订单接口
     * @param tableBillId 桌台账单Id
     */
    void closeOrder(String tableBillId);
}
