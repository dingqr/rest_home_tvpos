package com.yonyou.hhtpos.presenter;

/**
 * 作者：liushuofei on 2017/7/17 19:28
 * 邮箱：lsf@yonyou.com
 */
public interface IDishListPresenter {

    /**
     * 请求已点菜品列表
     * @param billId 桌台账单id
     */
    void requestDishList(String billId);
}
