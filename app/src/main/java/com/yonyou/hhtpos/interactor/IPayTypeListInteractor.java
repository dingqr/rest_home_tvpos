package com.yonyou.hhtpos.interactor;

/**
 * 作者：liushuofei on 2017/8/8 14:55
 * 邮箱：lsf@yonyou.com
 */
public interface IPayTypeListInteractor {

    /**
     * 请求支付方式列表
     * @param shopId
     */
    void requestPayTypeList(String shopId);
}
