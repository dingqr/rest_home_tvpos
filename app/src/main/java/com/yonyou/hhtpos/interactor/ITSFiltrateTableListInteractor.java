package com.yonyou.hhtpos.interactor;

/**
 * Created by ybing on 2017/7/20.
 * 邮箱：ybing@yonyou.com
 * 描述：堂食-桌台筛选列表
 */
public interface ITSFiltrateTableListInteractor {
    /**
     *
     * @param diningAreaRelateId 餐区关联ID
     * @param shopId 门店id
     * @param tableOperation 桌台状态
     */
    void requestFiltrateTableList(String diningAreaRelateId, String shopId, String tableOperation);
}
