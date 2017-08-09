package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/8/9 14:13
 * 邮箱：lsf@yonyou.com
 */
public interface IPayResultPresenter {

    /**
     *  获取账单扫码支付结果
     * @param shopId 店铺id
     * @param tableBillId 账单id
     */
    void requestPayResult(String shopId, String tableBillId);
}
