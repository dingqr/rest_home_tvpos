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
import com.yonyou.framework.library.adapter.rv.MultiItemTypeAdapter;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CanteenList;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;
import com.yonyou.hhtpos.dialog.DIA_OpenOrder;
import com.yonyou.hhtpos.dialog.DIA_ReserveOpenOrder;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.interfaces.OpenOrderCallback;
import com.yonyou.hhtpos.presenter.IChooseWaiterPresenter;
import com.yonyou.hhtpos.presenter.ITSOpenOrderPresenter;
import com.yonyou.hhtpos.presenter.ITableListPresenter;
import com.yonyou.hhtpos.presenter.Impl.ChooseWaiterPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.TSOpenOrderPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.TableListPresenterImpl;
import com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes;
import com.yonyou.hhtpos.util.DP2PX;
import com.yonyou.hhtpos.view.IChooseWaiterView;
import com.yonyou.hhtpos.view.ITSOpenOrderView;
import com.yonyou.hhtpos.view.ITableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


/**
 * Created by zj on 2017/7/8.
 * 邮箱：zjuan@yonyou.com
 * 描述：堂食列表
 */
public class FRA_CanteenTableList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        MultiItemTypeAdapter.OnItemClickListener, ITableListView, ITSOpenOrderView, OpenOrderCallback,
        IChooseWaiterView {

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

    //请求桌台列表接口
    private ITableListPresenter mTableListPresenter;
    //测试请求参数
    private String diningAreaRelateId = "";
    private String shopId = "hht";
    //桌台状态
    private String mTableState;

    //开单接口
    private ITSOpenOrderPresenter mTSOpenOrderPresenter;
    //查询所有服务员接口
    private IChooseWaiterPresenter mChooseWaiterPresenter;
    private List<WaiterEntity> mWaiterList = new ArrayList<>();
    //测试参数
    private String shopIdFake = "C13352966C000000A60000000016E000";
    /**
     * 传入数据
     */
    public static final String TYPE = "type";
    private int type;

    public static final FRA_CanteenTableList newInstance(int type) {
        FRA_CanteenTableList f = new FRA_CanteenTableList();
        Bundle bdl = new Bundle(1);
        bdl.putInt(TYPE, type);
        f.setArguments(bdl);
        return f;
    }

    @Override
    protected void onFirstUserVisible() {
        if (NetUtils.isNetworkConnected(mContext)) {
            mTableListPresenter.requestTableList(diningAreaRelateId, shopId, mTableState);
        } else {
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    protected void onUserVisible() {
        if (NetUtils.isNetworkConnected(mContext)) {
            mTableListPresenter.requestTableList(diningAreaRelateId, shopId, mTableState);
        } else {
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
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
        mTableListPresenter = new TableListPresenterImpl(mContext, this);
        mTSOpenOrderPresenter = new TSOpenOrderPresenterImpl(mContext, this);
        mChooseWaiterPresenter = new ChooseWaiterPresenterImpl(mContext, this);
        mChooseWaiterPresenter.requestWaiterList(shopIdFake);

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

        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setHasFixedSize(true);

        Bundle mArgument = getArguments();
        if (null != mArgument) {
            switchStatus(mArgument.getInt(TYPE, ACT_Canteen.RB_FREE));
        }
    }

    /**
     * 桌台状态0 空闲 ，1 占用（消费中），2 占用（部分支付），3 占用（锁定），4 占用（结清），5 预订，传（1,2，3,4）为查询占用，不传默认查询全部
     *
     * @param state 每个tab对应的值
     */
    private void switchStatus(int state) {
        switch (state) {
            //空闲
            case ACT_Canteen.RB_FREE:
                mTableState = "0";
                break;
            //结清
            case ACT_Canteen.RB_SETTLE:
                mTableState = "4";
                break;
            //预定
            case ACT_Canteen.RB_BOOK:
                mTableState = "5";
                break;
            //占用
            case ACT_Canteen.RB_OCCUPY:
                mTableState = "1,2,3,4";
                break;
            //锁定
            case ACT_Canteen.RB_LOCKED:
                mTableState = "3";
                break;
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_canteen_table_list;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    public void onRefresh() {
        if (mSwiperefreshLayout.isRefreshing()) {
            mSwiperefreshLayout.setRefreshing(false);
        }
        if (NetUtils.isNetworkConnected(mContext)) {
            mTableListPresenter.requestTableList(diningAreaRelateId, shopId, mTableState);
        } else {
            CommonUtils.makeEventToast(mContext, getString(R.string.network_error), false);
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        CanteenTableEntity canteenTableEntity = datas.get(position - 1);
        if (canteenTableEntity != null) {
            switch (canteenTableEntity.tableStatus) {
                //桌台空闲，弹出开单对话框
                case 0:
                    DIA_OpenOrder dia_openOrder = new DIA_OpenOrder(mContext);
                    dia_openOrder.setData(canteenTableEntity, mWaiterList);
                    dia_openOrder.setTsCallback(FRA_CanteenTableList.this);
                    dia_openOrder.getDialog().show();
                    break;
                //桌台预定 弹出预订单开单对话框
                case 5:
                    DIA_ReserveOpenOrder dia_reserveOpenOrder = new DIA_ReserveOpenOrder(mContext);
                    dia_reserveOpenOrder.setData(canteenTableEntity, mWaiterList);
                    dia_reserveOpenOrder.setTsCallback(FRA_CanteenTableList.this);
                    dia_reserveOpenOrder.getDialog().show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void openOrder(String tableBillId) {
        //todo 获取tableBillId 传到点菜页面
        if (tableBillId != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ACT_OrderDishes.TABLE_BILL_ID, tableBillId);
            readyGo(ACT_OrderDishes.class, bundle);
        }
    }

    @Override
    public void sendTsEntity(OpenOrderEntity tsOpenOrderEntity) {
        if (tsOpenOrderEntity != null) {
            switch (tsOpenOrderEntity.getTableStatus()) {
                //桌台空闲
                case 0:
                    tsOpenOrderEntity.setShopId(shopIdFake);
                    mTSOpenOrderPresenter.openOrder(tsOpenOrderEntity);
                    break;
                //桌台预定
                case 5:
                    tsOpenOrderEntity.setShopId(shopIdFake);
                    mTSOpenOrderPresenter.openOrder(tsOpenOrderEntity);
                    break;
            }
        }

    }

    @Override
    public void requestWaiterList(List<WaiterEntity> waiterList) {
        if (waiterList != null && waiterList.size() > 0) {
            this.mWaiterList = waiterList;
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

    @Override
    public void requestTableList(List<CanteenTableEntity> tableList) {
        if (tableList != null && tableList.size() > 0) {
            this.datas = (ArrayList<CanteenTableEntity>) tableList;
            if (mSwiperefreshLayout.isRefreshing()) {
                mSwiperefreshLayout.setRefreshing(false);
            }
            mAdapter.update(tableList, true);
        } else {
            showEmptyHyperLink(getActivity(), API.URL_OPERATION_PALTFORM, "");
        }
    }

    /**
     * 更新桌台列表
     *
     * @param tableList
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onUpdateTableList(List<CanteenTableEntity> tableList) {
        if (tableList != null && tableList.size() > 0) {
            this.datas = (ArrayList<CanteenTableEntity>) tableList;
            mAdapter.update(tableList, true);
            Elog.e("tableList.size==", tableList.size());
        }else{
            Elog.e("tableList.size==", "00000000");
        }
    }
}
