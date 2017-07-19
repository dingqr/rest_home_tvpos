package com.yonyou.hhtpos.ui.dinner.wd;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.PackingFragmentAdapter;
import com.yonyou.hhtpos.bean.wd.OpenOrderEntity;
import com.yonyou.hhtpos.dialog.DIA_WDOpenOrder;
import com.yonyou.hhtpos.global.ReceiveConstants;
import com.yonyou.hhtpos.presenter.IWDOpenOrderPresenter;
import com.yonyou.hhtpos.presenter.Impl.WDOpenOrderPresenterImpl;
import com.yonyou.hhtpos.util.SalesModeUtil;
import com.yonyou.hhtpos.view.IWDOpenOrderView;
import com.yonyou.hhtpos.widgets.PagerSlidingTabStrip;

import butterknife.Bind;

/**
 * 外带列表左侧布局（viewpager + fragment）
 * 作者：liushuofei on 2017/7/4 16:47
 */
public class FRA_PackingLeft extends BaseFragment implements IWDOpenOrderView, View.OnClickListener, DIA_WDOpenOrder.OnSelectedListener {

    @Bind(R.id.vp_order)
    ViewPager mViewPager;
    @Bind(R.id.psts_tab)
    PagerSlidingTabStrip mTab;
    @Bind(R.id.iv_bill)
    ImageView mBillImg;

    /**当前Fragment */
    private FRA_PackingList mCurrentFramgent;
    /**记录前一个被选中的tab的位置 */
    private int prePosition;

    private PackingFragmentAdapter mFragmentAdapter;

    public static final int RB_ALL = 0;
    public static final int RB_OUT_STANDING = 1;
    public static final int RB_CHECKED_OUT = 2;

    /**中间者 */
    private IWDOpenOrderPresenter mPackingLeftPresenter;

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
        setVpAdapter();

        initSlidingTab();

        mPackingLeftPresenter = new WDOpenOrderPresenterImpl(mContext, this);
        mBillImg.setOnClickListener(this);
    }

    private void setVpAdapter() {
        mFragmentAdapter = new PackingFragmentAdapter(getSupportFragmentManager(), mContext);
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
                mCurrentFramgent = (FRA_PackingList) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_packing_left;
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
        CommonUtils.makeEventToast(mContext, mContext.getString(R.string.open_order_success), false);
        sendBroadcast(ReceiveConstants.WD_OPEN_ORDER_SUCCESS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_bill:
                DIA_WDOpenOrder dia_wdOpenOrder = new DIA_WDOpenOrder(mContext, this);
                dia_wdOpenOrder.getDialog().show();
                break;

            default:
                break;
        }
    }

    @Override
    public void confirm(String dinnerCount, String phone) {
        OpenOrderEntity bean = new OpenOrderEntity();
        bean.setPersonNum(dinnerCount);
//        bean.setMobileNo(phone);
        bean.setMobileNo("13671205992");
        bean.setShopId("hht");
        bean.setTableId("0001");
        bean.setWaiterId("0001");
        bean.setWaiterName("王五");
        bean.setSalesMode(SalesModeUtil.SALES_MODE_WD);
        mPackingLeftPresenter.openOrder(bean);
    }
}
