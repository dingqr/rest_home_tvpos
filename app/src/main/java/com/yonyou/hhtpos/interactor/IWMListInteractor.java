package com.yonyou.hhtpos.interactor;

import com.yonyou.hhtpos.bean.wm.OrderListRequestEntity;

/**
 * 作者：liushuofei on 2017/7/14 18:34
 * 邮箱：lsf@yonyou.com
 */
public interface IWMListInteractor {

    /**
     * 请求外卖列表
     * @param bean 请求实体类
     */
    void requestTakeOutList(OrderListRequestEntity bean);
}
