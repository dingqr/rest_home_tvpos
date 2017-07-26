package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;

/**
 * 作者：liushuofei on 2017/7/18 10:40
 * 邮箱：lsf@yonyou.com
 */
public interface IWDOpenOrderView extends BaseView{

    /**
     * 外带开单成功回调
     * @param tableBillId 账单id
     */
    void openOrder(String tableBillId);
}
