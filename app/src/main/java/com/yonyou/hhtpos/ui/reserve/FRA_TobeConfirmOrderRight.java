package com.yonyou.hhtpos.ui.reserve;

import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;

import butterknife.Bind;

/**
 * 作者：ybing on 2017/7/1
 * 邮箱：ybing@yonyou.com
 */
public class FRA_TobeConfirmOrderRight extends BaseFragment {

    @Bind(R.id.rb_receive_order)
    RadioButton rbReceiveOrder;
    @Bind(R.id.rb_refuse_order)
    RadioButton rbRefuseOrder;
    @Bind(R.id.rg_receive_order)
    RadioGroup rgReceiveOrder;

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
        return R.layout.fra_tobe_confirm_order_right;
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
