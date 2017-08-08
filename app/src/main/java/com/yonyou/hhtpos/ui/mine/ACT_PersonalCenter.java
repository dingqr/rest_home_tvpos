package com.yonyou.hhtpos.ui.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;
import com.yonyou.hhtpos.util.Constants;

import butterknife.Bind;

/**
 * 个人中心页面
 * 作者：ybing on 2017/8/4 16:41
 */
public class ACT_PersonalCenter extends ACT_BaseMultiple implements View.OnClickListener,
        FRA_PersonalCenterLeft.FrgOptionCallBack {
    @Bind(R.id.tv_title)
    TextView tvTitle;

    private Fragment currentFragment;
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
        currentFragment = mPersonalCenterEmpty;
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
        return currentFragment;
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
    /**
     * 切换Fragment
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (currentFragment != to) {
            currentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // 先判断是否被add过
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(from).add(R.id.fl_right, to).commitAllowingStateLoss();
                // 隐藏当前的fragment，显示下一个
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void sendOption(int optionNum) {
        switch (optionNum){
            case (Constants.PASS_WORK):
                switchContent(currentFragment,mFraPasswork);
                break;
            case (Constants.MODIFY_PWD):
                switchContent(currentFragment,mFraModifyPassword);
                break;
            case (Constants.DAILY_SETTLE_ACCOUNT):
                switchContent(currentFragment,mFraDailySettleAccount);
                break;
            case (Constants.OPEN_CASH_BOX):
                break;
            default:
                break;
        }
    }
}
