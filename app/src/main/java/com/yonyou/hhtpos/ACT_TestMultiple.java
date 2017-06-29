package com.yonyou.hhtpos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;
import com.yonyou.hhtpos.bean.NavigationEntity;
import com.yonyou.hhtpos.util.NavigationUtil;

/**
 * 测试多重布局
 * 作者：liushuofei on 2017/6/23 10:44
 */
public class ACT_TestMultiple extends ACT_BaseMultiple {

    @Override
    protected float getLeftWeight() {
        return 0.77f;
    }

    @Override
    protected Fragment getLeftContent() {
        return new FRA_TestLeft();
    }

    @Override
    protected Fragment getRightContent() {
        return new FRA_TestRight();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

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
