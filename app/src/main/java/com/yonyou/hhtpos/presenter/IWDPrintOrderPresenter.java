package com.yonyou.hhtpos.presenter;

/**
 * Created by zj on 2017/8/9.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带补打账单
 */
public interface IWDPrintOrderPresenter {
    void requestPrintOrder(String tableBillId, String shopId);
}
