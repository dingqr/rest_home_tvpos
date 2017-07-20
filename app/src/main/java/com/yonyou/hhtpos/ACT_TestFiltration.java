package com.yonyou.hhtpos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.fastjson.serializer.FilterUtils;
import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.MultipleOption;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;
import com.yonyou.hhtpos.bean.TableCapacityEntity;
import com.yonyou.hhtpos.bean.TakeoutCompanyEntity;
import com.yonyou.hhtpos.bean.TakeoutMarketEntity;
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
import com.yonyou.hhtpos.presenter.ITakeoutCompanyPresenter;
import com.yonyou.hhtpos.presenter.ITakeoutMarketPresenter;
import com.yonyou.hhtpos.presenter.Impl.TakeoutCompanyPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.TakeoutMarketPresenterImpl;
import com.yonyou.hhtpos.util.FiltrationUtil;
import com.yonyou.hhtpos.view.ITakeoutCompanyView;
import com.yonyou.hhtpos.view.ITakeoutMarketView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.yonyou.hhtpos.util.FiltrationUtil.getCapacities;
import static com.yonyou.hhtpos.util.FiltrationUtil.getFakeData;
import static com.yonyou.hhtpos.util.FiltrationUtil.getOptions;
import static com.yonyou.hhtpos.util.FiltrationUtil.getReserveOrderList;

//测试筛选布局
public class ACT_TestFiltration extends BaseActivity implements View.OnClickListener, ITakeoutCompanyView,ITakeoutMarketView {

    @Bind(R.id.ll_content)
    LinearLayout llContent;
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

    private FilterItemEntity openOrderCompany;




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

    private String shopId = "C482CE78AC000000AA8000000003A000";

    /**presenter*/
    private ITakeoutCompanyPresenter companyPresenter;
    private ITakeoutMarketPresenter marketPresenter;

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
        return llContent;
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





        //假数据
        filterItemList = getFakeData();
        filterOptionsEntities = getOptions();
        capacityEntities = getCapacities();
        reserveOrderListEntity = getReserveOrderList();
//        takeoutType = new FilterItemEntity(getTakeOutType(),"外卖类型");
//        takeoutMarket = new FilterItemEntity( getTakeOutMarket(),"市别");

        //外卖筛选数据获取
        companyPresenter = new TakeoutCompanyPresenterImpl(this,this);
        companyPresenter.getAllTakeOutCompany(shopId);

        marketPresenter = new TakeoutMarketPresenterImpl(this,this);
        marketPresenter.getAllSchedule(shopId);
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
                dia_reserveFiltration = new DIA_TakeOutOpenOrder(mContext,openOrderCompany);
                dia_reserveFiltration.getDialog().show();
                break;
            default:
                break;
        }
    }

    @Override
    public void getAllTakeOutCompany(List<TakeoutCompanyEntity> list) {
        ArrayList<FilterOptionsEntity> filterOptionsEntities = new ArrayList<>();
        ArrayList<FilterOptionsEntity> openOrderMarkets = new ArrayList<>();
        if (list!=null){
            for (int i=0;i<list.size();i++){
                //外卖筛选 多项选择
                FilterOptionsEntity fde = new FilterOptionsEntity(list.get(i).getTakeOutCompanyName(),list.get(i).getTakeOutCompanyId(),
                        FiltrationUtil.TAKE_OUT_TYPE,false);
                filterOptionsEntities.add(fde);
                //外卖开单 单项选择
                FilterOptionsEntity market = new FilterOptionsEntity(list.get(i).getTakeOutCompanyName(),list.get(i).getTakeOutCompanyId(),
                        FiltrationUtil.VIEW_TAKEOUT_TYPE,false);
                filterOptionsEntities.add(fde);
                openOrderMarkets.add(market);
            }
        }
        takeoutType = new FilterItemEntity(filterOptionsEntities,"外卖类型");
        openOrderCompany = new FilterItemEntity(openOrderMarkets,"");
    }

    @Override
    public void getAllTakeOutMarket(List<TakeoutMarketEntity> list) {
        ArrayList<FilterOptionsEntity> filterOptionsEntities = new ArrayList<>();
        if (list!=null){
            for (int i=0;i<list.size();i++){

                FilterOptionsEntity fde = new FilterOptionsEntity(list.get(i).getScheduleName(),list.get(i).getScheduleId(),
                        FiltrationUtil.MARKET_TYPE,false);
                filterOptionsEntities.add(fde);
            }
        }
        takeoutMarket = new FilterItemEntity(filterOptionsEntities,"市别");
    }
}
