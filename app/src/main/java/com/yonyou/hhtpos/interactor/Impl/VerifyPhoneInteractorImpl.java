package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IVerifyPhoneInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * 作者：liushuofei on 2017/7/5 16:56
 * 邮箱：lsf@yonyou.com
 */
public class VerifyPhoneInteractorImpl implements IVerifyPhoneInteractor {

    private BaseLoadedListener codeListener;
    private BaseLoadedListener verifyListener;

    public VerifyPhoneInteractorImpl(BaseLoadedListener codeListener, BaseLoadedListener verifyListener) {
        this.codeListener = codeListener;
        this.verifyListener = verifyListener;
    }

    @Override
    public void sendSmsCode(String companyId, String shopId, String mobileNo) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("companyId", companyId);
        hashMap.put("mobileNo", mobileNo);
        hashMap.put("shopId", shopId);
        RequestManager.getInstance().requestPostByAsyn(API.GET_SMS_CODE, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String bean) {
                codeListener.onSuccess(0,bean);
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
    public void verifyPhone(String companyId, String shopId, String mobileNo, String msgCode) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("companyId", companyId);
        hashMap.put("mobileNo", mobileNo);
        hashMap.put("msgCode", msgCode);
        hashMap.put("shopId", shopId);
        RequestManager.getInstance().requestPostByAsyn(API.VERIFY_PHONE_NUMBER, hashMap, new ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String bean) {
                verifyListener.onSuccess(0,bean);
            }

            @Override
            public void onFailure(String result) {
                verifyListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                verifyListener.onBusinessError(errorBean);
            }
        });
    }

}
