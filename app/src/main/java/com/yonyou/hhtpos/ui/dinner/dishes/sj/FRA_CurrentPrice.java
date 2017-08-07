package com.yonyou.hhtpos.ui.dinner.dishes.sj;

import android.view.View;
import android.widget.GridView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CurrentPriceList;

import butterknife.Bind;

/**
 * 时价列表fragment
 * 作者：liushuofei on 2017/8/7 13:47
 */
public class FRA_CurrentPrice extends BaseFragment {

    @Bind(R.id.gv_current_price)
    GridView mGridView;

    private ADA_CurrentPriceList mAdapter;

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
        mAdapter = new ADA_CurrentPriceList(mContext);
        mGridView.setAdapter(mAdapter);

        for (int i = 0; i < 60; i++){
            mAdapter.update("");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_current_price;
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
