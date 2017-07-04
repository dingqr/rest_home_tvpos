package com.yonyou.hhtpos.ui.book;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;

/**
 * 预订总览
 * 作者：liushuofei on 2017/6/29 15:23
 */
public class ACT_BookPreview extends ACT_BaseMultiple {

    private Fragment currentFragment;
    private FRA_BookLeft mBookLeft;
    private FRA_BookSearch mBookSearch;

    @Override
    protected void initView() {
        mBookLeft = new FRA_BookLeft();
        mBookSearch = new FRA_BookSearch();

        currentFragment = mBookLeft;
    }

    @Override
    protected float getLeftWeight() {
        return 0.81f;
    }

    @Override
    protected Fragment getLeftContent() {
        return mBookLeft;
    }

    @Override
    protected Fragment getRightContent() {
        return new FRA_BookDetail();
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

    public void switchToSearch(){
        switchContent(currentFragment, mBookSearch);
    }

    public void switchToLeft(){
        switchContent(currentFragment, mBookLeft);
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
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from).add(R.id.fl_left, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}
