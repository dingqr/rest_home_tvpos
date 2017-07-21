package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.WDOrderDetailEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IOrderDetailInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带和外卖的订单详情
 */
public class OrderDetailInteractorImpl implements IOrderDetailInteractor {

    private BaseLoadedListener<WDOrderDetailEntity> orderDetailListener;

    public OrderDetailInteractorImpl(BaseLoadedListener<WDOrderDetailEntity> orderDetailListener) {
        this.orderDetailListener = orderDetailListener;
    }
    /**
     * 获取外带订单详情
     * @param tableBillId
     */
    @Override
    public void requestWDOrderDetail(String tableBillId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tableBillId", tableBillId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_WD_ORDER_DETAIL, hashMap, new ReqCallBack<WDOrderDetailEntity>() {

            @Override
            public void onReqSuccess(WDOrderDetailEntity result) {
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

    /**
     * 获取外卖订单详情
     * @param tableBillId
     */
    @Override
    public void requestWMOrderDetail(String tableBillId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tableBillId", tableBillId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_WM_ORDER_DETAIL, hashMap, new ReqCallBack<WDOrderDetailEntity>() {

            @Override
            public void onReqSuccess(WDOrderDetailEntity result) {
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
