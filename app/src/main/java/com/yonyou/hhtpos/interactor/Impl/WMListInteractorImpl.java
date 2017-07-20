package com.yonyou.hhtpos.interactor.Impl;

import android.text.TextUtils;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.wm.OrderListEntity;
import com.yonyou.hhtpos.bean.wm.OrderListRequestEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IWMListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：liushuofei on 2017/7/14 18:35
 * 邮箱：lsf@yonyou.com
 */
public class WMListInteractorImpl implements IWMListInteractor {

    private BaseLoadedListener takeOutListListener;

    public WMListInteractorImpl(BaseLoadedListener takeOutListListener) {
        this.takeOutListListener = takeOutListListener;
    }


    @Override
    public void requestTakeOutList(OrderListRequestEntity bean) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("salesMode", bean.getSalesMode());
        hashMap.put("shopId", bean.getShopId());
        hashMap.put("pageNum", bean.getPageNum());
        hashMap.put("pageSize", bean.getPageSize());

        // 全部不传递该字段
        if (!bean.getDinnerStatus().equals("0")){
            hashMap.put("dinnerStatus", bean.getDinnerStatus());
        }
        // 为空不传递该字段：市别
        if (!TextUtils.isEmpty(bean.getScheduleNameId())){
            hashMap.put("scheduleNameId", bean.getScheduleNameId());
        }
        // 为空不传递该字段：外卖公司
        if (!TextUtils.isEmpty(bean.getTakeOutCompanyId())){
            hashMap.put("takeOutCompanyId", bean.getTakeOutCompanyId());
        }

        RequestManager.getInstance().requestPostByAsyn(API.URL_TAKE_OUT_LIST, hashMap, new ReqCallBack<List<OrderListEntity>>() {

            @Override
            public void onReqSuccess(List<OrderListEntity> dataList) {
                takeOutListListener.onSuccess(0, dataList);
            }

            @Override
            public void onFailure(String result) {
                takeOutListListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                takeOutListListener.onBusinessError(errorBean);
            }
        });
    }
}
