package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.WDOrderDetailEntity;
import com.yonyou.hhtpos.bean.wm.WMOrderDetailEntity;
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

    private BaseLoadedListener<WDOrderDetailEntity> wdOrderDetailListener;
    private BaseLoadedListener<WMOrderDetailEntity> wmOrderDetailListener;

    public OrderDetailInteractorImpl(BaseLoadedListener<WDOrderDetailEntity> wdOrderDetailListener) {
        this.wdOrderDetailListener = wdOrderDetailListener;
    }

    public OrderDetailInteractorImpl(BaseLoadedListener wmOrderDetailListener, boolean isWm) {
        this.wmOrderDetailListener = wmOrderDetailListener;
    }

    /**
     * 获取外带订单详情
     *
     * @param tableBillId
     */
    @Override
    public void requestWDOrderDetail(String tableBillId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tableBillId", tableBillId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_WD_ORDER_DETAIL, hashMap, new ReqCallBack<WDOrderDetailEntity>() {

            @Override
            public void onReqSuccess(WDOrderDetailEntity result) {
                wdOrderDetailListener.onSuccess(0, result);
            }

            @Override
            public void onFailure(String result) {
                wdOrderDetailListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                wdOrderDetailListener.onBusinessError(errorBean);
            }
        });
    }

    /**
     * 获取外卖订单详情
     *
     * @param tableBillId
     */
    @Override
    public void requestWMOrderDetail(String tableBillId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tableBillId", tableBillId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_WM_ORDER_DETAIL, hashMap, new ReqCallBack<WMOrderDetailEntity>() {

            @Override
            public void onReqSuccess(WMOrderDetailEntity result) {
                wmOrderDetailListener.onSuccess(0, result);
            }

            @Override
            public void onFailure(String result) {
                wmOrderDetailListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                wmOrderDetailListener.onBusinessError(errorBean);
            }
        });
    }
}
