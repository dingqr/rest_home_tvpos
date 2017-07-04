package com.yonyou.hhtpos.ui.book;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_PreviewList;
import com.yonyou.hhtpos.bean.PreviewListEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 预定总览列表
 * 作者：liushuofei on 2017/7/1 16:30
 */
public class FRA_BookList extends BaseFragment {

    @Bind(R.id.lv_preview)
    ListView mListView;

    /**传入数据 */
    public static final String TYPE = "type";
    private int type;

    private List<PreviewListEntity> dataList;
    private ADA_PreviewList mAdapter;

    public static final FRA_BookList newInstance(int type)
    {
        FRA_BookList f = new FRA_BookList();
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
        // 类型
        type = getArguments().getInt(TYPE);

        // 无数据页面
        // showEmpty(R.drawable.default_no_book, mContext.getString(R.string.book_no_data));

        mAdapter = new ADA_PreviewList(mContext);
        mListView.setAdapter(mAdapter);

        // 假数据
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            PreviewListEntity bean = new PreviewListEntity();
            if (i == 0) {
                bean.setCheck(true);
            }
            dataList.add(bean);
        }
        mAdapter.update(dataList);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_book_list;
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
