package com.yonyou.hhtpos.ui.mine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.PackingFragmentAdapter;
import com.yonyou.hhtpos.global.DishConstants;
import com.yonyou.hhtpos.presenter.IWDOpenOrderPresenter;
import com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes;
import com.yonyou.hhtpos.ui.dinner.wd.FRA_PackingList;
import com.yonyou.hhtpos.widgets.PagerSlidingTabStrip;

import butterknife.Bind;

/**
 * 个人中心左侧页面
 * 作者：ybing on 2017/8/4 16:47
 */
public class FRA_PersonalCenterLeft extends BaseFragment implements View.OnClickListener{


    @Override
    public void onClick(View v) {

    }

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
        return R.layout.fra_personal_center_left;
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
