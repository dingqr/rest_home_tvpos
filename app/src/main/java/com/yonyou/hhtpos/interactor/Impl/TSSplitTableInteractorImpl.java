package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITSSplitTableInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by ybing on 2017/7/25.
 * 堂食拼台接口
 */

public class TSSplitTableInteractorImpl implements ITSSplitTableInteractor{

    private BaseLoadedListener splitOrderListener;

    public TSSplitTableInteractorImpl(BaseLoadedListener splitOrderListener) {
        this.splitOrderListener = splitOrderListener;
    }

    @Override
    public void splitTable(String shopId, String tableId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put("shopId", StringUtil.getString(shopId));
        hashMap.put("tableId",StringUtil.getString(tableId));
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_SPLIT_ORDER, hashMap, new ReqCallBack<CanteenTableEntity>() {
            @Override
            public void onReqSuccess(CanteenTableEntity result) {
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
