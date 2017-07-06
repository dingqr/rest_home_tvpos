package com.yonyou.hhtpos.ui.dinner.wd;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_PackingList;
import com.yonyou.hhtpos.bean.PackingListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 外带列表
 * 作者：liushuofei on 2017/7/4 16:45
 */
public class FRA_PackingList extends BaseFragment {

    @Bind(R.id.ll_root)
    LinearLayout mRoot;
    @Bind(R.id.lv_packing)
    ListView mListView;

    /**传入数据 */
    public static final String TYPE = "type";
    private int type;

    private List<PackingListBean> dataList;
    private ADA_PackingList mAdapter;

    public static final FRA_PackingList newInstance(int type)
    {
        FRA_PackingList f = new FRA_PackingList();
        Bundle bdl = new Bundle(1);
        bdl.putInt(TYPE, type);
        f.setArguments(bdl);
        return f;
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
        return mListView;
    }

    @Override
    protected void initViewsAndEvents() {
        // 无数据页面
        // showEmpty(R.drawable.default_no_order, mContext.getString(R.string.order_no_data));

        mAdapter = new ADA_PackingList(mContext);
        mListView.setAdapter(mAdapter);

        // 假数据
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            PackingListBean bean = new PackingListBean();
            if (i == 0) {
                bean.setCheck(true);
            }
            dataList.add(bean);
        }
        mAdapter.update(dataList);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_packing_list;
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
