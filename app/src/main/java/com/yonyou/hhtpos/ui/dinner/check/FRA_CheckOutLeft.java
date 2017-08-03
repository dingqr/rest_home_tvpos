package com.yonyou.hhtpos.ui.dinner.check;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_CheckOutList;
import com.yonyou.hhtpos.adapter.ADA_ServiceCharge;
import com.yonyou.hhtpos.bean.check.CheckOrderListEntity;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;
import com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes;
import com.yonyou.hhtpos.widgets.BanSlideListView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.FROM_WHERE;
import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.IS_FROM_SETTLE_ACCOUNT;
import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.TABLE_BILL_ID;

/**
 * 结账左侧fragment
 * 作者：liushuofei on 2017/7/15 10:24
 */
public class FRA_CheckOutLeft extends BaseFragment {

    @Bind(R.id.tv_header)
    TextView tvHeader;
    @Bind(R.id.lv_check_out)
    ListView lvCheckOut;
    @Bind(R.id.tv_go_to_order)
    TextView tvGoToOrder;
    private ADA_CheckOutList mAdapter;
    private SettleAccountDataEntity dataBean;
    private TextView tvTotalCharge;//消费总计
    private TextView tvPersonNum;
    private TextView tvOpenTime;//开台时间
    private TextView tvWaiteName;
    private TextView tvBillResource;
    private TextView tvDishTotalPrice;//菜品总价
    private TextView tvDishPresent;//菜品赠送
    private TextView tvTotalServiceCharge;//服务费总计
    private TextView tvDishCharge;//菜品消费
    private BanSlideListView lvServiceCharge;//服务费详情列表
    private String tableBillId;
    private int fromWhere;
    private ADA_ServiceCharge mServiceChargeAdapter;

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onRefreshLeft(SettleAccountDataEntity settleAccountDataEntity) {
        this.dataBean = settleAccountDataEntity;
        refreshData();
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
        return lvCheckOut;
    }

    @Override
    protected void initViewsAndEvents() {
        tableBillId = ((ACT_CheckOut) getActivity()).getTableBillId();
        fromWhere = ((ACT_CheckOut) getActivity()).getFromWhere();

        View headView = LayoutInflater.from(mContext).inflate(R.layout.header_check_out_list, null);
        tvTotalCharge = (TextView) headView.findViewById(R.id.tv_total_charge);
        tvPersonNum = (TextView) headView.findViewById(R.id.tv_person_num);
        tvOpenTime = (TextView) headView.findViewById(R.id.tv_open_time);
        tvWaiteName = (TextView) headView.findViewById(R.id.tv_waiter_name);
        tvBillResource = (TextView) headView.findViewById(R.id.tv_bill_resource);
        tvDishTotalPrice = (TextView) headView.findViewById(R.id.tv_dish_total_price);
        tvDishPresent = (TextView) headView.findViewById(R.id.tv_dish_present);
        tvDishCharge = (TextView) headView.findViewById(R.id.tv_dish_charge);
        tvTotalServiceCharge = (TextView) headView.findViewById(R.id.tv_total_service_charge);
        lvServiceCharge = (BanSlideListView) headView.findViewById(R.id.lv_service_charge);
        lvCheckOut.addHeaderView(headView);

        //服务费明细
        mServiceChargeAdapter = new ADA_ServiceCharge(mContext);
        lvServiceCharge.setAdapter(mServiceChargeAdapter);

        mAdapter = new ADA_CheckOutList(mContext);
        lvCheckOut.setAdapter(mAdapter);

        lvCheckOut.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    tvHeader.setVisibility(View.VISIBLE);
                } else {
                    tvHeader.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.tv_go_to_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_go_to_order:
                Bundle bundle = new Bundle();
                bundle.putBoolean(IS_FROM_SETTLE_ACCOUNT, true);
                bundle.putString(TABLE_BILL_ID, tableBillId);
                bundle.putInt(FROM_WHERE, fromWhere);
                readyGo(ACT_OrderDishes.class, bundle);
                break;
        }
    }

    /**
     * 刷新界面数据
     */
    private void refreshData() {
        if (dataBean != null) {
            //消费总计
            if (!TextUtils.isEmpty(dataBean.getTotalCharge())) {
                tvTotalCharge.setText(dataBean.getTotalCharge());
            }
            //就餐人数
            if (!TextUtils.isEmpty(dataBean.personNum)) {
                tvPersonNum.setText(dataBean.personNum);
            }
            //就餐人数
            if (dataBean.openTime != null) {
                tvOpenTime.setText(AppDateUtil.getTimeStamp(dataBean.openTime, AppDateUtil.MM_DD_HH_MM));
            }
            //服务员
            if (!TextUtils.isEmpty(dataBean.waiterName)) {
                tvWaiteName.setText(dataBean.waiterName);
            }
            //订单来源
            if (!TextUtils.isEmpty(dataBean.billResource)) {
                tvBillResource.setText(dataBean.billResource);
            }
            //菜品消费
            if (!TextUtils.isEmpty(dataBean.getDishCharge())) {
                tvDishCharge.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.getDishCharge());
            }
            //菜品总价
            if (!TextUtils.isEmpty(dataBean.getDishChargeDetail().getDishCount())) {
                tvDishTotalPrice.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.getDishChargeDetail().getDishCount());
            }
            //赠送
            if (dataBean.getDishChargeDetail() != null && !TextUtils.isEmpty(dataBean.getDishChargeDetail().getDishPresent())) {
                tvDishPresent.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.getDishChargeDetail().getDishPresent());
            }
            //服务费总计
            if (!TextUtils.isEmpty(dataBean.serviceCharge)) {
                tvTotalServiceCharge.setText(mContext.getResources().getString(R.string.RMB_symbol) + dataBean.serviceCharge);
            }
            //服务费明细
            if (dataBean.serviceChargeDetail != null && dataBean.serviceChargeDetail.size() > 0) {
                mServiceChargeAdapter.update(dataBean.serviceChargeDetail, true);
            }

        }
        if (dataBean.orderDishes != null && dataBean.orderDishes.size() > 0) {
            mAdapter.update(dataBean.orderDishes);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_check_out_left;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    private String currentTable;

    @Override
    public void showBusinessError(ErrorBean error) {
    }

    /**
     * 重新组合账单中菜单明细列表的数据，按照桌台依次排列的新集合
     */
    private ArrayList<CheckOrderListEntity> regroupNewDataList() {
        HashMap<String, ArrayList<CheckOrderListEntity>> map = new HashMap<>();
        //(1)先删选出当前有多少个不同的桌台
        //列表中涉及到的所有桌台
        ArrayList<String> mTableTypeList = new ArrayList<>();
        if (dataBean.orderDishes.size() > 0) {
            mTableTypeList.add(dataBean.orderDishes.get(0).tableName);
        }
        for (int i = 0; i < dataBean.orderDishes.size(); i++) {
            String tableName = dataBean.orderDishes.get(i).tableName;
            for (int j = 0; j < mTableTypeList.size(); j++) {
                String existTable = mTableTypeList.get(j);
                if (tableName.equals(existTable)) {
                    break;
                }
                if (!tableName.equals(existTable) && j == mTableTypeList.size() - 1) {
                    mTableTypeList.add(tableName);
                    break;
                }
            }
        }
        //(2)以桌台为key,将同一个桌台下的订单，分组到不同集合中。
        for (int i = 0; i < mTableTypeList.size(); i++) {
            currentTable = mTableTypeList.get(i);
            ArrayList<CheckOrderListEntity> tableOrderList = new ArrayList<>();
            for (int j = 0; j < dataBean.orderDishes.size(); j++) {
                CheckOrderListEntity checkOrderListEntity = dataBean.orderDishes.get(0);
                if (checkOrderListEntity.tableName.equals(currentTable)) {
                    tableOrderList.add(checkOrderListEntity);
                }
            }
            map.put(currentTable, tableOrderList);
        }
        //(3)将按照桌台分组的账单，重新添加到新的集合中
        ArrayList<CheckOrderListEntity> mNewDataList = new ArrayList<>();
        for (int i = 0; i < mTableTypeList.size(); i++) {
            String key = mTableTypeList.get(i);
            ArrayList<CheckOrderListEntity> newList = map.get(key);
            mNewDataList.addAll(newList);
        }
        return mNewDataList;
    }
}
