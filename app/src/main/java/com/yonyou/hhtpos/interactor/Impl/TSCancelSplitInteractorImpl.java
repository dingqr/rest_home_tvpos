package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITSCancelSplitInteractor;
import com.yonyou.hhtpos.interactor.ITSSplitTableInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by ybing on 2017/7/29.
 * 堂食 取消拼台接口
 */

public class TSCancelSplitInteractorImpl implements ITSCancelSplitInteractor {

    private BaseLoadedListener splitOrderListener;

    public TSCancelSplitInteractorImpl(BaseLoadedListener splitOrderListener) {
        this.splitOrderListener = splitOrderListener;
    }

    @Override
    public void cancelSplit(String tableId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put("tableId",StringUtil.getString(tableId));
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_CANCEL_SPLIT_TABLE, hashMap, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                splitOrderListener.onSuccess(1,result);
            }

            @Override
            public void onFailure(String result) {
                splitOrderListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                splitOrderListener.onBusinessError(errorBean);
            }
        });
    }
}
