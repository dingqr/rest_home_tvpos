package com.yonyou.hhtpos.ui.dinner.ts;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CanteenList;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.ui.dinner.wd.FRA_PackingList;
import com.yonyou.hhtpos.util.DP2PX;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * Created by zj on 2017/7/8.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食列表
 */
public class FRA_CanteenList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rv_canteen_list)
    LRecyclerView mRecyclerView;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwiperefreshLayout;

    //每一页请求多少条数据
    private static final int REQUEST_COUNT = 5;
    //当前页数
    private int mCurrentPage = 1;
    //列表显示的列数
    private int mColumnNum = 7;
    private ADA_CanteenList mAdapter;
    private LRecyclerViewAdapter mLuRecyclerViewAdapter;
    private ArrayList<CanteenTableEntity> datas = new ArrayList<>();

    /**传入数据 */
    public static final String TYPE = "type";
    private int type;

    public static final FRA_CanteenList newInstance(int type)
    {
        FRA_CanteenList f = new FRA_CanteenList();
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
        return mSwiperefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {

        //设置刷新时动画的颜色，可以设置4个
        if (mSwiperefreshLayout != null) {
            mSwiperefreshLayout.setProgressViewOffset(false, 0, DP2PX.dip2px(mContext, 30));
            mSwiperefreshLayout.setColorSchemeColors(
                    getResources().getColor(R.color.gplus_color_1),
                    getResources().getColor(R.color.gplus_color_2),
                    getResources().getColor(R.color.gplus_color_3),
                    getResources().getColor(R.color.gplus_color_4));
            mSwiperefreshLayout.setOnRefreshListener(this);
        }
        //去除LRecyclerView的默认的下拉刷新效果
        mRecyclerView.setPullRefreshEnabled(false);

        mAdapter = new ADA_CanteenList(mContext);
        //设置LayoutManager必须在设置setAdapter之前
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mColumnNum);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //setAdapter
        mLuRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);

        mRecyclerView.addItemDecoration(new SpaceItemDecoration());
        //loadmore
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                loadMore();
            }
        });
        //设置底部加载颜色-
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark, R.color.color_dcdcdc);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(mContext.getResources().getString(R.string.loading_note), mContext.getResources().getString(R.string.no_more_note), "");

        mRecyclerView.setHasFixedSize(true);
        setData();
    }

    private void setData() {
        for (int i = 0; i < 50; i++) {
            CanteenTableEntity canteenTableEntity = new CanteenTableEntity();
            canteenTableEntity.table_name = "item=" + i;
            datas.add(canteenTableEntity);
        }
        mAdapter.update(datas);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_canteen_list;
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
        if (mSwiperefreshLayout.isRefreshing()) {
            mSwiperefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 设置item之间的间距
     */
    public class SpaceItemDecoration extends LuRecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration() {
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            outRect.left = 10;
            outRect.right = 10;
            outRect.bottom = 10;
            //header占了一个位置，故从位置1开始显示实际的item-第一行不设置顶部间距(UI)
            if (itemPosition <= mColumnNum) {
                outRect.top = 0;
            } else {
                outRect.top = 10;
            }

        }
    }

}
