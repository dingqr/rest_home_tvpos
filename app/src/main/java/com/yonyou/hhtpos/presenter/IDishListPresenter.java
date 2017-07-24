package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/7/17 19:28
 * 邮箱：lsf@yonyou.com
 */
public interface IDishListPresenter {

    /**
     * 请求已点菜品列表
     * @param billId 桌台账单id
     * @param showLoading 是否显示加载中
     */
    void requestDishList(String billId, boolean showLoading);

    /**
     * 请求下单接口
     * @param dishIds 未下单菜品
     */
    void requestPlaceOrder(String dishIds);
}
