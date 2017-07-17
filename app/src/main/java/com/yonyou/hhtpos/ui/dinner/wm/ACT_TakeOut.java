package com.yonyou.hhtpos.ui.dinner.wm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;
import com.yonyou.hhtpos.dialog.DIA_Navigation;

import butterknife.Bind;

/**
 * 外卖
 * 作者：liushuofei on 2017/7/6 10:44
 */
public class ACT_TakeOut extends ACT_BaseMultiple implements View.OnClickListener{

    private FRA_TakeOutLeft mLeftContent;

    @Override
    protected void initView() {
        mLeftContent = new FRA_TakeOutLeft();
    }

    @Override
    protected float getLeftWeight() {
        return 0.69f;
    }

    @Override
    protected Fragment getLeftContent() {
        return mLeftContent;
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
