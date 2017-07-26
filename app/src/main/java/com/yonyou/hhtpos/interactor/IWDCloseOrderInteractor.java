package com.yonyou.hhtpos.interactor;

/**
 * 作者：liushuofei on 2017/7/25 19:02
 * 邮箱：lsf@yonyou.com
 */
public interface IWDCloseOrderInteractor {

    /**
     * 桌台账单id
     * @param tableBillId
     */
    void closeOrder(String tableBillId);
}
