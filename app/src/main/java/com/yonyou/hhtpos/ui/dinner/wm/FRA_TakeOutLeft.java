package com.yonyou.hhtpos.ui.dinner.wm;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.TakeOutFragmentAdapter;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;
import com.yonyou.hhtpos.bean.TakeoutCompanyEntity;
import com.yonyou.hhtpos.bean.TakeoutMarketEntity;
import com.yonyou.hhtpos.bean.wm.FilterEntity;
import com.yonyou.hhtpos.bean.wm.OpenOrderEntity;
import com.yonyou.hhtpos.dialog.DIA_TakeOutFiltration;
import com.yonyou.hhtpos.dialog.DIA_TakeOutOpenOrder;
import com.yonyou.hhtpos.global.ReceiveConstants;
import com.yonyou.hhtpos.presenter.ITakeoutCompanyPresenter;
import com.yonyou.hhtpos.presenter.ITakeoutMarketPresenter;
import com.yonyou.hhtpos.presenter.IWMOpenOrderPresenter;
import com.yonyou.hhtpos.presenter.Impl.TakeoutCompanyPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.TakeoutMarketPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.WMOpenOrderPresenterImpl;
import com.yonyou.hhtpos.util.FiltrationUtil;
import com.yonyou.hhtpos.view.ITakeoutCompanyView;
import com.yonyou.hhtpos.view.ITakeoutMarketView;
import com.yonyou.hhtpos.view.IWMOpenOrderView;
import com.yonyou.hhtpos.widgets.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 外卖左侧fragment
 * 作者：liushuofei on 2017/7/6 10:46
 */
public class FRA_TakeOutLeft extends BaseFragment implements IWMOpenOrderView,ITakeoutCompanyView,ITakeoutMarketView,
        DIA_TakeOutOpenOrder.WmCallback,DIA_TakeOutFiltration.WMFCallback {

    @Bind(R.id.psts_tab)
    PagerSlidingTabStrip mTab;
    @Bind(R.id.vp_take_out)
    ViewPager mViewPager;
    @Bind(R.id.iv_take_out)
    ImageView ivTakeOut;
    @Bind(R.id.tv_filter)
    TextView tvFilter;

    /**当前Fragment */
    private FRA_TakeOutList mCurrentFramgent;
    /**记录前一个被选中的tab的位置 */
    private int prePosition;

    private TakeOutFragmentAdapter mFragmentAdapter;
    private String shopId = "C13352966C000000A60000000016E000";

    /**外卖开单弹框外卖公司列表数据*/
    private FilterItemEntity openOrderCompany;
    private ITakeoutCompanyPresenter companyPresenter;


    /**外卖列表筛选弹框外卖公司列表数据*/
    private FilterItemEntity takeoutType;

    /**外卖列表筛选弹框外卖市别列表数据*/
    private FilterItemEntity takeoutMarket;
    private ITakeoutMarketPresenter marketPresenter;

    /**筛选弹框*/
    private  DIA_TakeOutFiltration dia_takeOutFiltration;
    /**开单弹框*/
    private DIA_TakeOutOpenOrder dia_takeOutOpenOrder;

    public static final int RB_ALL = 0;
    public static final int RB_NO_ORDER = 1;
    public static final int RB_PLACE_ORDER = 2;
    public static final int RB_CHECKED_OUT = 3;
    public static final int RB_REFUNDED = 4;

    /**中间者 */
    private IWMOpenOrderPresenter mTakeOutPresenter;

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
        return mViewPager;
    }

    @Override
    protected void initViewsAndEvents() {
        setVpAdapter();

        initSlidingTab();

        mTakeOutPresenter = new WMOpenOrderPresenterImpl(mContext, this);
        companyPresenter = new TakeoutCompanyPresenterImpl(mContext,this);
        marketPresenter = new TakeoutMarketPresenterImpl(mContext,this);

        companyPresenter.getAllTakeOutCompany(shopId);


        ivTakeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia_takeOutOpenOrder = new DIA_TakeOutOpenOrder(mContext,openOrderCompany);
                dia_takeOutOpenOrder.setWmCallback(FRA_TakeOutLeft.this);
                dia_takeOutOpenOrder.getDialog().show();
            }
        });
        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia_takeOutFiltration.setWmfCallback(FRA_TakeOutLeft.this);
                dia_takeOutFiltration.getDialog().show();
            }
        });

//        bean.setTakeOutCompanyId("C4BECEC6040000008000000000296000");
//        bean.setName("小点的王");
//        bean.setPhone("13466668888");
//        bean.setAddress("海淀区苏州街维亚大厦");
//        bean.setPersonNum("3");
//        bean.setReserveTime("2017-7-18 15:30:23");
//        bean.setSendNow("Y");
//        bean.setShopId("C482CE78AC000000AA8000000003A000");
//        mTakeOutPresenter.openOrder(bean);
    }



    private void setVpAdapter() {
        mFragmentAdapter = new TakeOutFragmentAdapter(getSupportFragmentManager(), mContext);
        mViewPager.setAdapter(mFragmentAdapter);
    }

    private void initSlidingTab(){
        mTab.setViewPager(mViewPager);
        mTab.setIndicatorColor(mContext.getResources().getColor(R.color.color_eb6247));
        TextView tabTextView = (TextView) mTab.getTabsContainer().getChildAt(prePosition); //设置默认选中第一个时为红色
        tabTextView.setTextColor(mContext.getResources().getColor(R.color.color_eb6247));

        mTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TextView tabTextView = (TextView) mTab.getTabsContainer().getChildAt(position);
                tabTextView.setTextColor(mContext.getResources().getColor(R.color.color_eb6247));
                TextView preTabTextView = (TextView) mTab.getTabsContainer().getChildAt(prePosition);
                preTabTextView.setTextColor(mContext.getResources().getColor(R.color.color_222222));
                prePosition = position;
                //获取当前显示的Fragment
                mCurrentFramgent = (FRA_TakeOutList) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_take_out_left;
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
    public void openOrder() {
        sendBroadcast(ReceiveConstants.WM_OPEN_ORDER_SUCCESS);
    }

    @Override
    public void getAllTakeOutCompany(List<TakeoutCompanyEntity> list) {
        ArrayList<FilterOptionsEntity> filterCompanies = new ArrayList<>();
        ArrayList<FilterOptionsEntity> openOrderCompanies = new ArrayList<>();
        if (list!=null && list.size()>0){
            for (int i=0;i<list.size();i++){
                //外卖筛选 多项选择
                FilterOptionsEntity fde = new FilterOptionsEntity(list.get(i).getTakeOutCompanyName(),list.get(i).getTakeOutCompanyId(),
                        FiltrationUtil.TAKE_OUT_TYPE,false);
                filterCompanies.add(fde);
                //外卖开单 单项选择
                FilterOptionsEntity market = new FilterOptionsEntity(list.get(i).getTakeOutCompanyName(),list.get(i).getTakeOutCompanyId(),
                        FiltrationUtil.VIEW_TAKEOUT_TYPE,false);
                openOrderCompanies.add(market);
            }
        }
        marketPresenter.getAllSchedule(shopId);
        takeoutType = new FilterItemEntity(filterCompanies,"外卖类型");
        openOrderCompany = new FilterItemEntity(openOrderCompanies,"");

    }

    @Override
    public void sendWmEntity(OpenOrderEntity wmOpenOrderEntity) {
        mTakeOutPresenter.openOrder(wmOpenOrderEntity);
    }

    @Override
    public void getAllTakeOutMarket(List<TakeoutMarketEntity> list) {
        ArrayList<FilterOptionsEntity> filterOptionsEntities = new ArrayList<>();
        if (list != null){
            for (int i = 0;i < list.size();i++){
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
        EventBus.getDefault().post(bean);
    }
}