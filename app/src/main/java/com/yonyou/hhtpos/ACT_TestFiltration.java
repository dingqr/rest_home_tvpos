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
import com.yonyou.hhtpos.dialog.DIA_ChooseTime;
import com.yonyou.hhtpos.dialog.DIA_FamilySetMeal;
import com.yonyou.hhtpos.dialog.DIA_OrderDishFixedPrice;
import com.yonyou.hhtpos.dialog.DIA_OrderDishNorms;
import com.yonyou.hhtpos.dialog.DIA_OrderDishSetPrice;
import com.yonyou.hhtpos.dialog.DIA_OrderDishVegetable;
import com.yonyou.hhtpos.dialog.DIA_OrderDishWeight;
import com.yonyou.hhtpos.dialog.DIA_ReserveFiltration;
import com.yonyou.hhtpos.dialog.DIA_TakeOutFiltration;
import com.yonyou.hhtpos.dialog.DIA_TakeOutOpenOrder;
import com.yonyou.hhtpos.dialog.DIA_TakeOutRefund;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yonyou.hhtpos.util.FiltrationUtil.getCapacities;
import static com.yonyou.hhtpos.util.FiltrationUtil.getFakeData;
import static com.yonyou.hhtpos.util.FiltrationUtil.getOptions;
import static com.yonyou.hhtpos.util.FiltrationUtil.getReserveOrderList;
import static com.yonyou.hhtpos.util.FiltrationUtil.getTakeOutMarket;
import static com.yonyou.hhtpos.util.FiltrationUtil.getTakeOutType;

//测试筛选布局
public class ACT_TestFiltration extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.btn_confirm1)
    Button btnConfirm1;
    @Bind(R.id.btn_confirm2)
    Button btnConfirm2;
    @Bind(R.id.btn_confirm3)
    Button btnConfirm3;
    @Bind(R.id.btn_confirm4)
    Button btnConfirm4;
    @Bind(R.id.btn_confirm5)
    Button btnConfirm5;
    @Bind(R.id.btn_confirm6)
    Button btnConfirm6;
    @Bind(R.id.btn_confirm7)
    Button btnConfirm7;
    @Bind(R.id.btn_confirm8)
    Button btnConfirm8;
    @Bind(R.id.btn_confirm9)
    Button btnConfirm9;

    private ArrayList<FilterItemEntity> filterItemList;
    private ArrayList<FilterOptionsEntity> filterOptionsEntities;
    private ArrayList<TableCapacityEntity> capacityEntities;
    private ArrayList<ReserveOrderListEntity> reserveOrderListEntity;


    private FilterItemEntity takeoutType;
    private FilterItemEntity takeoutMarket;
//    DIA_SelectTable dia_reserveFiltration;
    DIA_ReserveFiltration dia_reserveFiltration1;
//    DIA_ReserveList dia_reserveFiltration;
    DIA_TakeOutOpenOrder dia_reserveFiltration;
    DIA_TakeOutRefund dia_reserveFiltration2;
    DIA_TakeOutFiltration dia_reserveFiltration3;
    DIA_OrderDishVegetable dia_orderDishVegetable;
    DIA_OrderDishWeight dia_orderDishWeight;
    DIA_OrderDishSetPrice dia_orderDishSetPrice;
    DIA_OrderDishFixedPrice dia_orderDishFixedPrice;
    DIA_OrderDishNorms dia_orderDishNorms;
    DIA_FamilySetMeal dia_familySetMeal;
//    DIA_ChooseTime dia_familySetMeal;



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
        btnConfirm1.setOnClickListener(this);
        btnConfirm2.setOnClickListener(this);
        btnConfirm3.setOnClickListener(this);
        btnConfirm4.setOnClickListener(this);
        btnConfirm5.setOnClickListener(this);
        btnConfirm6.setOnClickListener(this);
        btnConfirm7.setOnClickListener(this);
        btnConfirm8.setOnClickListener(this);
        btnConfirm9.setOnClickListener(this);

        filterItemList = getFakeData();
        filterOptionsEntities = getOptions();
        capacityEntities = getCapacities();
        reserveOrderListEntity = getReserveOrderList();
        takeoutType = new FilterItemEntity(getTakeOutType(),"外卖类型");
        takeoutMarket = new FilterItemEntity( getTakeOutMarket(),"市别");
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
                dia_familySetMeal = new DIA_FamilySetMeal(mContext);
                dia_familySetMeal.getDialog().show();

                break;
            case R.id.btn_confirm1:
                dia_reserveFiltration1 = new DIA_ReserveFiltration(mContext,filterItemList);
                dia_reserveFiltration1.getDialog().show();
                break;
            case R.id.btn_confirm2:
                dia_reserveFiltration2 = new DIA_TakeOutRefund(mContext);
                dia_reserveFiltration2.getDialog().show();
                break;
            case R.id.btn_confirm3:
                dia_reserveFiltration3 = new DIA_TakeOutFiltration(mContext,takeoutType,takeoutMarket);
                dia_reserveFiltration3.getDialog().show();
                break;
            case R.id.btn_confirm4:
                dia_orderDishVegetable = new DIA_OrderDishVegetable(mContext);
                dia_orderDishVegetable.getDialog().show();
                break;
            case R.id.btn_confirm5:
                dia_orderDishWeight = new DIA_OrderDishWeight(mContext);
                dia_orderDishWeight.getDialog().show();
                break;
            case R.id.btn_confirm6:
                dia_orderDishNorms = new DIA_OrderDishNorms(mContext);
                dia_orderDishNorms.getDialog().show();
                break;
            case R.id.btn_confirm7:
                dia_orderDishSetPrice = new DIA_OrderDishSetPrice(mContext);
                dia_orderDishSetPrice.getDialog().show();
                break;
            case R.id.btn_confirm8:
                dia_orderDishFixedPrice = new DIA_OrderDishFixedPrice(mContext);
                dia_orderDishFixedPrice.getDialog().show();
                break;
            case R.id.btn_confirm9:
                dia_reserveFiltration = new DIA_TakeOutOpenOrder(mContext);
                dia_reserveFiltration.getDialog().show();
                break;
//            case R.id.btn_confirm9:
//                dia_familySetMeal = new DIA_ChooseTime(mContext);
//                dia_familySetMeal.show();
//                break;
            default:
                break;
        }
    }
}
