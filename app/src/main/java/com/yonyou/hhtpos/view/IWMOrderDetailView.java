package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.wm.WMOrderDetailEntity;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外卖订单详情-View层接口
 */
public interface IWMOrderDetailView extends BaseView {
    void requestWMOrderDetail(WMOrderDetailEntity orderDetailEntity);
}
