package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IActivateAppInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/6/28 11:04
 * 邮箱：lsf@yonyou.com
 */
public class ActivateAppInteractorImpl implements IActivateAppInteractor {

    private BaseLoadedListener codeListener;
    private BaseLoadedListener activateListener;

    public ActivateAppInteractorImpl(BaseLoadedListener codeListener, BaseLoadedListener activateListener) {
        this.codeListener = codeListener;
        this.activateListener = activateListener;
    }

    @Override
    public void requestActivateCode() {
        HashMap<String,String> hashMap = new HashMap<>();
        //hashMap.put("token", token);
        RequestManager.getInstance().requestGetByAsyn(API.BASE_SERVER_URL, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String bean) {
                codeListener.onSuccess(1,bean);
            }

            @Override
            public void onFailure(String result) {
                codeListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                codeListener.onBusinessError(errorBean);
            }
        });
    }

    @Override
    public void doActivate() {
        HashMap<String,String> hashMap = new HashMap<>();
        //hashMap.put("token", token);
        RequestManager.getInstance().requestGetByAsyn(API.BASE_SERVER_URL, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String bean) {
                codeListener.onSuccess(1,bean);
            }

            @Override
            public void onFailure(String result) {
                codeListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                codeListener.onBusinessError(errorBean);
            }
        });
    }

}
