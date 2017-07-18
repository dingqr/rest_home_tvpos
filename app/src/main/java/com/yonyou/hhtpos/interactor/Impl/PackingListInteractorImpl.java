package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IPackingListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/17 16:39
 * 邮箱：lsf@yonyou.com
 */
public class PackingListInteractorImpl implements IPackingListInteractor{

    private BaseLoadedListener packingListListener;

    public PackingListInteractorImpl(BaseLoadedListener packingListListener) {
        this.packingListListener = packingListListener;
    }

    @Override
    public void requestPackingList(String billNo, String salesMode, String shopId, String pageNum, String pageSize) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("billNo", billNo);
        hashMap.put("salesMode", salesMode);
        hashMap.put("shopId", shopId);
        hashMap.put("pageNum", pageNum);
        hashMap.put("pageSize", pageSize);
        RequestManager.getInstance().requestPostByAsyn(API.URL_PACKING_LIST, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String dataList) {
                packingListListener.onSuccess(0, dataList);
            }

            @Override
            public void onFailure(String result) {
                packingListListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                packingListListener.onBusinessError(errorBean);
            }
        });
    }
}
