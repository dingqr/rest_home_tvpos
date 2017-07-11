package com.yonyou.hhtpos.ui;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.yonyou.framework.library.adapter.rv.MultiItemTypeAdapter;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_DishTypeList;
import com.yonyou.hhtpos.bean.DishTypeEntity;
import com.yonyou.hhtpos.util.NavigationUtil;
import com.yonyou.hhtpos.widgets.RightNavigationView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by zj on 2017/7/11.
 * 邮箱：zjuan@yonyou.com
 * 描述：点菜页面-右侧导航及菜品列表
 */
public class FRA_OrderDishes extends BaseFragment {
    @Bind(R.id.layout_dish_root)
    LinearLayout layoutRoot;
    @Bind(R.id.rv_orderdish_list)
    LRecyclerView mRecyclerView;
    @Bind(R.id.view_navigation_right)
    RightNavigationView mRightNavigationView;

    //每一页请求多少条数据
    private static final int REQUEST_COUNT = 5;
    //当前页数
    private int mCurrentPage = 1;
    //列表显示的列数
    private int mColumnNum = 4;
    private ADA_DishTypeList mAdapter;
    private LRecyclerViewAdapter mLuRecyclerViewAdapter;
    private ArrayList<DishTypeEntity> datas = new ArrayList<>();

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
        return layoutRoot;
    }

    @Override
    protected void initViewsAndEvents() {

//        //设置刷新时动画的颜色，可以设置4个
//        if (mSwiperefreshLayout != null) {
//            mSwiperefreshLayout.setProgressViewOffset(false, 0, DP2PX.dip2px(mContext, 30));
//            mSwiperefreshLayout.setColorSchemeColors(
//                    getResources().getColor(R.color.gplus_color_1),
//                    getResources().getColor(R.color.gplus_color_2),
//                    getResources().getColor(R.color.gplus_color_3),
//                    getResources().getColor(R.color.gplus_color_4));
//            mSwiperefreshLayout.setOnRefreshListener(this);
//        }
        //去除LRecyclerView的默认的下拉刷新效果
        mRecyclerView.setPullRefreshEnabled(false);

        mAdapter = new ADA_DishTypeList(mContext);
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
        //去除loadmore
        mRecyclerView.setLoadMoreEnabled(false);

        setData();
        mRightNavigationView.setData(NavigationUtil.getRightDefaultData());

        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mAdapter.setSelectItem(position);
                mAdapter.notifyDataSetChanged();
                CommonUtils.makeEventToast(mContext, mAdapter.getDataList().get(position).id, false);
                mRightNavigationView.refreshCount(mAdapter.getDataList().get(position).id,true);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void setData() {
        for (int i = 0; i < 50; i++) {
            DishTypeEntity dishTypeEntity = new DishTypeEntity();
            dishTypeEntity.name = "item=" + i;
            Random rand = new Random();
            int uid = rand.nextInt(8);
            dishTypeEntity.id = StringUtil.getString(uid);
            datas.add(dishTypeEntity);
        }
        mAdapter.update(datas);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_order_dishes;
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

    /**
     * 设置item之间的间距
     */
    public class SpaceItemDecoration extends LuRecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration() {
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            outRect.left = 5;
            outRect.right = 5;
            outRect.bottom = 5;
            //header占了一个位置，故从位置1开始显示实际的item-第一行不设置顶部间距(UI)
            if (itemPosition <= mColumnNum) {
                outRect.top = 3;
            } else {
                outRect.top = 5;
            }

        }
    }
}
