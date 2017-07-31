package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITSClearTableInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by ybing on 2017/7/25.
 * 堂食清台接口
 */

public class TSClearTableInteractorImpl implements ITSClearTableInteractor {

    private BaseLoadedListener mBaseLoadedListener;

    public TSClearTableInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void clearTable(String shopId,String tableId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put("shopId",StringUtil.getString(shopId));
        hashMap.put("tableId",StringUtil.getString(tableId));
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_CLEAR_ORDER, hashMap, new ReqCallBack<String>() {
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
