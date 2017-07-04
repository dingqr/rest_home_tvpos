package com.yonyou.hhtpos.ui.book;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.PreviewFragmentAdapter;
import com.yonyou.hhtpos.dialog.DIA_Calendar;
import com.yonyou.hhtpos.dialog.DIA_TakeOutInfo;
import com.yonyou.hhtpos.widgets.PagerSlidingTabStrip;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 预定总览左侧布局
 * 作者：liushuofei on 2017/6/29 15:25
 */
public class FRA_BookLeft extends BaseFragment {

    @Bind(R.id.vp_preview)
    ViewPager mViewPager;
    @Bind(R.id.psts_tab)
    PagerSlidingTabStrip mTab;

    /**当前Fragment */
    private FRA_BookList mCurrentFramgent;
    /**记录前一个被选中的tab的位置 */
    private int prePosition;

    private PreviewFragmentAdapter mFragmentAdapter;

    public static final int RB_ALL = 0;
    public static final int RB_ACCEPT = 1;
    public static final int RB_ARRIVE = 2;
    public static final int RB_CANCEL = 3;

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
    }

    private void setVpAdapter() {
        mFragmentAdapter = new PreviewFragmentAdapter(getSupportFragmentManager(), mContext);
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
                mCurrentFramgent = (FRA_BookList) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_book_left;
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

    @OnClick({R.id.tv_yesterday, R.id.tv_tomorrow, R.id.tv_search, R.id.tv_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_yesterday:
//                DIA_OnceConfirm dia_onceConfirm = new DIA_OnceConfirm(mContext, "标题单行", "说明当前状态、提示解决方案");
//                dia_onceConfirm.getDialog().show();
                break;
            case R.id.tv_tomorrow:
//                DIA_DoubleConfirm dia_doubleConfirm = new DIA_DoubleConfirm(mContext, "说明当前状态、提示用户解决方案，最好不要超过两行", this);
//                dia_doubleConfirm.getDialog().show();
                DIA_TakeOutInfo dia_takeOutInfo = new DIA_TakeOutInfo(mContext);
                dia_takeOutInfo.getDialog().show();

                break;
            case R.id.tv_date:
                DIA_Calendar dia_calendar = new DIA_Calendar(mContext);
                dia_calendar.getDialog().show();
                break;
            case R.id.tv_search:
                ((ACT_BookPreview)getActivity()).switchToSearch();
                break;

            default:
                break;
        }
    }
}
