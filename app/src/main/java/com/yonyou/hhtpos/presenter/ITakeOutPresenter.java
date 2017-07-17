package com.yonyou.hhtpos.presenter;

import com.yonyou.hhtpos.bean.WMOpenOrderEntity;

/**
 * 作者：liushuofei on 2017/7/15 17:37
 * 邮箱：lsf@yonyou.com
 */
public interface ITakeOutPresenter {
    /**
     * 开单
     * @param bean 开单实体类
     */
    void openOrder(WMOpenOrderEntity bean);
}
