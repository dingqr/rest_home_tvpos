package com.yonyou.hhtpos.interactor.Impl;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.base.BaseLoadedListener;
import com.yonyou.hhtpos.db.entity.UserEntity;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interactor.ILoginInteractor;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;

/**
 * Created by ybing on 2017/7/5.
 */

public class LoginInteractorImpl implements ILoginInteractor{
    private BaseLoadedListener mBaseLoadedListener;

    public LoginInteractorImpl(BaseLoadedListener mBaseLoadedListener) {
        this.mBaseLoadedListener = mBaseLoadedListener;
    }
    @Override
    public void login(String companyId, String deviceType, String mobileNo, String password, String shopId) {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("companyId", StringUtil.getString(companyId));
        hashMap.put("deviceType",StringUtil.getString(deviceType));
        hashMap.put("mobileNo",StringUtil.getString(mobileNo));
        hashMap.put("password",StringUtil.getString(password));
        hashMap.put("shopId",StringUtil.getString(shopId));
//        hashMap.put("shopId","");

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
