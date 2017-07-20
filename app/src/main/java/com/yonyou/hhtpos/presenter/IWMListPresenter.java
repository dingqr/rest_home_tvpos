package com.yonyou.hhtpos.presenter;

import com.yonyou.hhtpos.bean.wm.OrderListRequestEntity;

/**
 * 作者：liushuofei on 2017/7/14 18:31
 * 邮箱：lsf@yonyou.com
 */
public interface IWMListPresenter {

    /**
     * 请求外卖列表
     * @param bean 请求参数实体类
     * @param isRefresh 是否为刷新
     * @param isEmpty 是否为空页面
     */
    void requestTakeOutList(OrderListRequestEntity bean, boolean isRefresh, boolean isEmpty);
}
