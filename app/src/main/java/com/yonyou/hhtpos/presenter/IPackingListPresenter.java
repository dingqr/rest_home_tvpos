package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/7/17 16:34
 * 邮箱：lsf@yonyou.com
 */
public interface IPackingListPresenter {

    /**
     * 请求外带列表
     * @param billNo 订单编号
     * @param salesMode 销售模式
     * @param shopId 门店Id
     * @param pageNum 页数
     * @param pageSize 每页显示数量
     * @param isRefresh 是否为刷新
     * @param isEmpty 是否为空页面
     */
    void requestPackingList(String billNo, String salesMode, String shopId, String pageNum, String pageSize, boolean isRefresh, boolean isEmpty);
}
