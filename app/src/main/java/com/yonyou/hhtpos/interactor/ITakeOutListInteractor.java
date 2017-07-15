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
     */
    void requestTakeOutList(String companyId, String salesMode, String shopId);
}
