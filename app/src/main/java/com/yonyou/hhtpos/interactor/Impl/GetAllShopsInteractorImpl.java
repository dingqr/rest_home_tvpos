package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.StoreEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IGetAllShopsInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ybing on 2017/7/31.
 * 获取所有门店
 */

public class GetAllShopsInteractorImpl implements IGetAllShopsInteractor {

    private BaseLoadedListener mBaseLoadedListener;

    public GetAllShopsInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void getAllShops(String companyId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put("companyId",StringUtil.getString(companyId));
        RequestManager.getInstance().requestGetByAsyn(API.URL_GET_ALL_SHOPS, hashMap, new ReqCallBack<List<StoreEntity>>() {
            @Override
            public void onReqSuccess(List<StoreEntity> result) {
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
