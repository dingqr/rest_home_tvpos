package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IQueryBillInfoInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by zj on 2017/7/28.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取结账信息页数据
 */
public class QueryBillInfoInteractorImpl implements IQueryBillInfoInteractor {
    private BaseLoadedListener<SettleAccountDataEntity> mBillInfoListener;

    public QueryBillInfoInteractorImpl(BaseLoadedListener<SettleAccountDataEntity> listListener) {
        this.mBillInfoListener = listListener;
    }

    /**
     * 获取结账信息
     *
     * @param compId
     * @param shopId
     * @param tableBillId
     */
    @Override
    public void queryBillInfo(String compId, String shopId, String tableBillId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("compId", compId);
        params.put("shopId", shopId);
        params.put("tableBillId", tableBillId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_QUERY_BILL_INFO, params, new ReqCallBack<SettleAccountDataEntity>() {
            @Override
            public void onReqSuccess(SettleAccountDataEntity settleAccountDataEntity) {
                mBillInfoListener.onSuccess(1, settleAccountDataEntity);
            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/
                mBillInfoListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                mBillInfoListener.onBusinessError(error);
            }
        });
    }
}
