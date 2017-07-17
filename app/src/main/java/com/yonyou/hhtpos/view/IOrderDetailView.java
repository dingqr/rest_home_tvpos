package com.yonyou.hhtpos.view;

import com.yonyou.framework.library.view.BaseView;
import com.yonyou.hhtpos.bean.wd.OrderDetailEntity;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单详情-View层接口
 */
public interface IOrderDetailView extends BaseView {
    void requestOrderDetail(OrderDetailEntity orderDetailEntity);
}
