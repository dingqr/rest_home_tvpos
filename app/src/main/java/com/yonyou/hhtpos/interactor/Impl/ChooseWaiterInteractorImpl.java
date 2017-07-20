package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IChooseWaiterInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zj on 2017/7/20.
 * 邮箱：zjuan@yonyou.com
 * 描述：选择服务员
 */
public class ChooseWaiterInteractorImpl implements IChooseWaiterInteractor {
    private BaseLoadedListener<List<WaiterEntity>> mWaiterListener;

    public ChooseWaiterInteractorImpl(BaseLoadedListener<List<WaiterEntity>> listener) {
        this.mWaiterListener = listener;
    }

    /**
     * 查询服务员
     *
     * @param shopId
     */
    @Override
    public void requestWaiterList(String shopId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("shopId", shopId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_GET_ALL_WAITERS, params, new ReqCallBack<List<WaiterEntity>>() {
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
