package com.yonyou.hhtpos.interactor;

/**
 * 作者：liushuofei on 2017/7/17 16:38
 * 邮箱：lsf@yonyou.com
 */
public interface IWDListInteractor {

    /**
     * 请求外带列表
     * @param billNo 订单编号
     * @param salesMode 销售模式
     * @param shopId 门店Id
     * @param pageNum 页数
     * @param pageSize 每页显示数量
     * @param payStatus 付款状态
     */
    void requestPackingList(String billNo, String salesMode, String shopId, String pageNum, String pageSize, String payStatus);
}
