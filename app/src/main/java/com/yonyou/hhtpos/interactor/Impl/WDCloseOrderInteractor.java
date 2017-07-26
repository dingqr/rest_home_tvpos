package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IWDCloseOrderInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/25 19:03
 * 邮箱：lsf@yonyou.com
 */
public class WDCloseOrderInteractor implements IWDCloseOrderInteractor {

    private BaseLoadedListener mBaseLoadedListener;

    public WDCloseOrderInteractor(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void closeOrder(String tableBillId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("tableBillId", tableBillId);

        RequestManager.getInstance().requestPostByAsyn(API.URL_WD_CLOSE_ORDER, hashMap, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                mBaseLoadedListener.onSuccess(1,result);
            }

            @Override
            public void onFailure(String result) {
                mBaseLoadedListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                mBaseLoadedListener.onBusinessError(errorBean);
            }

        });
    }
}
