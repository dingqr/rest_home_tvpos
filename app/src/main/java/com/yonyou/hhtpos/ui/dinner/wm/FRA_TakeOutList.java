package com.yonyou.hhtpos.ui.dinner.wm;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_TakeOutList;
import com.yonyou.hhtpos.bean.TakeOutListBean;
import com.yonyou.hhtpos.view.ITakeOutListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 外卖列表fragment
 * 作者：liushuofei on 2017/7/6 10:47
 */
public class FRA_TakeOutList extends BaseFragment implements ITakeOutListView{

    @Bind(R.id.lv_take_out)
    ListView mListView;

    /**传入数据 */
    public static final String TYPE = "type";
    private int type;

    private List<TakeOutListBean> dataList;
    private ADA_TakeOutList mAdapter;

    public static final FRA_TakeOutList newInstance(int type) {
        FRA_TakeOutList f = new FRA_TakeOutList();
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
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        // 无数据页面
        // showEmpty(R.drawable.default_no_order, mContext.getString(R.string.take_out_order_no_data));

        mAdapter = new ADA_TakeOutList(mContext);
        mListView.setAdapter(mAdapter);

        // 假数据
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TakeOutListBean bean = new TakeOutListBean();
            if (i == 0) {
                bean.setCheck(true);
            }
            dataList.add(bean);
        }
        mAdapter.update(dataList);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_take_out_list;
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

    @Override
    public void requestTakeOutList() {

    }
}
