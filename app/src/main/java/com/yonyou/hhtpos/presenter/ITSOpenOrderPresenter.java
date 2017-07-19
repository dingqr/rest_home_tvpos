package com.yonyou.hhtpos.presenter;

import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;

/**
 * 作者：liushuofei on 2017/7/19 09:46
 * 邮箱：lsf@yonyou.com
 */
public interface ITSOpenOrderPresenter {
    /**
     * 堂食开单
     * @param bean 堂食开单实体类
     */
    void openOrder(OpenOrderEntity bean);
}
