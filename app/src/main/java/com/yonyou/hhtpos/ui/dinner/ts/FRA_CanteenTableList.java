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
import com.yonyou.hhtpos.bean.MealAreaEntity;
import com.yonyou.hhtpos.bean.WaiterEntity;
import com.yonyou.hhtpos.bean.ts.OpenOrderEntity;
import com.yonyou.hhtpos.bean.ts.TSTableBillIdEntity;
import com.yonyou.hhtpos.dialog.DIA_DoubleConfirm;
import com.yonyou.hhtpos.dialog.DIA_OpenOrder;
import com.yonyou.hhtpos.dialog.DIA_ReserveOpenOrder;
import com.yonyou.hhtpos.dialog.DIA_TurnChooseTable;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.global.DishConstants;
import com.yonyou.hhtpos.global.ReceiveConstants;
import com.yonyou.hhtpos.interfaces.OpenOrderCallback;
import com.yonyou.hhtpos.presenter.IChooseWaiterPresenter;
import com.yonyou.hhtpos.presenter.ITSClearTablePresenter;
import com.yonyou.hhtpos.presenter.ITSOpenOrderPresenter;
import com.yonyou.hhtpos.presenter.ITableListPresenter;
import com.yonyou.hhtpos.presenter.Impl.ChooseWaiterPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.TSClearTablePresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.TSOpenOrderPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.TableListPresenterImpl;
import com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.util.DP2PX;
import com.yonyou.hhtpos.view.IChooseWaiterView;
import com.yonyou.hhtpos.view.ITSClearTableView;
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
        IChooseWaiterView, ITSClearTableView {

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
    private String shopId = Constants.SHOP_ID;
    //桌台状态
    private String mTableState;

    //开单接口
    private ITSOpenOrderPresenter mTSOpenOrderPresenter;
    //清台接口
    private ITSClearTablePresenter mTSClearTablePresenter;
    //查询所有服务员接口
    private IChooseWaiterPresenter mChooseWaiterPresenter;
    private List<WaiterEntity> mWaiterList = new ArrayList<>();
    //测试参数
//    private String shopIdFake = "C13352966C000000A60000000016E000";
    /**
     * 传入数据
     */
    public static final String TYPE = "type";
    private int type;
    private String tableOption = "-1";

    //转台标记
    private boolean turnFlag = false;
    private List<MealAreaEntity> mMealAreas = new ArrayList<>();
    private DIA_TurnChooseTable mDiaTurnChooseTable;

    public static final FRA_CanteenTableList newInstance(int type) {
        FRA_CanteenTableList f = new FRA_CanteenTableList();
        Bundle bdl = new Bundle(1);
        bdl.putInt(TYPE, type);
        f.setArguments(bdl);
        return f;
    }

    @Override
    protected void onFirstUserVisible() {
        mChooseWaiterPresenter.requestWaiterList(Constants.SHOP_ID);
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
//        mChooseWaiterPresenter.requestWaiterList(Constants.SHOP_ID);
        mTSClearTablePresenter = new TSClearTablePresenterImpl(mContext, this);

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
            switchStatus(mArgument.getInt(TYPE, ACT_Canteen.RB_TOTAL));
        }

        //转台弹窗
        mDiaTurnChooseTable = new DIA_TurnChooseTable(mContext);
        //餐区筛选
        mDiaTurnChooseTable.setOnChooseMealAreaListener(new DIA_TurnChooseTable.OnChooseMealAreaListener() {
            @Override
            public void onMealAreaResult(MealAreaEntity areaEntity) {
                updateTurnList(areaEntity);
            }
        });
    }

    /**
     * 桌台状态 空为全部 0 空闲 ，1 占用（消费中），2 占用（部分支付），8 占用（锁定），9占用（结清），3 预订，传（6,7,8,9,10）为查询占用，不传默认查询全部
     *
     * @param state 每个tab对应的值
     */
    private void switchStatus(int state) {
        switch (state) {
            //全部
            case ACT_Canteen.RB_TOTAL:
                mTableState = null;
                break;
            //空闲
            case ACT_Canteen.RB_FREE:
                mTableState = "0";
                break;
            //结清
            case ACT_Canteen.RB_SETTLE:
                mTableState = "9";
                break;
            //预定
            case ACT_Canteen.RB_BOOK:
                mTableState = "3";
                break;
            //占用
            case ACT_Canteen.RB_OCCUPY:
                mTableState = "6,7,8,9,10";
                break;
            //锁定
            case ACT_Canteen.RB_LOCKED:
                mTableState = "8";
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
        final CanteenTableEntity canteenTableEntity = datas.get(position - 1);
        if (canteenTableEntity != null) {
            switch (canteenTableEntity.tableStatus) {
                //桌台空闲，弹出开单对话框
                case 0:
                    DIA_OpenOrder dia_openOrder = new DIA_OpenOrder(mContext);
                    dia_openOrder.setData(canteenTableEntity, mWaiterList);
                    dia_openOrder.setTsCallback(FRA_CanteenTableList.this);
                    dia_openOrder.getDialog().show();
                    break;
                //桌台占用，订单服务中
                case 6:
                case 7:
                case 8:
                case 10:
                    //可以被拼台的桌台列表
                    if (tableOption.equals("3")) {
                        //点击桌台 开拼桌的单子
                        DIA_OpenOrder dia_openOrderSplit = new DIA_OpenOrder(mContext);
                        canteenTableEntity.setTableOption(3);
                        dia_openOrderSplit.setData(canteenTableEntity, mWaiterList);
                        dia_openOrderSplit.setTsCallback(FRA_CanteenTableList.this);
                        dia_openOrderSplit.getDialog().show();
                        turnFlag = false;
                    }
                    //可以被转台的桌台列表
                    else if (tableOption.equals("1")) {
                        //点击桌台 筛选可以被转入的桌台
                        canteenTableEntity.setTableOption(1);
                        mTableListPresenter.requestTableList(diningAreaRelateId, shopId, "0,6,10");
                        turnFlag = true;

                    }
                    //可以被清台的桌台列表
                    else if (tableOption.equals("0")){
                        turnFlag = false;
                        //桌台占用，订单清台中
                        DIA_DoubleConfirm diaClearTable = new DIA_DoubleConfirm(mContext, mContext.getString(R.string.clear_table),
                                new DIA_DoubleConfirm.OnSelectedListener() {
                                    @Override
                                    public void confirm() {
                                        mTSClearTablePresenter.clearTable(Constants.SHOP_ID, canteenTableEntity.tableID);
                                    }
                                });
                        diaClearTable.getDialog().show();
                    }
                    else {
                        turnFlag = false;
                        Bundle bundle = new Bundle();
                        bundle.putString(ACT_OrderDishes.TABLE_BILL_ID, canteenTableEntity.tableBillId);
                        bundle.putInt(ACT_OrderDishes.FROM_WHERE, DishConstants.TYPE_TS);
                        bundle.putString(ACT_OrderDishes.TITLE_TEXT, canteenTableEntity.tableName);
                        readyGo(ACT_OrderDishes.class, bundle);
                    }
                    break;

                //桌台占用，筛选已结清账单的桌台
                case 9:
                    if (tableOption.equals("0")) {
                        //桌台占用，订单清台中
                        DIA_DoubleConfirm diaClearTable = new DIA_DoubleConfirm(mContext, mContext.getString(R.string.clear_table),
                                new DIA_DoubleConfirm.OnSelectedListener() {
                                    @Override
                                    public void confirm() {
                                        mTSClearTablePresenter.clearTable(Constants.SHOP_ID, canteenTableEntity.tableID);
                                    }
                                });
                        diaClearTable.getDialog().show();
                    } else if (tableOption.equals("3")) {
                        //点击桌台 开拼桌的单子
                        DIA_OpenOrder dia_openOrderSplit = new DIA_OpenOrder(mContext);
                        canteenTableEntity.setTableOption(3);
                        dia_openOrderSplit.setData(canteenTableEntity, mWaiterList);
                        dia_openOrderSplit.setTsCallback(FRA_CanteenTableList.this);
                        dia_openOrderSplit.getDialog().show();
                    }
                    break;
                //桌台预定 弹出预订单开单对话框
                case 3:
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
    public void openOrder(TSTableBillIdEntity bean) {
        if (bean != null) {
            Bundle bundle = new Bundle();
            bundle.putString(ACT_OrderDishes.TABLE_BILL_ID, bean.getTableBillId());
            bundle.putInt(ACT_OrderDishes.FROM_WHERE, DishConstants.TYPE_TS);
            readyGo(ACT_OrderDishes.class, bundle);
        }
    }

    @Override
    public void sendTsEntity(OpenOrderEntity tsOpenOrderEntity) {
        if (tsOpenOrderEntity != null) {
            switch (tsOpenOrderEntity.getTableStatus()) {
                //桌台空闲
                case 0:
                    tsOpenOrderEntity.setShopId(Constants.SHOP_ID);
                    mTSOpenOrderPresenter.openOrder(tsOpenOrderEntity);
                    break;
                //桌台预定
                case 5:
                    tsOpenOrderEntity.setShopId(Constants.SHOP_ID);
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

    @Override
    public void clearTable(String result) {
        onRefresh();
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
        // restore view helper
        restoreViewHelper();
        // reset adapter
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);

        if (tableList != null && tableList.size() > 0) {
            if (turnFlag) {
                //桌台列表是tablelist
                //餐区列表是ACT_Canteen里的mealAreas
                mMealAreas = ((ACT_Canteen) getActivity()).getMealAreaList();
                if (mMealAreas != null && mMealAreas.size() > 0) {
                    //刷新转入桌台弹窗的数据
                    mDiaTurnChooseTable.refreshMealAreaData(mMealAreas);
                    mDiaTurnChooseTable.refreshTableList(tableList);
                    mDiaTurnChooseTable.getTableListAdapter().setSelectItem(0);
                    mDiaTurnChooseTable.getTableListAdapter().notifyDataSetChanged();
                    mDiaTurnChooseTable.show();
                }else {
                    CommonUtils.makeEventToast(mContext,"无可转的桌台",false);
                }
//                Log.e("TAG", "请求可用桌台列表");

            } else {
                this.datas = (ArrayList<CanteenTableEntity>) tableList;
                if (mSwiperefreshLayout.isRefreshing()) {
                    mSwiperefreshLayout.setRefreshing(false);
                }
                mAdapter.update(tableList, true);
            }
        } else {
            // 空页面
            showEmptyHyperLink(getActivity(), API.URL_OPERATION_PALTFORM, "");
        }
    }

    /**
     * 根据餐区筛选转台弹框里的桌台数据
     */
    private void updateTurnList(MealAreaEntity mealAreaEntity) {
        if (mealAreaEntity != null) {
            mTableListPresenter.requestTableList(mealAreaEntity.getRelateId(), shopId, "0,6,10");
            turnFlag = true;
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

            if (getUserVisibleHint()) {
                // restore view helper
                restoreViewHelper();
                // reset adapter
                mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
                // update data
                mAdapter.update(tableList, true);
            }

            Elog.e("tableList.size==", tableList.size());
        } else {
            showEmptyHyperLink(getActivity(), API.URL_OPERATION_PALTFORM, "");
            Elog.e("tableList.size==", "00000000");
        }
    }

    /**
     * 更新桌台操作
     *
     * @param tableOption
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onUpdateTableOption(String tableOption) {
        if (tableOption != null) {
            this.tableOption = tableOption;
            if (tableOption.equals("99")){
                mSwiperefreshLayout.setEnabled(true);
            }else{
                mSwiperefreshLayout.setEnabled(false);
            }
        }
    }

    /**
     * 获取餐区id
     *
     * @param diningArea
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onUpdateAreaId(MealAreaEntity diningArea) {
        if (diningAreaRelateId != null && getUserVisibleHint()) {
            this.diningAreaRelateId = diningArea.getRelateId();
            onRefresh();
        }
    }
//
//    /**
//     * 获取餐区集合
//     *
//     * @param mealAreas
//     */
//    @Subscribe(threadMode = ThreadMode.MainThread)
//    public void onGetMealArea(List<MealAreaEntity> mealAreas) {
//        if (mealAreas != null && mealAreas.size() > 0) {
//            this.mMealAreas = mealAreas;
//            Log.e("TAG", "获取餐区集合");
//        }
//    }

    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.REFRESH_TABLE_LIST && getUserVisibleHint()) {
            onRefresh();
        }
    }
}
