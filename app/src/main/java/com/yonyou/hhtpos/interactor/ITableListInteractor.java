package com.yonyou.hhtpos.interactor;

/**
 * Created by zj on 2017/7/20.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-桌台列表
 */
public interface ITableListInteractor {
    /**
     *
     * @param diningAreaRelateId 餐区关联ID
     * @param shopId 门店id
     * @param tableStatus 桌台状态
     */
    void requestWaiterList(String diningAreaRelateId,String shopId,String tableStatus);
}
