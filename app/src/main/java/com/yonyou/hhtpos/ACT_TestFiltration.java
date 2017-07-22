package com.yonyou.hhtpos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.ReserveOrderListEntity;
import com.yonyou.hhtpos.bean.TableCapacityEntity;
import com.yonyou.hhtpos.bean.TakeoutCompanyEntity;
import com.yonyou.hhtpos.bean.TakeoutMarketEntity;
import com.yonyou.hhtpos.bean.dish.DataBean;
import com.yonyou.hhtpos.bean.wm.FilterEntity;
import com.yonyou.hhtpos.dialog.DIA_EmployeeFiltration;
import com.yonyou.hhtpos.dialog.DIA_FamilySetMeal;
import com.yonyou.hhtpos.dialog.DIA_FreeOrder;
import com.yonyou.hhtpos.dialog.DIA_OrderDishSetWeight;
import com.yonyou.hhtpos.dialog.DIA_OrderDishNorms;
import com.yonyou.hhtpos.dialog.DIA_OrderDishSetPrice;
import com.yonyou.hhtpos.dialog.DIA_OrderDishCount;
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
import static com.yonyou.hhtpos.util.FiltrationUtil.getCookery;
import static com.yonyou.hhtpos.util.FiltrationUtil.getCookeryFish;
import static com.yonyou.hhtpos.util.FiltrationUtil.getDishBean;
import static com.yonyou.hhtpos.util.FiltrationUtil.getDishNorms;
import static com.yonyou.hhtpos.util.FiltrationUtil.getDishRemark;
import static com.yonyou.hhtpos.util.FiltrationUtil.getFakeData;
import static com.yonyou.hhtpos.util.FiltrationUtil.getFreeReasons;
import static com.yonyou.hhtpos.util.FiltrationUtil.getOptions;
import static com.yonyou.hhtpos.util.FiltrationUtil.getReserveOrderList;

//测试筛选布局
public class ACT_TestFiltration extends BaseActivity implements View.OnClickListener, ITakeoutCompanyView,ITakeoutMarketView,DIA_TakeOutFiltration.WMFCallback{

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
    @Bind(R.id.btn_confirm10)
    Button btnConfirm10;
    @Bind(R.id.btn_confirm11)
    Button btnConfirm11;

    private ArrayList<FilterItemEntity> filterItemList;
    private ArrayList<FilterOptionsEntity> filterOptionsEntities;
    private ArrayList<TableCapacityEntity> capacityEntities;
    private ArrayList<ReserveOrderListEntity> reserveOrderListEntity;


    private FilterItemEntity takeoutType;
    private FilterItemEntity takeoutMarket;

    private FilterItemEntity openOrderCompany;
    private FilterItemEntity freeReasons;
    DataBean dishBean;

    FilterItemEntity cookeryOption;
    FilterItemEntity fishCookeryOption;
    FilterItemEntity remarkOption;
    FilterItemEntity dishNorms;


    //    DIA_SelectTable dia_reserveFiltration;
    DIA_ReserveFiltration dia_reserveFiltration1;
//    DIA_ReserveList dia_reserveFiltration;
    DIA_TakeOutOpenOrder dia_reserveFiltration;
    DIA_TakeOutRefund dia_reserveFiltration2;
    DIA_TakeOutFiltration dia_takeOutFiltration;
    DIA_OrderDishCount dia_orderDishVegetable;
    DIA_OrderDishWeight dia_orderDishWeight;
    DIA_OrderDishSetPrice dia_orderDishSetPrice;
    DIA_OrderDishSetWeight dia_orderDishSetWeight;
    DIA_OrderDishNorms dia_orderDishNorms;
    DIA_FamilySetMeal dia_familySetMeal;
    DIA_FreeOrder dia_freeOrder;
    DIA_EmployeeFiltration dia_employeeFiltration;

    private String shopId = "C13352966C000000A60000000016E000";

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
        btnConfirm10.setOnClickListener(this);
        btnConfirm11.setOnClickListener(this);

        //假数据
        filterItemList = getFakeData();
        filterOptionsEntities = getOptions();
        capacityEntities = getCapacities();
        reserveOrderListEntity = getReserveOrderList();

        freeReasons = new FilterItemEntity();
        freeReasons.setTitle(mContext.getString(R.string.free_order_reason));
        freeReasons.setOptions(getFreeReasons());

         dishBean = getDishBean();

//        takeoutType = new FilterItemEntity(getTakeOutType(),"外卖类型");
//        takeoutMarket = new FilterItemEntity( getTakeOutMarket(),"市别");

        //外卖筛选数据获取
        companyPresenter = new TakeoutCompanyPresenterImpl(this,this);
        companyPresenter.getAllTakeOutCompany(shopId);

        marketPresenter = new TakeoutMarketPresenterImpl(this,this);

        cookeryOption = new FilterItemEntity();
        cookeryOption.setTitle("");
        cookeryOption.setOptions(getCookery());

        remarkOption = new FilterItemEntity();
        remarkOption.setTitle("");
        remarkOption.setOptions(getDishRemark());

        fishCookeryOption = new FilterItemEntity();
        fishCookeryOption.setTitle("");
        fishCookeryOption.setOptions(getCookeryFish());

        dishNorms = new FilterItemEntity();
        dishNorms.setTitle("");
        dishNorms.setOptions(getDishNorms());

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
                dia_takeOutFiltration.setWmfCallback(this);
                dia_takeOutFiltration.getDialog().show();
                break;
            case R.id.btn_confirm4:
//                dia_orderDishVegetable = new DIA_OrderDishCount(mContext,dishBean);
                dia_orderDishVegetable.getDialog().show();
                break;
            case R.id.btn_confirm5:
                dia_orderDishWeight = new DIA_OrderDishWeight(mContext,dishBean);
                dia_orderDishWeight.getDialog().show();
                break;
            case R.id.btn_confirm6:
                dia_orderDishNorms = new DIA_OrderDishNorms(mContext,dishBean);
                dia_orderDishNorms.getDialog().show();
                break;
            case R.id.btn_confirm7:
                dia_orderDishSetPrice = new DIA_OrderDishSetPrice(mContext,dishBean);
                dia_orderDishSetPrice.getDialog().show();
                break;
            case R.id.btn_confirm8:
                dia_orderDishSetWeight = new DIA_OrderDishSetWeight(mContext,dishBean);
                dia_orderDishSetWeight.getDialog().show();
                break;
            case R.id.btn_confirm9:
                dia_reserveFiltration = new DIA_TakeOutOpenOrder(mContext,openOrderCompany);
                dia_reserveFiltration.getDialog().show();
                break;
            case R.id.btn_confirm10:
                dia_freeOrder = new DIA_FreeOrder(mContext,freeReasons);
                dia_freeOrder.getDialog().show();
                break;
            case R.id.btn_confirm11:
                dia_employeeFiltration = new DIA_EmployeeFiltration(mContext,freeReasons);
                dia_freeOrder.getDialog().show();
                break;
            default:
                break;
        }
    }

    @Override
    public void getAllTakeOutCompany(List<TakeoutCompanyEntity> list) {
        ArrayList<FilterOptionsEntity> filterOptionsEntities = new ArrayList<>();
        ArrayList<FilterOptionsEntity> openOrderMarkets = new ArrayList<>();
        if (list!=null && list.size()>0){
            for (int i=0;i<list.size();i++){
                //外卖筛选 多项选择
                FilterOptionsEntity fde = new FilterOptionsEntity(list.get(i).getTakeOutCompanyName(),list.get(i).getTakeOutCompanyId(),
                        FiltrationUtil.TAKE_OUT_TYPE,false);
                filterOptionsEntities.add(fde);
                //外卖开单 单项选择
                FilterOptionsEntity market = new FilterOptionsEntity(list.get(i).getTakeOutCompanyName(),list.get(i).getTakeOutCompanyId(),
                        FiltrationUtil.VIEW_TAKEOUT_TYPE,false);
                openOrderMarkets.add(market);
            }
        }
        takeoutType = new FilterItemEntity(filterOptionsEntities,"外卖类型");
        openOrderCompany = new FilterItemEntity(openOrderMarkets,"");

        marketPresenter.getAllSchedule(shopId);
    }

    @Override
    public void getAllTakeOutMarket(List<TakeoutMarketEntity> list) {
        ArrayList<FilterOptionsEntity> filterOptionsEntities = new ArrayList<>();
        if (list!=null && list.size()>0){
            for (int i=0;i<list.size();i++){

                FilterOptionsEntity fde = new FilterOptionsEntity(list.get(i).getScheduleName(),list.get(i).getScheduleId(),
                        FiltrationUtil.MARKET_TYPE,false);
                filterOptionsEntities.add(fde);
            }
        }
        takeoutMarket = new FilterItemEntity(filterOptionsEntities,"市别");
        dia_takeOutFiltration = new DIA_TakeOutFiltration(mContext,takeoutType,takeoutMarket);
    }

    @Override
    public void sendItems(FilterEntity bean) {

    }
}
