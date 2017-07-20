package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.TakeoutCompanyEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITakeoutCompanyInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ybing on 2017/7/17.
 */

public class TakeoutCompanyInteractorImpl implements ITakeoutCompanyInteractor{
    private BaseLoadedListener mBaseLoadedListener;

    public TakeoutCompanyInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }
    @Override
    public void getAllTakeOutCompany(String shopId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();

//        hashMap.put("companyId", StringUtil.getString(companyId));
        hashMap.put("shopId",StringUtil.getString(shopId));
        RequestManager.getInstance().requestPostByAsyn(API.URL_POST_TAKEOUT_COMPANY, hashMap, new ReqCallBack <List<TakeoutCompanyEntity>>() {
            @Override
            public void onReqSuccess(List<TakeoutCompanyEntity> result) {
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
