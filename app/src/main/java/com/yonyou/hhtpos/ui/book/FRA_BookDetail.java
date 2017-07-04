package com.yonyou.hhtpos.ui.book;

import android.view.View;
import android.widget.LinearLayout;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;

import butterknife.Bind;

/**
 * 预订详情
 * 作者：liushuofei on 2017/6/29 15:26
 */
public class FRA_BookDetail extends BaseFragment {

    @Bind(R.id.ll_content)
    LinearLayout mContentLayout;

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
        return mContentLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        //showCompanyInfo();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_book_detail;
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
