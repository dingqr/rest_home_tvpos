package com.yonyou.hhtpos.interactor;

/**
 * 作者：liushuofei on 2017/7/14 18:34
 * 邮箱：lsf@yonyou.com
 */
public interface ITakeOutListInteractor {

    /**
     * 请求外卖列表
     * @param companyId 公司id
     * @param salesMode 就餐类型
     * @param shopId 门店id
     * @param pageNum 页数
     * @param pageSize 每页显示数量
     * @param dinnerStatus 订单状态
     */
    void requestTakeOutList(String companyId, String salesMode, String shopId, String pageNum, String pageSize, String dinnerStatus);
}
