package com.yonyou.hhtpos.ui.reserve;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_TobeConfirmOrder;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;
import com.yonyou.hhtpos.bean.TableReserveEntity;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yonyou.hhtpos.util.FiltrationUtil.getLeftReserveOrderList;

/**
 * 作者：ybing on 2017/7/1
 * 邮箱：ybing@yonyou.com
 */
public class FRA_TobeConfirmOrderLeft extends BaseFragment {

    @Bind(R.id.rl_whole_content)
    RelativeLayout rlContent;
    @Bind(R.id.tobe_confirm_list_swipe_layout)
    SwipeRefreshLayout tobeSwipeList;
    @Bind(R.id.tobe_confirm_list_view)
    LuRecyclerView tobeLuList;

    private LuRecyclerViewAdapter mLuRecyclerViewAdapter;
    private ADA_TobeConfirmOrder mAdapter;
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
        ArrayList<ReserveOrderListEntity> data = getLeftReserveOrderList();
        mAdapter = new ADA_TobeConfirmOrder(mContext,data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tobeLuList.setLayoutManager(linearLayoutManager);

        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mAdapter);
        tobeLuList.setAdapter(mLuRecyclerViewAdapter);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_tobe_confirm_order_left;
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
