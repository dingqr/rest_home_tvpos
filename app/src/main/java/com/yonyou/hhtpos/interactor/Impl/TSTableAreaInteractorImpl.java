package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;
import com.yonyou.hhtpos.bean.ts.TSTableBillIdEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITSOpenOrderInteractor;
import com.yonyou.hhtpos.interactor.ITSTableAreaInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：ybing on 2017/7/19 10:11
 * 邮箱：ybing@yonyou.com
 */
public class TSTableAreaInteractorImpl implements ITSTableAreaInteractor {

    private BaseLoadedListener getTableListListener;

    public TSTableAreaInteractorImpl(BaseLoadedListener getTableListListener) {
        this.getTableListListener = getTableListListener;
    }

    @Override
    public void getTableList(String shopId,String diningAreaRelatedId,String tableStatus) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("shopId", shopId);
        hashMap.put("dining_area_relate_id",diningAreaRelatedId);
        hashMap.put("tableStatus",tableStatus);
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_OPEN_ORDER, hashMap, new ReqCallBack<TSTableBillIdEntity>() {

            @Override
            public void onReqSuccess(TSTableBillIdEntity result) {
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
