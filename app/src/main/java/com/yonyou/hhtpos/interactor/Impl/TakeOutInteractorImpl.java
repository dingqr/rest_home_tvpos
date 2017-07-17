package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.WMOpenOrderEntity;
import com.yonyou.hhtpos.db.entity.UserEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITakeOutInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/15 17:54
 * 邮箱：lsf@yonyou.com
 */
public class TakeOutInteractorImpl implements ITakeOutInteractor{

    private BaseLoadedListener mBaseLoadedListener;

    public TakeOutInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void openOrder(WMOpenOrderEntity bean) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("address", StringUtil.getString(bean.getAddress()));
        hashMap.put("companyId",StringUtil.getString(bean.getCompanyId()));
        hashMap.put("name",StringUtil.getString(bean.getName()));
        hashMap.put("personNum",StringUtil.getString(bean.getPersonNum()));
        hashMap.put("phone",StringUtil.getString(bean.getPhone()));
        hashMap.put("reserveTime",StringUtil.getString(bean.getReserveTime()));
        hashMap.put("scheduleNameId",StringUtil.getString(bean.getScheduleNameId()));
        hashMap.put("sendNow",StringUtil.getString(bean.getSendNow()));
        hashMap.put("shopId",StringUtil.getString(bean.getShopId()));
        hashMap.put("takeOutCompanyId",StringUtil.getString(bean.getTakeOutCompanyId()));

        RequestManager.getInstance().requestPostByAsyn(API.URL_WM_OPEN_ORDER, hashMap, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
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
