package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;

/**
 * Created by zj on 2017/7/15.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取菜品-View层接口
 */
public interface IWDPrintOrderView extends BaseView {
    void requestPrintOrder(String result);
}
