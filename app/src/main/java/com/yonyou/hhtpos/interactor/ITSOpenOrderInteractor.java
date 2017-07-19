package com.yonyou.hhtpos.interactor;

import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;

/**
 * 作者：liushuofei on 2017/7/19 10:10
 * 邮箱：lsf@yonyou.com
 */
public interface ITSOpenOrderInteractor {
    /**
     * 堂食开单
     * @param bean 堂食开单实体类
     */
    void openOrder(OpenOrderEntity bean);
}
