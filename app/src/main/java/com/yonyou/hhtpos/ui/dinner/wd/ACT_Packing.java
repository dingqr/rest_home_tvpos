package com.yonyou.hhtpos.ui.dinner.wd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;
import com.yonyou.hhtpos.bean.wd.OrderListEntity;
import com.yonyou.hhtpos.ui.book.FRA_BookSearch;

/**
 * 外带页面
 * 作者：liushuofei on 2017/7/4 16:41
 */
public class ACT_Packing extends ACT_BaseMultiple implements View.OnClickListener {

    private Fragment currentFragment;
    private FRA_BookSearch mSearchFragment;
    private FRA_PackingLeft mPackingLeft;
    private FRA_PackingDetail mPackingDetail;

    @Override
    protected void initView() {
        mSearchFragment = new FRA_BookSearch().newInstance(FRA_BookSearch.TYPE_WD);
        mPackingLeft= new FRA_PackingLeft();
        mPackingDetail = new FRA_PackingDetail();
        currentFragment = mPackingLeft;
    }

    @Override
    protected float getLeftWeight() {
        return 0.65f;
    }

    @Override
    protected Fragment getLeftContent() {
        return mPackingLeft;
    }

    @Override
    protected Fragment getRightContent() {
        return mPackingDetail;
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
        switchContent(currentFragment, mSearchFragment);
    }

    public void switchToLeft(){
        switchContent(currentFragment, mPackingLeft);
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

    public void requestPackingDetail(OrderListEntity orderListEntity) {
            mPackingDetail.requestPackingDetail(orderListEntity);
    }

}
