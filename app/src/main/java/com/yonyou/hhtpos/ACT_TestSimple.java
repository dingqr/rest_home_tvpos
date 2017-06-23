package com.yonyou.hhtpos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.base.ACT_BaseSimple;
import com.yonyou.hhtpos.util.NavigationUtil;

import java.util.List;

/**
 * 测试单一布局
 * 作者：liushuofei on 2017/6/23 10:14
 */
public class ACT_TestSimple extends ACT_BaseSimple {

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

    @Override
    protected List<String> getNavigationGroupData() {
        return NavigationUtil.getDefaultGroupData();
    }

    @Override
    protected List<List<String>> getNavigationChildData() {
        return NavigationUtil.getDefaultChildData();
    }

    @Override
    protected Fragment getContentFragment() {
        return new FRA_TestLeft();
    }

    @Override
    protected void initView() {

    }
}
