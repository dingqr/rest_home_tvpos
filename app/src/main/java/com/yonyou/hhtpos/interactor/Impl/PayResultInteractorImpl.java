package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.db.entity.UserEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IPayResultInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/8/9 14:20
 * 邮箱：lsf@yonyou.com
 */
public class PayResultInteractorImpl implements IPayResultInteractor {

    private BaseLoadedListener mBaseLoadedListener;

    public PayResultInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void requestPayResult(String shopId, String tableBillId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("shopId", shopId);
        hashMap.put("tableBillId", tableBillId);

        RequestManager.getInstance().requestGetByAsyn(API.GET_PAY_RESULT, hashMap, new ReqCallBack<Boolean>() {
            @Override
            public void onReqSuccess(Boolean result) {
                mBaseLoadedListener.onSuccess(0, result);
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
