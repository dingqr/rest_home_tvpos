package com.yonyou.hhtpos.ui.reserve;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;

import butterknife.Bind;


/**
 * Created by ybing on 2017/7/1.
 * 待确认订单
 *
 */
public class ACT_TobeConfirmOrder extends ACT_BaseMultiple {

    @Bind(R.id.tv_title)
    protected TextView title;

    @Override
    protected void initView() {
        title.setText(this.getResources().getString(R.string.tobe_confirm));
    }

    @Override
    protected float getLeftWeight() {
        return 0.81f;
    }

    @Override
    protected Fragment getLeftContent() {
        return new FRA_TobeConfirmOrderLeft();
    }

    @Override
    protected Fragment getRightContent() {
        return new FRA_TobeConfirmOrderRight();
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
