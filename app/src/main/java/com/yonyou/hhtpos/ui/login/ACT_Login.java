package com.yonyou.hhtpos.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.base.ACT_BaseFullScreen;
import com.yonyou.hhtpos.db.entity.UserEntity;
import com.yonyou.hhtpos.presenter.ILoginPresenter;
import com.yonyou.hhtpos.util.SpUtil;
import com.yonyou.hhtpos.view.ILoginView;

/**
 * 作者：ybing on 2017/6/27 10:01
 * 邮箱：ybing@yonyou.com
 */
public class ACT_Login extends ACT_BaseFullScreen {
    private String shopId;
    private String shopName;
    private FRA_Login fraLogin;
    @Override
    protected void initView() {
        fraLogin = new FRA_Login();
        Bundle bundle = new Bundle();
        bundle.putString(SpUtil.SHOP_ID,shopId);
        bundle.putString(SpUtil.SHOP_NAME,shopName);
        fraLogin.setArguments(bundle);
    }

    @Override
    protected Fragment getContentFragment() {
        return  fraLogin;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        shopId = extras.getString(SpUtil.SHOP_ID);
        shopName = extras.getString(SpUtil.SHOP_NAME);
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
