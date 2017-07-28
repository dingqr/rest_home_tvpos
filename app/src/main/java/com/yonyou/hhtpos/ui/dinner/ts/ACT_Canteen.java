package com.yonyou.hhtpos.ui.dinner.ts;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_MealArea;
import com.yonyou.hhtpos.adapter.CanteenFragmentAdapter;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.bean.CanteenTableEntity;
import com.yonyou.hhtpos.bean.MealAreaEntity;
import com.yonyou.hhtpos.dialog.DIA_Navigation;
import com.yonyou.hhtpos.presenter.ITSFiltrateTableListPresenter;
import com.yonyou.hhtpos.presenter.Impl.TSFiltrateTableListPresenterImpl;
import com.yonyou.hhtpos.view.ITSFiltrateTableView;
import com.yonyou.hhtpos.widgets.NoScrollViewPager;
import com.yonyou.hhtpos.widgets.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 堂食页面
 * 作者：liushuofei on 2017/7/8 10:42
 */
public class ACT_Canteen extends BaseActivity implements View.OnClickListener, ITSFiltrateTableView {

    @Bind(R.id.iv_menu)
    ImageView mMenuImg;
    @Bind(R.id.lv_meal_area)
    ListView mListView;
    @Bind(R.id.psts_tab)
    PagerSlidingTabStrip mTab;
    @Bind(R.id.vp_canteen_list)
    NoScrollViewPager mViewPager;
    @Bind(R.id.tv_table_turn)
    TextView tvTurnTable;
    @Bind(R.id.tv_table_merge)
    TextView tvMergeTable;
    @Bind(R.id.tv_table_split)
    TextView tvSplitTable;
    @Bind(R.id.tv_table_clear)
    TextView tvClearTable;

    private List<MealAreaEntity> dataList;
    private ADA_MealArea mAdapter;
    private CanteenFragmentAdapter mCanteenFragmentAdapter;

    private String shopId = "hht";

    public static final int RB_FREE = 0;
    public static final int RB_SETTLE = 1;
    public static final int RB_BOOK = 2;
    public static final int RB_OCCUPY = 3;
    public static final int RB_LOCKED = 4;

    /**
     * 当前Fragment
     */
    private FRA_CanteenTableList mCurrentFramgent;
    /**
     * 记录前一个被选中的tab的位置
     */
    private int prePosition;
    private TextView tabTextView;

    private DIA_Navigation dia_navigation;

    /**
     * 根据桌台操作筛选可用的桌台
     */
    private ITSFiltrateTableListPresenter filtrateTableListPresenter;

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
        return mViewPager;
    }

    @Override
    protected void initViewsAndEvents() {

        filtrateTableListPresenter = new TSFiltrateTableListPresenterImpl(this, this);
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

        mMenuImg.setOnClickListener(this);
        tvTurnTable.setOnClickListener(this);
        tvMergeTable.setOnClickListener(this);
        tvSplitTable.setOnClickListener(this);
        tvClearTable.setOnClickListener(this);
    }

    private boolean checkAbandonFlag() {
        if (tvTurnTable.getText().toString().equals(mContext.getString(R.string.abandon))) {
            return true;
        }
        if (tvMergeTable.getText().toString().equals(mContext.getString(R.string.abandon))) {
            return true;
        }
        if (tvSplitTable.getText().toString().equals(mContext.getString(R.string.abandon))) {
            return true;
        }
        if (tvClearTable.getText().toString().equals(mContext.getString(R.string.abandon))) {
            return true;
        }
        return false;
    }

    private void setVpAdapter() {
        mCanteenFragmentAdapter = new CanteenFragmentAdapter(getSupportFragmentManager(), mContext);
        mViewPager.setAdapter(mCanteenFragmentAdapter);
    }

    private void initSlidingTab() {
        mTab.setViewPager(mViewPager);
        mTab.setIndicatorColor(mContext.getResources().getColor(R.color.color_eb6247));
        tabTextView = (TextView) mTab.getTabsContainer().getChildAt(prePosition); //设置默认选中第一个时为红色
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
                mCurrentFramgent = (FRA_CanteenTableList) mCanteenFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
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

    @Override
    public void onClick(View v) {
        Drawable turnTableGray = getResources().getDrawable(R.drawable.ic_table_turn_gray);
        turnTableGray.setBounds(0, 0, turnTableGray.getMinimumWidth(), turnTableGray.getMinimumHeight());
        Drawable mergeTableGray = getResources().getDrawable(R.drawable.ic_table_merge_gray);
        mergeTableGray.setBounds(0, 0, mergeTableGray.getMinimumWidth(), mergeTableGray.getMinimumHeight());
        Drawable splitTableGray = getResources().getDrawable(R.drawable.ic_table_split_gray);
        splitTableGray.setBounds(0, 0, splitTableGray.getMinimumWidth(), splitTableGray.getMinimumHeight());
        Drawable clearTableGray = getResources().getDrawable(R.drawable.ic_table_clear_gray);
        clearTableGray.setBounds(0, 0, clearTableGray.getMinimumWidth(), clearTableGray.getMinimumHeight());
        Drawable turnTableRed = getResources().getDrawable(R.drawable.ic_table_turn);
        turnTableRed.setBounds(0, 0, turnTableRed.getMinimumWidth(), turnTableRed.getMinimumHeight());
        Drawable mergeTableRed = getResources().getDrawable(R.drawable.ic_table_merge);
        mergeTableRed.setBounds(0, 0, mergeTableRed.getMinimumWidth(), mergeTableRed.getMinimumHeight());
        Drawable splitTableRed = getResources().getDrawable(R.drawable.ic_table_split);
        splitTableRed.setBounds(0, 0, splitTableRed.getMinimumWidth(), splitTableRed.getMinimumHeight());
        Drawable clearTableRed = getResources().getDrawable(R.drawable.ic_table_clear);
        clearTableRed.setBounds(0, 0, clearTableRed.getMinimumWidth(), clearTableRed.getMinimumHeight());

        mTab.setIndicatorColor(Color.parseColor("#eb6247"));
        mTab.setIndicatorColorWhite(Color.parseColor("#ffffff"));
        mTab.setTabTextColor(Color.parseColor("#222222"));
        mTab.setTabTextColorGray(Color.parseColor("#cccccc"));

        switch (v.getId()) {
            case R.id.iv_menu:
                dia_navigation = new DIA_Navigation(mContext, MyApplication.dataList);
                dia_navigation.getDialog().show();
                break;

            case R.id.tv_table_turn:
                // 转台按钮变成放弃，并台、转台、清台按钮置灰并且不可点。
                // top导航不可点击，默认选中的红线消失，选项置灰。
                // 右侧导航不可点击，默认选中的红线消失，选项置灰。
                if (!checkAbandonFlag()) {
                    tvTurnTable.setText(mContext.getString(R.string.abandon));

                    tvMergeTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvMergeTable.setCompoundDrawables(mergeTableGray, null, null, null);
                    tvMergeTable.setClickable(false);

                    tvSplitTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvSplitTable.setCompoundDrawables(splitTableGray, null, null, null);
                    tvSplitTable.setClickable(false);

                    tvClearTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvClearTable.setCompoundDrawables(clearTableGray, null, null, null);
                    tvClearTable.setClickable(false);

                    mAdapter.disableAllItemChooser();
                    //调用转台接口
                    filtrateTableListPresenter.requestFiltrateTableList("", shopId, "1");
                    setTopTab(false);

                } else {
                    tvTurnTable.setText(mContext.getString(R.string.table_turn));

                    tvMergeTable.setTextColor(Color.parseColor("#222222"));
                    tvMergeTable.setCompoundDrawables(mergeTableRed, null, null, null);
                    tvMergeTable.setClickable(true);

                    tvSplitTable.setTextColor(Color.parseColor("#222222"));
                    tvSplitTable.setCompoundDrawables(splitTableRed, null, null, null);
                    tvSplitTable.setClickable(true);

                    tvClearTable.setTextColor(Color.parseColor("#222222"));
                    tvClearTable.setCompoundDrawables(clearTableRed, null, null, null);
                    tvClearTable.setClickable(true);

                    mAdapter.enableItemChooser();

                    setTopTab(true);
                }
                break;

            case R.id.tv_table_merge:
                //并台
                if (!checkAbandonFlag()) {
                    tvMergeTable.setText(mContext.getString(R.string.abandon));

                    tvTurnTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvTurnTable.setCompoundDrawables(turnTableGray, null, null, null);
                    tvTurnTable.setClickable(false);

                    tvSplitTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvSplitTable.setCompoundDrawables(splitTableGray, null, null, null);
                    tvSplitTable.setClickable(false);

                    tvClearTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvClearTable.setCompoundDrawables(clearTableGray, null, null, null);
                    tvClearTable.setClickable(false);

                    mAdapter.disableAllItemChooser();
                    //调用并台接口
                    filtrateTableListPresenter.requestFiltrateTableList("", shopId, "2");
                    setTopTab(false);

                } else {
                    tvMergeTable.setText(mContext.getString(R.string.table_merge));

                    tvTurnTable.setTextColor(Color.parseColor("#222222"));
                    tvTurnTable.setCompoundDrawables(turnTableRed, null, null, null);
                    tvTurnTable.setClickable(true);

                    tvSplitTable.setTextColor(Color.parseColor("#222222"));
                    tvSplitTable.setCompoundDrawables(splitTableRed, null, null, null);
                    tvSplitTable.setClickable(true);

                    tvClearTable.setTextColor(Color.parseColor("#222222"));
                    tvClearTable.setCompoundDrawables(clearTableRed, null, null, null);
                    tvClearTable.setClickable(true);

                    mAdapter.enableItemChooser();
                    setTopTab(true);
                }
                break;

            case R.id.tv_table_split:
                //拼台
                if (!checkAbandonFlag()) {
                    tvSplitTable.setText(mContext.getString(R.string.abandon));

                    tvTurnTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvTurnTable.setCompoundDrawables(turnTableGray, null, null, null);
                    tvTurnTable.setClickable(false);

                    tvMergeTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvMergeTable.setCompoundDrawables(mergeTableGray, null, null, null);
                    tvMergeTable.setClickable(false);

                    tvClearTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvClearTable.setCompoundDrawables(clearTableGray, null, null, null);
                    tvClearTable.setClickable(false);

                    mAdapter.disableAllItemChooser();
                    //调用拼台接口
                    filtrateTableListPresenter.requestFiltrateTableList("", shopId, "3");
                    //把桌台列表表头置灰并且viewPager设置为不可滑动
                    setTopTab(false);
                } else {
                    tvSplitTable.setText(mContext.getString(R.string.table_put_together));

                    tvTurnTable.setTextColor(Color.parseColor("#222222"));
                    tvTurnTable.setCompoundDrawables(turnTableRed, null, null, null);
                    tvTurnTable.setClickable(true);

                    tvMergeTable.setTextColor(Color.parseColor("#222222"));
                    tvMergeTable.setCompoundDrawables(mergeTableRed, null, null, null);
                    tvMergeTable.setClickable(true);

                    tvClearTable.setTextColor(Color.parseColor("#222222"));
                    tvClearTable.setCompoundDrawables(clearTableRed, null, null, null);
                    tvClearTable.setClickable(true);

                    mAdapter.enableItemChooser();
                    setTopTab(true);
                }
                break;

            case R.id.tv_table_clear:
                //清台
                if (!checkAbandonFlag()) {
                    tvClearTable.setText(mContext.getString(R.string.abandon));

                    tvTurnTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvTurnTable.setCompoundDrawables(turnTableGray, null, null, null);
                    tvTurnTable.setClickable(false);

                    tvMergeTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvMergeTable.setCompoundDrawables(mergeTableGray, null, null, null);
                    tvMergeTable.setClickable(false);

                    tvSplitTable.setTextColor(Color.parseColor("#CCCCCC"));
                    tvSplitTable.setCompoundDrawables(splitTableGray, null, null, null);
                    tvSplitTable.setClickable(false);

                    mAdapter.disableAllItemChooser();
                    setTopTab(false);

                } else {
                    tvClearTable.setText(mContext.getString(R.string.table_clear));

                    tvTurnTable.setTextColor(Color.parseColor("#222222"));
                    tvTurnTable.setCompoundDrawables(turnTableRed, null, null, null);
                    tvTurnTable.setClickable(true);

                    tvMergeTable.setTextColor(Color.parseColor("#222222"));
                    tvMergeTable.setCompoundDrawables(mergeTableRed, null, null, null);
                    tvMergeTable.setClickable(true);

                    tvSplitTable.setTextColor(Color.parseColor("#222222"));
                    tvSplitTable.setCompoundDrawables(splitTableRed, null, null, null);
                    tvSplitTable.setClickable(true);

                    mAdapter.enableItemChooser();
                    setTopTab(true);
                }
                break;
            default:
                break;
        }
    }

    private void setTopTab(boolean isClickAble) {
        if (isClickAble) {
            mViewPager.setEnabled(isClickAble);
            mViewPager.setNoScroll(!isClickAble);
            mTab.setClickAble(isClickAble);
            tabTextView = (TextView) mTab.getTabsContainer().getChildAt(prePosition); //设置默认选中第一个时为红色
            tabTextView.setTextColor(mContext.getResources().getColor(R.color.color_eb6247));
        } else {
            //把桌台列表表头置灰并且viewPager设置为不可滑动
            mViewPager.setEnabled(isClickAble);
            mViewPager.setNoScroll(!isClickAble);
            mTab.setClickAble(isClickAble);
        }

    }

    @Override
    public void finish() {
        if (null != dia_navigation) {
            dia_navigation.getDialog().dismiss();
        }
        super.finish();
    }

    @Override
    public void getFiltrateTable(List<CanteenTableEntity> tableList) {
        Elog.e("tableList.size==", tableList.size() + "EventBusSend");
        EventBus.getDefault().post(tableList);
    }
}
