package com.yonyou.hhtpos;

import android.view.View;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.widgets.RightNavigationView;

import butterknife.Bind;

/**
 * 作者：liushuofei on 2017/6/23 10:20
 * 邮箱：lsf@yonyou.com
 */
public class FRA_TestRight extends BaseFragment {

    @Bind(R.id.v_navigation_right)
    RightNavigationView mRightNavigationView;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
//        mRightNavigationView.setData(NavigationUtil.getRightDefaultData());
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_test_right;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }
}
