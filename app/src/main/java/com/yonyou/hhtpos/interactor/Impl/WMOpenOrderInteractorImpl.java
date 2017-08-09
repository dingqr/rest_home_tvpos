package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wm.OpenOrderEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IWMOpenOrderInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/15 17:54
 * 邮箱：lsf@yonyou.com
 */
public class WMOpenOrderInteractorImpl implements IWMOpenOrderInteractor {

    private BaseLoadedListener mBaseLoadedListener;

    public WMOpenOrderInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void openOrder(OpenOrderEntity bean) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("address", StringUtil.getString(bean.getAddress()));
        hashMap.put("name",StringUtil.getString(bean.getName()));
        hashMap.put("personNum",StringUtil.getString(bean.getPersonNum()));
        hashMap.put("phone",StringUtil.getString(bean.getPhone()));
        hashMap.put("reserveTime",StringUtil.getString(bean.getReserveTime()));
        hashMap.put("sendNow",StringUtil.getString(bean.getSendNow()));
        hashMap.put("shopId",StringUtil.getString(bean.getShopId()));
        hashMap.put("takeOutCompanyId",StringUtil.getString(bean.getTakeOutCompanyId()));
        hashMap.put("takeOutNumber",StringUtil.getString(bean.getTakeOutNumber()));
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
