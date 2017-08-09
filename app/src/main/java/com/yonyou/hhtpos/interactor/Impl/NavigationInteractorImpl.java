package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.NavigationNewEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.INavigationInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：liushuofei on 2017/7/11 17:31
 * 邮箱：lsf@yonyou.com
 */
public class NavigationInteractorImpl implements INavigationInteractor {

    private BaseLoadedListener mBaseLoadedListener;

    public NavigationInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void requestNavigationList(String functionCode) {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("functionCode", functionCode);
        RequestManager.getInstance().requestGetByAsyn(API.GET_NAVIGATION_LIST, hashMap, new ReqCallBack<List<NavigationNewEntity>>() {
            @Override
            public void onReqSuccess(List<NavigationNewEntity> result) {
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
