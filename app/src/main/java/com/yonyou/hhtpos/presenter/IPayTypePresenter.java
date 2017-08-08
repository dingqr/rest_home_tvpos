package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/8/8 14:54
 * 邮箱：lsf@yonyou.com
 */
public interface IPayTypePresenter {

    /**
     * 请求支付方式列表
     * @param shopId
     */
    void requestPayTypeList(String shopId);
}
