package com.yonyou.hhtpos.interactor.Impl;

import android.text.TextUtils;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IPrintInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/8/2 18:53
 * 邮箱：lsf@yonyou.com
 */
public class PrintInteractorImpl implements IPrintInteractor {

    private BaseLoadedListener printListener;

    public PrintInteractorImpl(BaseLoadedListener printListener) {
        this.printListener = printListener;
    }

    @Override
    public void requestPrintOrder(String printType, String shopId, String companyId, String sourceId) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("printType", printType);
        hashMap.put("shopId", shopId);

        if (!TextUtils.isEmpty(companyId)){
            hashMap.put("companyId", companyId);
        }

        hashMap.put("sourceId", sourceId);
        RequestManager.getInstance().requestGetByAsyn(API.GET_PRINT_ORDER, hashMap, new ReqCallBack<String[]>() {

            @Override
            public void onReqSuccess(String[] bytes) {
                printListener.onSuccess(0, bytes);
            }

            @Override
            public void onFailure(String result) {
                printListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                printListener.onBusinessError(errorBean);
            }
        });
    }
}
