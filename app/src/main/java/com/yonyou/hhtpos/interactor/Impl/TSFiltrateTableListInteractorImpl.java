package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITSFiltrateTableListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ybing on 2017/7/20.
 * 邮箱：ybing@yonyou.com
 * 描述：堂食-桌台筛选列表
 */
public class TSFiltrateTableListInteractorImpl implements ITSFiltrateTableListInteractor {
    private BaseLoadedListener<List<CanteenTableEntity>> fliterTableListListener;

    public TSFiltrateTableListInteractorImpl(BaseLoadedListener<List<CanteenTableEntity>> listener) {
        this.fliterTableListListener = listener;
    }

    /**
     * 桌台列表
     *
     * @param diningAreaRelateId 餐区关联ID：与tableStatus不传默认查询全部
     * @param shopId             门店id
     * @param tableOperation     桌台操作 0=清台，1=转台，2=并台，3=拼台，4=菜品转台
     *
     *                           与diningAreaRelateId不传默认查询全部
     */
    @Override
    public void requestFiltrateTableList(String diningAreaRelateId, String shopId, String tableOperation) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("diningAreaRelateId",diningAreaRelateId);
        params.put("shopId", shopId);
        params.put("tableOperation",tableOperation);
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_CLICKABLE_TABLE, params, new ReqCallBack<List<CanteenTableEntity>>() {
            @Override
            public void onReqSuccess(List<CanteenTableEntity> canteenTableEntityList) {
                fliterTableListListener.onSuccess(1, canteenTableEntityList);
            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/
                fliterTableListListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                fliterTableListListener.onBusinessError(error);
            }
        });
    }
}
