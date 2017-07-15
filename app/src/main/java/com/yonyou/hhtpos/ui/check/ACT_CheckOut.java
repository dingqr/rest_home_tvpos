package com.yonyou.hhtpos.ui.check;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;
import com.yonyou.hhtpos.ui.dinner.wm.FRA_TakeOutDetail;

import butterknife.Bind;

/**
 * 结账页面
 * 作者：liushuofei on 2017/7/15 10:14
 */
public class ACT_CheckOut extends ACT_BaseMultiple{

    @Bind(R.id.inc_title)
    RelativeLayout mTitleLay;

    @Override
    protected void initView() {
        mTitleLay.setVisibility(View.GONE);
    }

    @Override
    protected float getLeftWeight() {
        return 0.47f;
    }

    @Override
    protected Fragment getLeftContent() {
        return new FRA_CheckOutLeft();
    }

    @Override
    protected Fragment getRightContent() {
        return new FRA_TakeOutDetail();
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
