package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.PackingListBean;
import com.yonyou.hhtpos.bean.WDOpenOrderEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IPackingLeftInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：liushuofei on 2017/7/18 11:00
 * 邮箱：lsf@yonyou.com
 */
public class PackingLeftInteractorImpl implements IPackingLeftInteractor {

    private BaseLoadedListener packingLeftListener;

    public PackingLeftInteractorImpl(BaseLoadedListener packingLeftListener) {
        this.packingLeftListener = packingLeftListener;
    }

    @Override
    public void openOrder(WDOpenOrderEntity bean) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("mobileNo", bean.getMobileNo());
        hashMap.put("salesMode", bean.getSalesMode());
        hashMap.put("shopId", bean.getShopId());
        hashMap.put("tableId", bean.getTableId());
        hashMap.put("waiterId", bean.getWaiterId());
        hashMap.put("waiterName", bean.getWaiterName());
        RequestManager.getInstance().requestPostByAsyn(API.URL_WD_OPEN_ORDER, hashMap, new ReqCallBack<List<PackingListBean>>() {

            @Override
            public void onReqSuccess(List<PackingListBean> dataList) {
                packingLeftListener.onSuccess(0, dataList);
            }

            @Override
            public void onFailure(String result) {
                packingLeftListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                packingLeftListener.onBusinessError(errorBean);
            }
        });
    }
}
