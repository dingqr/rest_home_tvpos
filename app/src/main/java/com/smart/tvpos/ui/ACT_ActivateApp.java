package com.smart.tvpos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.base.ACT_BaseFullScreen;

/**
 * 激活当前应用
 * 作者：liushuofei on 2017/6/26 18:01
 */
public class ACT_ActivateApp extends ACT_BaseFullScreen {

    @Override
    protected void initView() {

    }

    @Override
    protected Fragment getContentFragment() {
        return new FRA_ActivateApp();
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }
}
