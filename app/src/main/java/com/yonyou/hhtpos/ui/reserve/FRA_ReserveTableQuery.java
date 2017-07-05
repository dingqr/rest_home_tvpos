package com.yonyou.hhtpos.ui.reserve;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_TableReserveQuery;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;
import com.yonyou.hhtpos.bean.TableReserveEntity;
import com.yonyou.hhtpos.dialog.DIA_ReserveFiltration;
import com.yonyou.hhtpos.dialog.DIA_ReserveList;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yonyou.hhtpos.util.FiltrationUtil.getFakeData;
import static com.yonyou.hhtpos.util.FiltrationUtil.getReserveOrderList;
import static com.yonyou.hhtpos.util.FiltrationUtil.getTableQueryFakeData;

/**
 * 作者：ybing on 2017/7/1 10:20
 * 邮箱：ybing@yonyou.com
 * 桌台预定总览页面
 */
public class FRA_ReserveTableQuery extends BaseFragment {
    @Bind(R.id.grv_table)
    RecyclerView grvTable;
    @Bind(R.id.btn_multiple_filtration)
    ImageButton btnMultSelection;

    private ADA_TableReserveQuery mAdapter;
    private DIA_ReserveList dia_reserveList;
    private DIA_ReserveFiltration dia_reserveFiltration;
    private ArrayList<ReserveOrderListEntity> reserveOrderListEntity;
    private ArrayList<FilterItemEntity> filterItemList;

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
        ArrayList <TableReserveEntity> data = getTableQueryFakeData();
        mAdapter = new ADA_TableReserveQuery(mContext,data);
        mAdapter.setmOnItemClickListener(new ADA_TableReserveQuery.OnTableClickListener() {
            @Override
            public void onTableClick(View view, int position) {
                //弹出右侧对话框
                dia_reserveList = new DIA_ReserveList(mContext,reserveOrderListEntity);
                dia_reserveList.getDialog().show();
            }
        });
        RecyclerView.LayoutManager gridLayoutManage = new GridLayoutManager(mContext,6);
        grvTable.setLayoutManager(gridLayoutManage);
        grvTable.setAdapter(mAdapter);

        reserveOrderListEntity = getReserveOrderList();
        filterItemList = getFakeData();

        btnMultSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia_reserveFiltration = new DIA_ReserveFiltration(mContext,filterItemList);
                dia_reserveFiltration.getDialog().show();
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_table_query_left;
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
