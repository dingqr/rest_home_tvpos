package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/8/2 18:52
 * 邮箱：lsf@yonyou.com
 */
public interface IPrintPresenter {

    /**
     * 请求打印指令接口
     * @param printType 打印类型
     * @param shopId 店铺id
     * @param companyId 公司id
     * @param sourceId 打印指令id
     */
    void requestPrintOrder(String printType, String shopId, String companyId, String sourceId);
}
