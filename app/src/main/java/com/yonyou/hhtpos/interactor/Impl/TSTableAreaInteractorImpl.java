package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.MealAreaEntity;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;
import com.yonyou.hhtpos.bean.ts.TSTableBillIdEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITSOpenOrderInteractor;
import com.yonyou.hhtpos.interactor.ITSTableAreaInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：ybing on 2017/8/1 10:11
 * 邮箱：ybing@yonyou.com
 * 获取餐区列表
 */
public class TSTableAreaInteractorImpl implements ITSTableAreaInteractor {

    private BaseLoadedListener getTableListListener;

    public TSTableAreaInteractorImpl(BaseLoadedListener getTableListListener) {
        this.getTableListListener = getTableListListener;
    }

    @Override
    public void getTableList(String shopId) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("shopId", shopId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_AREA_LIST, hashMap, new ReqCallBack<List<MealAreaEntity>>() {

            @Override
            public void onReqSuccess(List<MealAreaEntity> result) {
                getTableListListener.onSuccess(0, result);
            }

            @Override
            public void onFailure(String result) {
                getTableListListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                getTableListListener.onBusinessError(errorBean);
            }
        });
    }
}
