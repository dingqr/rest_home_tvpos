package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;
import com.yonyou.hhtpos.bean.ts.TSTableBillIdEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITSOpenOrderInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/19 10:11
 * 邮箱：lsf@yonyou.com
 */
public class TSOpenOrderInteractorImpl implements ITSOpenOrderInteractor {

    private BaseLoadedListener openOrderListener;

    public TSOpenOrderInteractorImpl(BaseLoadedListener openOrderListener) {
        this.openOrderListener = openOrderListener;
    }

    @Override
    public void openOrder(OpenOrderEntity bean) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("billRemark", bean.getBillRemark());
        hashMap.put("memberId", bean.getMemberId());
        hashMap.put("personNum", bean.getPersonNum());
        hashMap.put("shopId", bean.getShopId());
        hashMap.put("tableId", bean.getTableNo());
        hashMap.put("waiterId", bean.getWaiterId());
        hashMap.put("openTime", bean.getOpenTime());
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_OPEN_ORDER, hashMap, new ReqCallBack<TSTableBillIdEntity>() {

            @Override
            public void onReqSuccess(TSTableBillIdEntity result) {
                openOrderListener.onSuccess(0, result);
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
