package com.yonyou.hhtpos.ui.dinner.dishes;

import android.view.View;
import android.widget.ListView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishesList;

import butterknife.Bind;

/**
 * 已点菜品列表
 * 作者：liushuofei on 2017/7/11 10:48
 */
public class FRA_DishesList extends BaseFragment {

    @Bind(R.id.lv_dishes)
    ListView mListView;

    private ADA_DishesList mAdapter;

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
        return mListView;
    }

    @Override
    protected void initViewsAndEvents() {
        // 无数据页面
        // showEmpty(R.drawable.default_no_dishes, mContext.getString(R.string.dishes_no_data));

        mAdapter = new ADA_DishesList(mContext);
        mListView.setAdapter(mAdapter);

        // 假数据
        for (int i = 0; i < 10; i++){
            mAdapter.update("");
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_dishes_list;
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
