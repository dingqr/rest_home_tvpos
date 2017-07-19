package com.yonyou.hhtpos.interactor;

import com.yonyou.hhtpos.bean.wd.OpenOrderEntity;

/**
 * 作者：liushuofei on 2017/7/18 10:59
 * 邮箱：lsf@yonyou.com
 */
public interface IWDOpenOrderInteractor {

    /**
     * 外带开单
     * @param bean 外带开单实体类
     */
    void openOrder(OpenOrderEntity bean);
}
