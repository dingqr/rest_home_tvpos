package com.yonyou.hhtpos.ui.member;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;
import com.yonyou.hhtpos.ui.dinner.wm.FRA_TakeOutDetail;

/**
 * Created by zj on 2017/8/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：会员列表和明细页面
 */
public class ACT_Member extends ACT_BaseMultiple implements View.OnClickListener {

    private FRA_MemberList mLeftContent;
    private FRA_TakeOutDetail mRightContent;

    @Override
    protected void initView() {
        mLeftContent = new FRA_MemberList();
        mRightContent = new FRA_TakeOutDetail();
    }

    @Override
    protected float getLeftWeight() {
        return 0.66f;
    }

    @Override
    protected Fragment getLeftContent() {
        return mLeftContent;
    }

    @Override
    protected Fragment getRightContent() {
        return mRightContent;
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
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

}
