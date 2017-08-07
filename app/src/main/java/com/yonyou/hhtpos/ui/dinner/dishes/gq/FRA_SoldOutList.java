package com.yonyou.hhtpos.ui.dinner.dishes.gq;

import android.view.View;
import android.widget.ListView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_SoldOutList;

import butterknife.Bind;

/**
 * 估清列表fragment
 * 作者：liushuofei on 2017/8/7 11:08
 */
public class FRA_SoldOutList extends BaseFragment {

    @Bind(R.id.lv_sold_out)
    ListView mListView;

    private ADA_SoldOutList mAdapter;

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
        mAdapter = new ADA_SoldOutList(mContext);
        mListView.setAdapter(mAdapter);

        for (int i = 0; i < 10; i++){
            mAdapter.update("");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_sold_out_list;
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
