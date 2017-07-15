package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ICanteenListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/14 17:28
 * 邮箱：lsf@yonyou.com
 */
public class CanteenListInteractorImpl implements ICanteenListInteractor {

    private BaseLoadedListener openOrderListener;

    public CanteenListInteractorImpl(BaseLoadedListener openOrderListener) {
        this.openOrderListener = openOrderListener;
    }

    @Override
    public void openOrder(String billRemark, String companyId, String memberId, String personNum, String shopId, String tableNo, String waiterId) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("billRemark", billRemark);
        hashMap.put("companyId", companyId);
        hashMap.put("memberId", memberId);
        hashMap.put("personNum", personNum);
        hashMap.put("shopId", shopId);
        hashMap.put("tableNo", tableNo);
        hashMap.put("waiterId", waiterId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_OPEN_ORDER, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String bean) {
                openOrderListener.onSuccess(0,bean);
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
