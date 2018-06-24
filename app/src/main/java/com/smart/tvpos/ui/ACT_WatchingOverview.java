package com.smart.tvpos.ui;

import android.os.Bundle;
import android.view.View;

import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.R;
/**
 * Created by JoJo on 2018/6/22.
 * wechat：18510829974
 * description：监控概览页面
 */
public class ACT_WatchingOverview extends BaseActivity {

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_watching_overview;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
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
}
