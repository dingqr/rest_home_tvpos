package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.OrderDetailEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IWDOrderDetailInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单详情-View层接口
 */
public class WDOrderDetailInteractorImpl implements IWDOrderDetailInteractor {

    private BaseLoadedListener<OrderDetailEntity> orderDetailListener;

    public WDOrderDetailInteractorImpl(BaseLoadedListener<OrderDetailEntity> orderDetailListener) {
        this.orderDetailListener = orderDetailListener;
    }

    @Override
    public void requestOrderDetail(String tableBillId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tableBillId", tableBillId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_WD_ORDER_DETAIL, hashMap, new ReqCallBack<OrderDetailEntity>() {

            @Override
            public void onReqSuccess(OrderDetailEntity result) {
                orderDetailListener.onSuccess(0, result);
            }

            @Override
            public void onFailure(String result) {
                orderDetailListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                orderDetailListener.onBusinessError(errorBean);
            }
        });
    }
}
