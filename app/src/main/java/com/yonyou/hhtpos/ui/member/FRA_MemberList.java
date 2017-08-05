package com.yonyou.hhtpos.ui.member;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.yonyou.framework.library.adapter.rv.MultiItemTypeAdapter;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_MemberList;
import com.yonyou.hhtpos.bean.MemberEntity;
import com.yonyou.hhtpos.util.DP2PX;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zj on 2017/8/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */
public class FRA_MemberList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.rl_member_list)
    LuRecyclerView mRecyclerView;


    //每一页请求多少条数据
    private static final int REQUEST_COUNT = 5;
    //当前页数
    private int mCurrentPage = 1;
    //列表显示的列数
    private int mColumnNum = 7;
    private ADA_MemberList mAdapter;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter;
    private ArrayList<MemberEntity> datas = new ArrayList<>();

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
        return mSwipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, DP2PX.dip2px(mContext, 30));
            mSwipeRefreshLayout.setColorSchemeColors(
                    getResources().getColor(R.color.gplus_color_1),
                    getResources().getColor(R.color.gplus_color_2),
                    getResources().getColor(R.color.gplus_color_3),
                    getResources().getColor(R.color.gplus_color_4));
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }

        mAdapter = new ADA_MemberList(mContext);
        //设置LayoutManager必须在设置setAdapter之前
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //setAdapter
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        //loadmore
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                loadMore();
            }
        });
        //设置底部加载颜色-
        mRecyclerView.setFooterViewColor(R.color.color_eb6247, R.color.color_999999, R.color.color_FFFFFF);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(mContext.getResources().getString(R.string.loading_note), mContext.getResources().getString(R.string.no_more_note), "");
        mRecyclerView.setHasFixedSize(true);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mAdapter.setSelectItem(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        for (int i = 0; i < 30; i++) {
            MemberEntity memberEntity = new MemberEntity();
            datas.add(memberEntity);
        }
        mAdapter.update(datas, true);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_member_list;
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
    public void onRefresh() {

    }
}
