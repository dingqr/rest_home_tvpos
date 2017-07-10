package com.yonyou.hhtpos.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.base.ACT_BaseFullScreen;

/**
 * 作者：ybing on 2017/6/27 10:01
 * 邮箱：ybing@yonyou.com
 */
public class ACT_ResetNewPwd extends ACT_BaseFullScreen{

    /**传入参数 */
    public static final String SMS_CODE = "sms.code";
    public static final String MOBILE_NO = "mobile.no";
    private String smsCode;
    private String mobileNo;

    private FRA_ResetNewPwd fraRestNewPwd;

    @Override
    protected void initView() {
        fraRestNewPwd = new FRA_ResetNewPwd();
        Bundle bundle = new Bundle();
        bundle.putString(SMS_CODE,smsCode);
        bundle.putString(MOBILE_NO,mobileNo);
        fraRestNewPwd.setArguments(bundle);
    }

    @Override
    protected Fragment getContentFragment() {
        return  fraRestNewPwd;
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        smsCode = extras.getString(SMS_CODE);
        mobileNo = extras.getString(MOBILE_NO);
//        smsCode = "123456";
//        mobileNo = "13671205992";
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
