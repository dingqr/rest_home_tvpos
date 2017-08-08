package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.PayTypeEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IPayTypeListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：liushuofei on 2017/8/8 14:56
 * 邮箱：lsf@yonyou.com
 */
public class PayTypeListInteractorImpl implements IPayTypeListInteractor {

    private BaseLoadedListener mBaseLoadedListener;

    public PayTypeListInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void requestPayTypeList(String shopId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("shopId",shopId);
        RequestManager.getInstance().requestGetByAsyn(API.GET_PAY_TYPE_LIST, hashMap, new ReqCallBack<List<PayTypeEntity>>() {
            @Override
            public void onReqSuccess(List<PayTypeEntity> result) {
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
