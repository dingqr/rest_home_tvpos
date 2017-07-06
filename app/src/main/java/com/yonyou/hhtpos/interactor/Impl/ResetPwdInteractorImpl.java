package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.db.entity.UserEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.IResetPwdInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by ybing on 2017/7/5.
 */

public class ResetPwdInteractorImpl implements IResetPwdInteractor{

    private BaseLoadedListener mBaseLoadedListener;

    public ResetPwdInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }

    @Override
    public void resetPwd(String mobileNo, String msgCode, String newPassword) {

        HashMap<String,String> hashMap = new HashMap<String,String>();

        hashMap.put("mobileNo", StringUtil.getString(mobileNo));
        hashMap.put("msgCode",StringUtil.getString(msgCode));
        hashMap.put("newPassword",StringUtil.getString(newPassword));
        RequestManager.getInstance().requestPostByAsyn(API.URL_PASSPORT_LOGIN, hashMap, new ReqCallBack<UserEntity>() {
            @Override
            public void onReqSuccess(UserEntity result) {
                mBaseLoadedListener.onSuccess(1,result);
            }

            @Override
            public void onFailure(String result) {
                mBaseLoadedListener.onException(result);
            }

            @Override
            public void onReqFailed(ErrorBean errorBean) {
                mBaseLoadedListener.onBusinessError(errorBean);
            }

        });


    }
}
