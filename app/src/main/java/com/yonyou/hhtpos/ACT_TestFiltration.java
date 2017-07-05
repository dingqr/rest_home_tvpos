package com.yonyou.hhtpos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;
import com.yonyou.hhtpos.bean.TableCapacityEntity;
import com.yonyou.hhtpos.dialog.DIA_OpenOrder;
import com.yonyou.hhtpos.dialog.DIA_ReserveFiltration;
import com.yonyou.hhtpos.dialog.DIA_ReserveList;
import com.yonyou.hhtpos.dialog.DIA_SelectTable;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yonyou.hhtpos.util.FiltrationUtil.getCapacities;
import static com.yonyou.hhtpos.util.FiltrationUtil.getFakeData;
import static com.yonyou.hhtpos.util.FiltrationUtil.getOptions;
import static com.yonyou.hhtpos.util.FiltrationUtil.getReserveOrderList;

//测试筛选布局
public class ACT_TestFiltration extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private ArrayList<FilterItemEntity> filterItemList;
    private ArrayList<FilterOptionsEntity> filterOptionsEntities;
    private ArrayList<TableCapacityEntity> capacityEntities;
    private ArrayList<ReserveOrderListEntity> reserveOrderListEntity;
//    DIA_SelectTable dia_reserveFiltration;
//    DIA_ReserveFiltration dia_reserveFiltration;
    DIA_ReserveList dia_reserveFiltration;


    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_test_filtration;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        btnConfirm.setOnClickListener(this);
        filterItemList = getFakeData();
        filterOptionsEntities = getOptions();
        capacityEntities = getCapacities();
        reserveOrderListEntity = getReserveOrderList();
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
//                dia_reserveFiltration = new DIA_ReserveFiltration(mContext,filterItemList);
//                dia_reserveFiltration.getDialog().show();
//                dia_reserveFiltration = new DIA_OpenOrder(mContext);
//                dia_reserveFiltration.getDialog().show();
//                dia_reserveFiltration = new DIA_SelectTable(mContext,filterOptionsEntities,capacityEntities);
//                dia_reserveFiltration.getDialog().show();
                dia_reserveFiltration = new DIA_ReserveList(mContext,reserveOrderListEntity);
                dia_reserveFiltration.getDialog().show();
                break;

            default:
                break;
        }
    }
}
