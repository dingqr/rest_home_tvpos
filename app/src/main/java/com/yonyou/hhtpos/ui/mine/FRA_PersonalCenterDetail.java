package com.yonyou.hhtpos.ui.mine;

/**
 * Created by ybing on 2017/8/5.
 * 邮箱：ybing@yonyou.com
 * 描述：个人中心右侧页面
 */

import android.view.View;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;

import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;

/**
 * Created by zj on 2017/7/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单明细-马诗雨
 */
public class FRA_PersonalCenterDetail extends BaseFragment   {


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

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_personal_center_right;
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

