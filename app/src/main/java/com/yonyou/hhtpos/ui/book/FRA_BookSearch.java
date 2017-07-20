package com.yonyou.hhtpos.ui.book;

import android.view.View;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 搜索页面
 * 作者：liushuofei on 2017/7/3 14:19
 */
public class FRA_BookSearch extends BaseFragment {

    @Bind(R.id.tv_filter)
    TextView tvSearch;

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
        return R.layout.fra_book_search;
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

    @OnClick(R.id.tv_filter)
    public void onClick() {
        ((ACT_BookPreview)getActivity()).switchToLeft();
    }
}
