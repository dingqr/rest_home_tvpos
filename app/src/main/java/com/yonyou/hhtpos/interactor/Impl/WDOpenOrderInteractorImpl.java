package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wd.OpenOrderEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IWDOpenOrderInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/18 11:00
 * 邮箱：lsf@yonyou.com
 */
public class WDOpenOrderInteractorImpl implements IWDOpenOrderInteractor {

    private BaseLoadedListener openOrderListener;

    public WDOpenOrderInteractorImpl(BaseLoadedListener openOrderListener) {
        this.openOrderListener = openOrderListener;
    }

    @Override
    public void openOrder(OpenOrderEntity bean) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("mobileNo", bean.getMobileNo());
        hashMap.put("personNum", bean.getPersonNum());
        hashMap.put("salesMode", bean.getSalesMode());
        hashMap.put("shopId", bean.getShopId());
        hashMap.put("tableId", bean.getTableId());
        hashMap.put("waiterId", bean.getWaiterId());
        hashMap.put("waiterName", bean.getWaiterName());
        RequestManager.getInstance().requestPostByAsyn(API.URL_WD_OPEN_ORDER, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String dataList) {
                openOrderListener.onSuccess(0, dataList);
            }

            @Override
            public void onFailure(String result) {
                openOrderListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                openOrderListener.onBusinessError(errorBean);
            }
        });
    }
}
