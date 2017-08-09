package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;

/**
 * 作者：liushuofei on 2017/8/9 13:51
 * 邮箱：lsf@yonyou.com
 */
public interface IPayResultView extends BaseView {

    /**
     * 获取账单扫码支付结果
     */
    void requestPayResult(boolean isPaid);
}
