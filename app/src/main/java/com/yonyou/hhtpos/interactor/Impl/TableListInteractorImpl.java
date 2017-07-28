package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ITableListInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zj on 2017/7/20.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食-桌台列表
 */
public class TableListInteractorImpl implements ITableListInteractor {
    private BaseLoadedListener<List<CanteenTableEntity>> mWaiterListener;

    public TableListInteractorImpl(BaseLoadedListener<List<CanteenTableEntity>> listener) {
        this.mWaiterListener = listener;
    }

    /**
     * 桌台列表
     *
     * @param diningAreaRelateId 餐区关联ID：与tableStatus不传默认查询全部
     * @param shopId             门店id
     * @param tableStatus        桌台状态 ：0 空闲 ，1 占用（消费中），2 占用（部分支付），3 占用（锁定），4 占用（结清），5 预订，传（1,2，3,4）为查询占用
     *                           与diningAreaRelateId不传默认查询全部
     */
    @Override
    public void requestTableList(String diningAreaRelateId, String shopId, String tableStatus) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("diningAreaRelateId",diningAreaRelateId);
        params.put("shopId", shopId);
        params.put("tableStatus",tableStatus);
        RequestManager.getInstance().requestPostByAsyn(API.URL_TS_TABLE_LIST, params, new ReqCallBack<List<CanteenTableEntity>>() {
            @Override
            public void onReqSuccess(List<CanteenTableEntity> canteenTableEntityList) {
                mWaiterListener.onSuccess(1, canteenTableEntityList);
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
