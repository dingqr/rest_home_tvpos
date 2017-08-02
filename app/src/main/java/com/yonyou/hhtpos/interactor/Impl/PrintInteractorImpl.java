package com.yonyou.hhtpos.interactor.Impl;

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
    public void requestPrintOrder() {
//        http://10.220.17.51:8064/PrintService/getPrintommand
//        ?printType=before1&shopId=C13352966C000000A60000000016E000&companyId=DIE49JkEU29JHD819HRh19hGDAY1&sourceId=22

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("printType", "before1");
        hashMap.put("shopId", "C13352966C000000A60000000016E000");
        hashMap.put("companyId", "DIE49JkEU29JHD819HRh19hGDAY1");
        hashMap.put("sourceId", "22");
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
