package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.check.DiscountEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IDiscountPlanInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zj on 2017/8/7.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取所有折扣方案
 */
public class DiscountPlanInteractorImpl implements IDiscountPlanInteractor {
    private BaseLoadedListener<List<DiscountEntity>> mDiscountListListener;

    public DiscountPlanInteractorImpl(BaseLoadedListener<List<DiscountEntity>> listener) {
        this.mDiscountListListener = listener;
    }

    @Override
    public void getAllDiscountPlan(String shopId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("shopId", shopId);
        RequestManager.getInstance().requestGetByAsyn(API.URL_GET_DISCOUNT_PLAN, params, new ReqCallBack<List<DiscountEntity>>() {
            @Override
            public void onReqSuccess(List<DiscountEntity> dataList) {
                mDiscountListListener.onSuccess(1, dataList);
            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/
                mDiscountListListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                mDiscountListListener.onBusinessError(error);
            }
        });
    }
}
