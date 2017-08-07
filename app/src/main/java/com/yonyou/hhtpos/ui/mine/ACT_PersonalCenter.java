package com.yonyou.hhtpos.ui.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;

import butterknife.Bind;

/**
 * 个人中心页面
 * 作者：ybing on 2017/8/4 16:41
 */
public class ACT_PersonalCenter extends ACT_BaseMultiple implements View.OnClickListener {
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private FRA_PersonalCenterLeft mPersonalCenterLeft;
    private FRA_PersonalCenterEmpty mPersonalCenterEmpty;
    private FRA_PassWork mFraPasswork;
    private FRA_ModifyPassword mFraModifyPassword;
    private FRA_DailySettleAccount mFraDailySettleAccount;

    @Override
    protected void initView() {
        tvTitle.setText(mContext.getString(R.string.personal_center));
        mPersonalCenterLeft= new FRA_PersonalCenterLeft();
        mPersonalCenterEmpty = new FRA_PersonalCenterEmpty();
        mFraPasswork = new FRA_PassWork();
        mFraModifyPassword = new FRA_ModifyPassword();
        mFraDailySettleAccount = new FRA_DailySettleAccount();
    }

    @Override
    protected float getLeftWeight() {
        return 0.55f;
    }

    @Override
    protected Fragment getLeftContent() {
        return mPersonalCenterLeft;
    }

    @Override
    protected Fragment getRightContent() {
        return mFraDailySettleAccount;
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
