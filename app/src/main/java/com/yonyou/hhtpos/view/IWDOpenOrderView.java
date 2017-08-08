package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.wd.OpenOrderSuccessEntity;

/**
 * 作者：liushuofei on 2017/7/18 10:40
 * 邮箱：lsf@yonyou.com
 */
public interface IWDOpenOrderView extends BaseView{

    /**
     * 外带开单成功回调
     * @param bean 开单成功实体类
     */
    void openOrder(OpenOrderSuccessEntity bean);
}
