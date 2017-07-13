package com.yonyou.hhtpos.ui.dinner.ts;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_MealArea;
import com.yonyou.hhtpos.adapter.CanteenFragmentAdapter;
import com.yonyou.hhtpos.adapter.PackingFragmentAdapter;
import com.yonyou.hhtpos.bean.MealAreaEntity;
import com.yonyou.hhtpos.ui.dinner.wd.FRA_PackingList;
import com.yonyou.hhtpos.widgets.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 堂食页面
 * 作者：liushuofei on 2017/7/8 10:42
 */
public class ACT_Canteen extends BaseActivity{

    @Bind(R.id.lv_meal_area)
    ListView mListView;
    @Bind(R.id.psts_tab)
    PagerSlidingTabStrip mTab;
    @Bind(R.id.vp_canteen_list)
    ViewPager mViewPager;

    private List<MealAreaEntity> dataList;
    private ADA_MealArea mAdapter;
    private CanteenFragmentAdapter mCanteenFragmentAdapter;

    public static final int RB_FREE = 0;
    public static final int RB_SETTLE = 1;
    public static final int RB_BOOK = 2;
    public static final int RB_OCCUPY = 3;
    public static final int RB_LOCKED = 4;

    /**当前Fragment */
    private FRA_CanteenList mCurrentFramgent;
    /**记录前一个被选中的tab的位置 */
    private int prePosition;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_canteen;
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
        mAdapter = new ADA_MealArea(mContext);
        mListView.setAdapter(mAdapter);

        // 假数据
        dataList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MealAreaEntity bean = new MealAreaEntity();
            if (i == 0) {
                bean.setCheck(true);
            }
            dataList.add(bean);
        }
        mAdapter.update(dataList);

        setVpAdapter();

        initSlidingTab();
    }

    private void setVpAdapter() {
        mCanteenFragmentAdapter = new CanteenFragmentAdapter(getSupportFragmentManager(), mContext);
        mViewPager.setAdapter(mCanteenFragmentAdapter);
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
                mCurrentFramgent = (FRA_CanteenList) mCanteenFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }
}
