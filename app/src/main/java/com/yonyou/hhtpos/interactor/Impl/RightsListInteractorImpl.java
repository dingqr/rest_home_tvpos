package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IRightsListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zj on 2017/8/9.
 * 邮箱：zjuan@yonyou.com
 * 描述：查询权限列表
 */
public class RightsListInteractorImpl implements IRightsListInteractor {
    private BaseLoadedListener<List<WaiterEntity>> mWaiterListener;

    public RightsListInteractorImpl(BaseLoadedListener<List<WaiterEntity>> listener) {
        this.mWaiterListener = listener;
    }


    @Override
    public void requestRightsList(String functionCode) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("functionCode", "001");
        RequestManager.getInstance().requestGetByAsyn(API.URL_GET_RIGHTS_LIST, params, new ReqCallBack<List<WaiterEntity>>() {
            @Override
            public void onReqSuccess(List<WaiterEntity> waiterEntity) {
                mWaiterListener.onSuccess(1, waiterEntity);
            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/
                mWaiterListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                mWaiterListener.onBusinessError(error);
            }
        });
    }
}
