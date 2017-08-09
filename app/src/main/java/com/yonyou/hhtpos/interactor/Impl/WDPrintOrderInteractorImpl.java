package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IWDPrintOrderInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by zj on 2017/8/9.
 * 邮箱：zjuan@yonyou.com
 * 描述：查询所有菜品/菜类
 */
public class WDPrintOrderInteractorImpl implements IWDPrintOrderInteractor {
    private BaseLoadedListener<String> mPrintListener;

    public WDPrintOrderInteractorImpl(BaseLoadedListener<String> printListener) {
        this.mPrintListener = printListener;
    }

    @Override
    public void requestPrintOrder(String tableBillId, String shopId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tableBillId", tableBillId);
        params.put("shopId", shopId);
        RequestManager.getInstance().requestPostByAsyn(API.URL_TAKE_OUT_PRINT_ORDER, params, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                mPrintListener.onSuccess(1, result);
            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/
                mPrintListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                mPrintListener.onBusinessError(error);
            }
        });
    }
}
