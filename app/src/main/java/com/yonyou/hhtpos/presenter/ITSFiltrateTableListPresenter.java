package com.yonyou.hhtpos.presenter;

/**
 * Created by ybing on 2017/7/27.
 * 邮箱：ybing@yonyou.com
 * 描述：堂食-桌台筛选列表
 */

public interface ITSFiltrateTableListPresenter {
    /**
     *
     * @param diningAreaRelateId 餐区关联ID
     * @param shopId 门店id
     * @param tableOperation 桌台操作
     */
    void requestFiltrateTableList(String diningAreaRelateId,String shopId,String tableOperation);
}
