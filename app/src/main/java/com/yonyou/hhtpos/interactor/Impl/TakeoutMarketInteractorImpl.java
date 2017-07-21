package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.TakeoutCompanyEntity;
import com.yonyou.hhtpos.bean.TakeoutMarketEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITakeoutMarketInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ybing on 2017/7/17.
 * 查询所有市别接口
 */

public class TakeoutMarketInteractorImpl implements ITakeoutMarketInteractor{
    private BaseLoadedListener mBaseLoadedListener;

    public TakeoutMarketInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void getAllTakeOutSchedule( String shopId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("shopId",StringUtil.getString(shopId));
        RequestManager.getInstance().requestPostByAsyn(API.URL_WM_SCHEDULE, hashMap, new ReqCallBack<List<TakeoutMarketEntity>>() {
            @Override
            public void onReqSuccess(List<TakeoutMarketEntity> result) {
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
