package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/7/14 18:31
 * 邮箱：lsf@yonyou.com
 */
public interface IWMListPresenter {

    /**
     * 请求外卖列表
     * @param companyId 公司id
     * @param salesMode 就餐类型
     * @param shopId 门店id
     * @param isRefresh 是否为刷新
     * @param isEmpty 是否为空页面
     * @param pageNum 页数
     * @param pageSize 每页显示数量
     * @param dinnerStatus 订单状态
     */
    void requestTakeOutList(String companyId, String salesMode, String shopId, String pageNum, String pageSize, String dinnerStatus, boolean isRefresh, boolean isEmpty);
}
