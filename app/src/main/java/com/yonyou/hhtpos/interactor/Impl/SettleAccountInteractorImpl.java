package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.bean.check.RequestPayEntity;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ISettleAccountInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by zj on 2017/8/1.
 * 邮箱：zjuan@yonyou.com
 * 描述：获取结账信息页数据
 */
public class SettleAccountInteractorImpl implements ISettleAccountInteractor {
    private BaseLoadedListener<SettleAccountDataEntity> mSettleAccountListener;

    public SettleAccountInteractorImpl(BaseLoadedListener<SettleAccountDataEntity> listListener) {
        this.mSettleAccountListener = listListener;
    }

    @Override
    public void settleAccount(String companyId, String memberId, String shopId, String tableBillId, RequestPayEntity requestPayEntity) {
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, HashMap<String, String>> paramsMap = new HashMap<String, HashMap<String, String>>();

        params.put("companyId", companyId);
        params.put("memberId", memberId);
        params.put("shopId", shopId);
        params.put("tableBillId", tableBillId);

        HashMap<String, String> map = new HashMap<>();
        map.put("payAmount", requestPayEntity.payAmount);
        map.put("payType", requestPayEntity.payType);
        paramsMap.put("payInfo", map);
        RequestManager.getInstance().requestPostByAsyn(API.URL_SETTLE_ACCOUNT, params, paramsMap, new ReqCallBack<SettleAccountDataEntity>() {
            @Override
            public void onReqSuccess(SettleAccountDataEntity settleAccountDataEntity) {
                mSettleAccountListener.onSuccess(1, settleAccountDataEntity);
            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/
                mSettleAccountListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                mSettleAccountListener.onBusinessError(error);
            }
        });
    }
}
