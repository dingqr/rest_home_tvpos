package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.TakeOutListEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITakeOutListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：liushuofei on 2017/7/14 18:35
 * 邮箱：lsf@yonyou.com
 */
public class TakeOutListInteractorImpl implements ITakeOutListInteractor {

    private BaseLoadedListener takeOutListListener;

    public TakeOutListInteractorImpl(BaseLoadedListener takeOutListListener) {
        this.takeOutListListener = takeOutListListener;
    }


    @Override
    public void requestTakeOutList(String companyId, String salesMode, String shopId, String pageNum, String pageSize, String dinnerStatus) {
        HashMap<String,String> hashMap = new HashMap<>();
        //hashMap.put("companyId", companyId);
        hashMap.put("salesMode", salesMode);
        hashMap.put("shopId", shopId);
        hashMap.put("pageNum", pageNum);
        hashMap.put("pageSize", pageSize);

        // 全部不传递该字段
        if (!dinnerStatus.equals("0")){
            hashMap.put("dinnerStatus", dinnerStatus);
        }

        RequestManager.getInstance().requestPostByAsyn(API.URL_TAKE_OUT_LIST, hashMap, new ReqCallBack<List<TakeOutListEntity>>() {

            @Override
            public void onReqSuccess(List<TakeOutListEntity> dataList) {
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
