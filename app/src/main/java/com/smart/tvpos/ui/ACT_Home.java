package com.smart.tvpos.ui;

import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.framework.library.adapter.rv.MultiItemTypeAdapter;
import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.ReceiveConstants;
import com.smart.framework.library.common.utils.AppDateUtil;
import com.smart.framework.library.common.utils.AppSharedPreferences;
import com.smart.framework.library.common.utils.StringUtil;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.adapter.ADA_BranchList;
import com.smart.tvpos.adapter.ADA_ChartIndicator;
import com.smart.tvpos.adapter.ADA_HomeMenu;
import com.smart.tvpos.adapter.ADA_LatestDynamic;
import com.smart.tvpos.adapter.ADA_LatestWarn;
import com.smart.tvpos.adapter.ADA_NurseProgressItem;
import com.smart.tvpos.bean.AbilityNumEntity;
import com.smart.tvpos.bean.BranchAddressEntity;
import com.smart.tvpos.bean.ChartCommonEntity;
import com.smart.tvpos.bean.EquipmentStatusEntity;
import com.smart.tvpos.bean.HomeHeadEntity;
import com.smart.tvpos.bean.HomeMenuEntity;
import com.smart.tvpos.bean.JobItemEntity;
import com.smart.tvpos.bean.LatestDynamicEntity;
import com.smart.tvpos.bean.LatestWarnEntity;
import com.smart.tvpos.bean.NurseLevelEntity;
import com.smart.tvpos.bean.StaffEntity;
import com.smart.tvpos.bean.UserHealthDataNum;
import com.smart.tvpos.bean.WarningEntity;
import com.smart.tvpos.interfaces.BarChartEntity;
import com.smart.tvpos.mvp.HomePresenter;
import com.smart.tvpos.mvp.IHomeView;
import com.smart.tvpos.util.CommonUtil;
import com.smart.tvpos.util.Constants;
import com.smart.tvpos.util.SharePreConstants;
import com.smart.tvpos.widgets.BanSlideListView;
import com.smart.tvpos.widgets.CommonPopupWindow;
import com.smart.tvpos.widgets.DashBarChartView;
import com.smart.tvpos.widgets.RadarChartView;
import com.smart.tvpos.widgets.RingChartView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JoJo on 2018/6/21.
 * wechat：18510829974
 * description：主页：数据监控
 */
public class ACT_Home extends BaseActivity implements IHomeView {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_rest_home_num)
    TextView tvRestHomeNum;
    @Bind(R.id.tv_user_num)
    TextView tvUserNum;
    @Bind(R.id.tv_bedroom_num)
    TextView tvBedroomNum;
    @Bind(R.id.tv_live_rate)
    TextView tvLiveRate;
    @Bind(R.id.iv_menu)
    ImageView ivMenu;
    @Bind(R.id.mattress_online_chart)
    RingChartView mMattressOnlineView;
//    @Bind(R.id.watch_online_chart)
//    RingChartView mWatchOnlineChartView;
    @Bind(R.id.abilityChartView)
    RingChartView mChartViewDataAblityView;
    @Bind(R.id.userlivingchartview)
    DashBarChartView mChartviewUserLivingView;
    @Bind(R.id.employeechartview)
    RingChartView mChartviewEmployeeView;
    @Bind(R.id.listView)
    ListView listViewNurseProgress;
    @Bind(R.id.iv_user)
    ImageView mIvUser;
    @Bind(R.id.red_point)
    RelativeLayout redPoint;
    @Bind(R.id.listview)
    BanSlideListView listview;

    @Bind(R.id.chart_indicator_user_in)
    RecyclerView userInIndicatorView;
    @Bind(R.id.chart_indicator_worker)
    RecyclerView workerIndicatorView;
    @Bind(R.id.chart_indicator_nursing_level)
    RecyclerView nurseLevelIndicatorView;
    @Bind(R.id.chart_indicator_mattress)
    RecyclerView mattressIndicatorView;
//    @Bind(R.id.chart_indicator_watch)
//    RecyclerView watchIndicatorView;
    @Bind(R.id.chart_indicator_ability)
    RecyclerView abilityIndicatorView;
    @Bind(R.id.chart_indicator_exam)
    RecyclerView examIndicatorView;

    @Bind(R.id.nursing_level_chart_view)
    RingChartView nurseLevelChartView;
    @Bind(R.id.body_examination_view)
    RadarChartView bodyExamChartView;

    @Bind(R.id.home_dynamic_list)
    RecyclerView homeDynamicListView;
    @Bind(R.id.elderly_warning_list)
    RecyclerView elderlyWarnListView;
    @Bind(R.id.no_data)
    TextView noWarnDataView;
    @Bind(R.id.current_no_heath_data)
    TextView noDataView;
    private AppSharedPreferences sharePre;
    private CommonPopupWindow mPopupWindow;
    private CommonPopupWindow.LayoutGravity mPopuplayoutGravity;
    private List<HomeMenuEntity> menuList = new ArrayList();
    private String[] memuTitle = {"数据监控", "护理进度"};
    private int[] memuIcons = {R.drawable.ic_data_watching, R.drawable.ic_nurse_progress};
    private HomePresenter mPresenter;
    private ADA_NurseProgressItem mAdapterNurseProgress;
    //暂时处理地图展示
    private int[] leftMarginArray = new int[]{125, 200 + 5 * 1, (140 + 5 * 1 + 40), 30, 160, 10 + 5 * 1, 48 + 5 * 1, 105 + 5 * 2, 115 + 5 * 1, 66 + 5 * 3, 150 + 5 * 1, 58, 0 + 5 * 2, 38 + 5 * 2, 66 + 5 * 2, 76 + 5 * 1, 140 + 5 * 2, (140 + 5 * 2 + 40), (140 + 5 * 2 + 40 * 2), 5 + 5 * 3, 38 + 5 * 3, 86, 105 + 5 * 3, 100 + 5 * 3};
    private int[] topMarginArray = new int[]{16, 2 + 40 * 1, (2 + 50 * 1 + 40), 20, 16, 16 + 50 * 1, 0 + 50 * 1, 14 + 50 * 1, 16 + 50 * 2, 14 + 50 * 3, 2 + 50 * 1, 0, 16 + 50 * 2, 0 + 50 * 2, 14 + 50 * 2, 16 + 50 * 1, 2 + 50 * 2, (2 + 50 * 2 + 50), (2 + 50 * 2 + 50), 50 * 3, 0 + 50 * 3, 14, 16 + 50 * 3, 2 + 50 * 2};
    //上海地区所有的养老院
    private List<BranchAddressEntity> areaList = new ArrayList<>();
    private Map<String, String> areaPointMap = new HashMap<>();
    private ADA_BranchList mBranchAdapter;
    private List<String> mUserInIndicatorList;
    private List<String> mWorkerIndicatorList;
    private List<String> mMattressIndicatorList;
//    private List<String> mWatchIndicatorList;
    private List<String> mAbilityIndicatorList;
    private List<String> mExamIndicatorList;

    private ADA_LatestWarn mAdapterLatestWarn;
    private ADA_LatestDynamic mAdapterLatestDynamic;
    private List<LatestDynamicEntity> latestDynamicList;

    private int numberUser;

    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.REFRESH_CURRENT_PAGE) {
            refreshNet();
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        if (Constants.TYPE.equals("总院")) {
            return R.layout.act_home_main;
        } else {
            return R.layout.act_home;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected long getRefreshTime() {
        return 3 * 1000;
    }

    @Override
    protected void initViewsAndEvents() {
        /**
         * admin账户权限进去时基于设计稿文案不变
         * 分院账号进去，第一个变为：（养老院简称）+智慧养老大数据监控平台
         */
        showView();
        initPopupWindow();

        initChartIndicator();

        mAdapterNurseProgress = new ADA_NurseProgressItem(mContext);
        listViewNurseProgress.setAdapter(mAdapterNurseProgress);

        mAdapterLatestWarn = new ADA_LatestWarn(mContext);
        mAdapterLatestDynamic = new ADA_LatestDynamic(mContext);

        mBranchAdapter = new ADA_BranchList(mContext);
        listview.setAdapter(mBranchAdapter);

        sharePre = new AppSharedPreferences(this);

        modifyRingChartView();

        //联网获取数据
        requestNet();
    }

    private void modifyRingChartView() {

        if(Constants.TYPE_PAD_MODEL.equals(Build.MODEL)) {
            mMattressOnlineView.setCommonSize(30, 55, 45, 35);
            mChartViewDataAblityView.setCommonSize(30, 55, 45, 35);
            mChartviewEmployeeView.setCommonSize(30, 55, 45, 35);
            nurseLevelChartView.setCommonSize(30, 55, 45, 35);
            bodyExamChartView.setCommonSize(30, 55, 45);

            mMattressOnlineView.setShowRateSize(10);
            mChartViewDataAblityView.setShowRateSize(10);
            mChartviewEmployeeView.setShowRateSize(10);
            nurseLevelChartView.setShowRateSize(10);
        }
    }

    private void initChartIndicator() {

        //初始化 入住用户 颜色指示列表
        mUserInIndicatorList = Arrays.asList(mContext.getResources().getStringArray(R.array.arr_age_indicator));
        ADA_ChartIndicator userInAdapter = new ADA_ChartIndicator(mUserInIndicatorList, initDrawableList(Constants.LivingUserData));
        userInIndicatorView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.HORIZONTAL, false, true));
        userInIndicatorView.setAdapter(userInAdapter);

        //员工统计
        mWorkerIndicatorList = Arrays.asList(mContext.getResources().getStringArray(R.array.arr_worker_indicator));
        ADA_ChartIndicator workerAdapter = new ADA_ChartIndicator(mWorkerIndicatorList, initDrawableList(Constants.StaffData));
        workerIndicatorView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, false, false));
        workerIndicatorView.setAdapter(workerAdapter);

        //
        mMattressIndicatorList = Arrays.asList(mContext.getResources().getStringArray(R.array.arr_mattress_indicator));
        ADA_ChartIndicator mattressAdapter = new ADA_ChartIndicator(mMattressIndicatorList, initDrawableList(Constants.MattressNew));
        mattressIndicatorView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, false, false));
        mattressIndicatorView.setAdapter(mattressAdapter);

//        mWatchIndicatorList = Arrays.asList(mContext.getResources().getStringArray(R.array.arr_watch_indicator));
//        ADA_ChartIndicator watchAdapter = new ADA_ChartIndicator(mWatchIndicatorList, initDrawableList(Constants.BraceletNew));
//        watchIndicatorView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, false, false));
//        watchIndicatorView.setAdapter(watchAdapter);

        //
        mAbilityIndicatorList = Arrays.asList(mContext.getResources().getStringArray(R.array.arr_exam_indicator));
        mExamIndicatorList = Arrays.asList(mContext.getResources().getStringArray(R.array.arr_exam_indicator));
        ADA_ChartIndicator examAdapter = new ADA_ChartIndicator(mExamIndicatorList, initDrawableList(Constants.UserHealthDataNum));
        examIndicatorView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, true, false));
        examIndicatorView.setAdapter(examAdapter);
    }

    private List<Drawable> initDrawableList(int type) {

        List<Drawable> list = new ArrayList<>();

        switch (type){
            case 1:                                                             //入住用户
            case 7:                                                             //当日体检
            case 8:                                                             //自理能力
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_1_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_2_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_3_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_4_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_5_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_6_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_7_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_8_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_9_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_10_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_11_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_12_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_13_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_0_corners));
                break;
            case 2:                                                             //员工统计
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_1_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_2_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_6_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_4_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_5_corners));
                break;
            case 3:                                                             //护理级别
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_1_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_2_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_3_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_4_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_5_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_6_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_7_corners));
                break;
            case 4:                                                             //床垫在线
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_2_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_5_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_7_corners));
                break;
            case 5:                                                             //手表在线
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_2_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_7_corners));
                break;
            case 6:                                                             //报警数据
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_5_corners));
                list.add(mContext.getResources().getDrawable(R.drawable.bg_view_7_corners));
                break;
            default: break;
        }

        return list;
    }


    private void updateNurseLevelIndicator(List<String> dataList) {
        // 护理级别指示更新
        ADA_ChartIndicator nurseLevAdapter = new ADA_ChartIndicator(dataList, initDrawableList(Constants.UserNurseData));
        nurseLevelIndicatorView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, false, false));
        nurseLevelIndicatorView.setAdapter(nurseLevAdapter);
    }

    private void updateAbilityIndicator(List<String>  dataList){
        ADA_ChartIndicator abilityAdapter = new ADA_ChartIndicator(dataList, initDrawableList(Constants.AbilityDataNum));
        abilityIndicatorView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, false, false));
        abilityIndicatorView.setAdapter(abilityAdapter);
    }

    private LinearLayoutManager getScrollableLayoutManager(int orientation, final boolean scrollVertically, final boolean scrollHorizontally) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext){

            @Override
            public boolean canScrollVertically() {
                return scrollVertically;
            }

            @Override
            public boolean canScrollHorizontally() {
                return scrollHorizontally;
            }
        };
        layoutManager.setOrientation(orientation);
        return layoutManager;
    }

    private void showView() {
        if (Constants.TYPE.equals("总院")) {
            tvTitle.setText(MyApplication.getContext().getString(R.string.string_home_title_zong));
        } else if (Constants.TYPE.equals("分院")) {
            tvTitle.setText(Constants.BRANCH_NAME + "智慧养老大数据监控平台");
        }

        //2018年6月13日 [TextView个别字体样式设置](https://blog.csdn.net/bowoolz/article/details/77418262)
        String timeStamp = AppDateUtil.getTimeStamp(System.currentTimeMillis(), AppDateUtil.YYYY_MM_DD_POINT);
        String week = AppDateUtil.getWeek(AppDateUtil.getTimeStamp(System.currentTimeMillis(), AppDateUtil.YYYY_MM_DD));

        SpannableString spanString = new SpannableString(timeStamp + "    " + week);
        ForegroundColorSpan span1 = new ForegroundColorSpan(Color.WHITE);
        ForegroundColorSpan span2 = new ForegroundColorSpan(Color.WHITE);
        ForegroundColorSpan span3 = new ForegroundColorSpan(Color.WHITE);
        ForegroundColorSpan span4 = new ForegroundColorSpan(Color.WHITE);
        spanString.setSpan(span1, 4, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spanString.setSpan(span2, 7, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spanString.setSpan(span3, 10, 11, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spanString.setSpan(span4, 15, 17, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvDate.setText(spanString);
    }

    /**
     * 初始化主页菜单
     */
    private void initPopupWindow() {
        for (int i = 0; i < memuTitle.length; i++) {
            HomeMenuEntity itemMenu = new HomeMenuEntity(memuIcons[i], memuTitle[i]);
            menuList.add(itemMenu);
        }
        mPopupWindow = new CommonPopupWindow(this, R.layout.popup_home_menu, 192, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                BanSlideListView listview = (BanSlideListView) view.findViewById(R.id.listview);
                ADA_HomeMenu adapter = new ADA_HomeMenu(mContext);
                listview.setAdapter(adapter);
                adapter.update(menuList, true);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 1) {
                            readyGo(ACT_NursingProgress.class);
                        }
                        mPopupWindow.mInstance.dismiss();
                    }
                });
            }

            @Override
            protected void initEvent() {
            }
        };
        mPopuplayoutGravity = new CommonPopupWindow.LayoutGravity(
                CommonPopupWindow.LayoutGravity.ALIGN_ABOVE | CommonPopupWindow.LayoutGravity.TO_RIGHT);
    }

    private void doLogout(){
        //存储用户信息
        sharePre.remove(SharePreConstants.USER_SIGN);
        sharePre.remove(SharePreConstants.USER_ID);
        sharePre.remove(SharePreConstants.USER_NAME);
        sharePre.remove(SharePreConstants.TYPE);
        sharePre.remove(SharePreConstants.BRANCH_NAME);
        sharePre.putBoolean(SharePreConstants.LOGOUT, true);
        Constants.USER_ID = null;
        Constants.USER_SIGN = null;
        Constants.TYPE = null;
        Constants.BRANCH_NAME = null;
        MyApplication.isLogin = false;
    }


    /**
     * 联网获取数据
     */
    private void requestNet() {
        mPresenter = new HomePresenter(this);
        //1. 入住养老院数等
        mPresenter.getHeaderData("branchNum");
        //2. 入住用户
        mPresenter.getLivingUserData("user");
        //3. 员工统计
        mPresenter.getStaffData("staff");
        //4. 护理级别
        mPresenter.getUserNurseData("userByNurse");
        //5.智能设备
//        mPresenter.getBraceletNew("braceletNew");
        mPresenter.getMattressNew("mattressNew");
        //6. 自理能力
//        mPresenter.getAlertData("warning");
        mPresenter.getAbilityNum("abilityNum");
        //7. 当日体检--近七天
        mPresenter.getUserHealthDataNum("userHealthDataNum");
        //8.分院地址
        mPresenter.getBranchAddress("branchList");
        //9.分院护理进度
        mPresenter.getNurseProgressList("jobItem");
        //老人警报
        mPresenter.getUserWarning6("userWarning6");
        //院内动态
//        mPresenter.getAfficheNew("afficheNew");
        mPresenter.getParty("party");
    }

    private void refreshNet() {
        mPresenter.getMattressNew("mattressNew");
        mPresenter.getNurseProgressList("jobItem");
        mPresenter.getUserWarning6("userWarning6");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getParty("party");
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @OnClick({R.id.iv_menu, R.id.iv_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                mPopupWindow.showBashOfAnchor(ivMenu, mPopuplayoutGravity, 0, 0);
                break;
            case R.id.iv_user:
                showRightMenuView();
                break;
        }
    }

    private void showRightMenuView(){

        HomeMenuEntity logoutMenu = new HomeMenuEntity(R.drawable.ic_menu_logout, getResources().getString(R.string.right_menu_logout));
        final List<HomeMenuEntity> rightMenu = new ArrayList<>();
        rightMenu.add(logoutMenu);
        CommonPopupWindow.LayoutGravity rightPopuplayoutGravity = new CommonPopupWindow.LayoutGravity(
                CommonPopupWindow.LayoutGravity.ALIGN_BOTTOM | CommonPopupWindow.LayoutGravity.TO_LEFT);
        CommonPopupWindow logoutPopWindow = new CommonPopupWindow(this, R.layout.popup_home_menu, 192, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                BanSlideListView logoutView = (BanSlideListView) view.findViewById(R.id.listview);
                ADA_HomeMenu adapter = new ADA_HomeMenu(mContext);
                logoutView.setAdapter(adapter);
                adapter.update(rightMenu, true);
                logoutView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        doLogout();
                        mPopupWindow.mInstance.dismiss();
                        readyGo(ACT_Login.class);
                        finish();
                    }
                });
            }

            @Override
            protected void initEvent() {

            }
        };
        logoutPopWindow.showBashOfAnchor(mIvUser, rightPopuplayoutGravity, 0, 0);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }


    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
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

    /**
     * 1、入住养老院数据等
     *
     * @param bean
     */
    @Override
    public void getHeaderData(HomeHeadEntity bean) {
        if (bean != null) {
            //用户数
            tvRestHomeNum.setText(bean.getNumUser() + "");
            numberUser = bean.getNumUser();
            //床位总数
            tvUserNum.setText(bean.getNumBed() + "");
            //智能设备
            tvBedroomNum.setText(bean.getMattressNA() + "");
            //使用率
            if(bean.getMattressNA() != 0){
                tvLiveRate.setText(StringUtil.getFormatPercentRate(bean.getNumUser() * 1f / bean.getNumBed() * 100));
            }
        }
    }

    /**
     * 2、入住用户
     *
     * @param dataList
     */
    @Override
    public void getLivingUserData(List<ChartCommonEntity> dataList) {
        //处理集合数据——由于fastjson解析数据，有默认排序，与预期的不符合
        if (dataList != null && dataList.size() > 0) {
            //{"100岁以上":0,"60-69岁":5,"60以下":4,"70-79岁":18,"80-89岁":6,"90-99岁":0,"其他":4}
            dataList.remove(dataList.size() - 1);
            dataList.add(dataList.size() - 1, dataList.get(0));
            dataList.remove(0);
            int underSixty = dataList.get(1).getValue();
            int six2sev = dataList.get(0).getValue();
            dataList.get(1).setValue(underSixty + six2sev);
            dataList.remove(0);
        }
        //添加色块
        List<Integer> userLivingChartcolorList = new ArrayList<>();
        int[] colors = mContext.getResources().getIntArray(R.array.color_group);
        for (int i = 0; i < colors.length; i++) {
            userLivingChartcolorList.add(colors[i]);
        }

        //设置显示的文字
        List<BarChartEntity> barChartList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartCommonEntity bean = dataList.get(i);
            barChartList.add(bean);
        }
        mChartviewUserLivingView.setYAxisTextList(CommonUtil.formatArithmeticSequence(numberUser, 5));
        mChartviewUserLivingView.updateData(barChartList);

    }

    /**
     * 3、员工统计
     */
    @Override
    public void getStaffData(List<StaffEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        List<Integer> employeeChartcolorList = new ArrayList<>();
        int[] colors = {R.color.type_color_1, R.color.type_color_2, R.color.type_color_6,
                R.color.type_color_4, R.color.type_color_5};
        int totalCount = 0;
        List<String> showTextList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            StaffEntity bean = dataList.get(i);
            totalCount += bean.getNum();
            if (bean.getWorkType().equals(MyApplication.getContext().getString(R.string.string_huliyuan))) {
                employeeChartcolorList.add(colors[0]);
                showTextList.add(bean.getWorkType());
            }
            if (bean.getWorkType().equals(MyApplication.getContext().getString(R.string.string_hushi))) {
                employeeChartcolorList.add(colors[1]);
                showTextList.add(bean.getWorkType());
            }
            if (bean.getWorkType().equals(MyApplication.getContext().getString(R.string.string_yishi))) {
                employeeChartcolorList.add(colors[2]);
                showTextList.add(bean.getWorkType());
            }
            if (bean.getWorkType().equals(MyApplication.getContext().getString(R.string.string_guanlirenyuan))) {
                employeeChartcolorList.add(colors[3]);
                showTextList.add(bean.getWorkType());
            }
            if (bean.getWorkType().equals(MyApplication.getContext().getString(R.string.string_gongqinrenyuan))) {
                employeeChartcolorList.add(colors[4]);
                showTextList.add(bean.getWorkType());
            }
        }

        // 百分比
        List<Float> employeeChartRateList = new ArrayList<>();
        //添加百分比
        for (int i = 0; i < dataList.size(); i++) {
            StaffEntity bean = dataList.get(i);
            employeeChartRateList.add(Float.parseFloat(StringUtil.getFormatPercentRate(bean.getNum() * 1f / totalCount * 100)));
        }
        mChartviewEmployeeView.setShow(employeeChartcolorList, employeeChartRateList, true, true);
        mChartviewEmployeeView.setShowTextList(showTextList);
    }

    /**
     * 4、护理级别
     *
     * @param dataList
     */
    @Override
    public void getUserNurseData(List<NurseLevelEntity> dataList) {

        if (dataList == null || dataList.size() == 0) {
            return;
        }

        // 添加的颜色
        List<Integer> userLivingChartcolorList = new ArrayList<>();
        userLivingChartcolorList.add(R.color.type_color_1);
        userLivingChartcolorList.add(R.color.type_color_2);
        userLivingChartcolorList.add(R.color.type_color_3);
        userLivingChartcolorList.add(R.color.type_color_4);
        userLivingChartcolorList.add(R.color.type_color_5);
        userLivingChartcolorList.add(R.color.type_color_6);
        userLivingChartcolorList.add(R.color.type_color_7);
        userLivingChartcolorList.add(R.color.type_color_8);

        List<Float> userLivingChartRateList = new ArrayList<>();
//        List<String> nameList = new ArrayList<>();
        List<String> showTextList = new ArrayList<>();

        //总人数
        int allNum = 0;
//        List<NurseLevelEntity> downList = new ArrayList<>();
        for(int i = 0; i < dataList.size(); ++i) {
            NurseLevelEntity entity = dataList.get(i);
            allNum += entity.getNum();
        }

        float numRate = 0f;
        for (int i = 0; i < dataList.size(); i++) {
            NurseLevelEntity entity = dataList.get(i);

            //添加百分比
            if(allNum !=0){
                numRate = (float) (entity.getNum() * 1.0 / allNum * 100);
                String formatNumRate = StringUtil.getFormatPercentRate(numRate);//format 返回的是字符串
                userLivingChartRateList.add(Float.parseFloat(formatNumRate));
                showTextList.add(entity.getName());
            }
        }

        nurseLevelChartView.setShow(userLivingChartcolorList, userLivingChartRateList, true, true);
        nurseLevelChartView.setShowTextList(showTextList);
//        nurseLevelChartView.updateData(charList);
        updateNurseLevelIndicator(showTextList);
    }

    /**
     * 5、智能设备在线情况
     */
//    @Override
//    public void getBraceletNew(List<EquipmentStatusEntity> dataList) {
//
//        // 添加的颜色
//        List<Integer> watchChartColorList = new ArrayList<>();
//        watchChartColorList.add(R.color.type_color_2);
//        watchChartColorList.add(R.color.type_color_7);
//
//        //  添加的是百分比
//        List<Float> watchChartRateList = new ArrayList<>();
//        List<String> showTextList = new ArrayList<>();
//        float onlineRate = 0f, offlineRate = 0f;
//        int onlineNum = 0, offlineNum = 0, allNum = 0;
//        for(EquipmentStatusEntity entity : dataList){
//            allNum += entity.getNum();
//            if(entity.getStatus().equals(mContext.getString(R.string.string_online))){
//                onlineNum = entity.getNum();
//            }
//            if(entity.getStatus().equals(mContext.getString(R.string.string_offline))){
//                offlineNum = entity.getNum();
//            }
//        }
//
//        if(allNum !=0){
////            onlineRate = (float) (onlineNum * 1.0 / allNum * 100);
//            offlineRate = (float) (offlineNum * 1.0 / allNum * 100);
//        }
//
//        String formatOfflineRate = StringUtil.getFormatPercentRate(offlineRate);//format 返回的是字符串
//        onlineRate = (100 - Float.parseFloat(formatOfflineRate));
//        String formatOnlineRate = StringUtil.getFormatPercentRate(onlineRate);
//
//        //添加百分比
//        watchChartRateList.add(Float.parseFloat(formatOnlineRate));
//        watchChartRateList.add(Float.parseFloat(formatOfflineRate));
//
//        //设置显示的text
//        showTextList.add(
//                Float.parseFloat(formatOnlineRate) + MyApplication.getContext().getString(R.string.string_percent_symbol));
//        showTextList.add(
//                Float.parseFloat(formatOfflineRate) + MyApplication.getContext().getString(R.string.string_percent_symbol));
//
//        mWatchOnlineChartView.setCommonSize(50, 80, 30, 20);
//        mWatchOnlineChartView.setShow(watchChartColorList, watchChartRateList, true, true);
//        mWatchOnlineChartView.setShowTextList(showTextList);
//    }

    @Override
    public void getMattressNew(List<EquipmentStatusEntity> dataList) {

        if(null == dataList || dataList.size() == 0){
            return;
        }

        // 添加的颜色
        List<Integer> mattressChartColorList = new ArrayList<>();
        mattressChartColorList.add(R.color.type_color_2);
        mattressChartColorList.add(R.color.type_color_5);
        mattressChartColorList.add(R.color.type_color_7);

        //  添加的是百分比
        List<Float> mattressChartRateList = new ArrayList<>();
        List<String> showTextList = new ArrayList<>();
        float offBedNumRate = 0f, offlineRate = 0f, onBedRate;
        int offBedNum = 0, offlineNum = 0, allNum = 0;
        for(EquipmentStatusEntity entity : dataList){
            allNum += entity.getNum();
            if(entity.getStatus().equals(mContext.getString(R.string.string_off_bed))){
                offBedNum = entity.getNum();
            }
            if(entity.getStatus().equals(mContext.getString(R.string.string_off_line))){
                offlineNum = entity.getNum();
            }
        }

        if(allNum !=0){
            offBedNumRate = (float) (offBedNum * 1.0 / allNum * 100);
            offlineRate = (float) (offlineNum * 1.0 / allNum * 100);
        }

        String formatOffBedRate = StringUtil.getFormatPercentRate(offBedNumRate);//format 返回的是字符串
        String formatOfflineRate = StringUtil.getFormatPercentRate(offlineRate);//format 返回的是字符串

        onBedRate = (100 - Float.parseFloat(formatOffBedRate) - Float.parseFloat(formatOfflineRate));
        String formatOnBedRate = StringUtil.getFormatPercentRate(onBedRate);

        //添加百分比
        mattressChartRateList.add(Float.parseFloat(formatOfflineRate));
        mattressChartRateList.add(Float.parseFloat(formatOffBedRate));
        mattressChartRateList.add(Float.parseFloat(formatOnBedRate));

        //设置显示的text
        showTextList.add(mContext.getString(R.string.string_offline));
        showTextList.add(mContext.getString(R.string.string_off_bed));
        showTextList.add(mContext.getString(R.string.string_on_bed));

//        mMattressOnlineView.setCommonSize(50, 80, 30, 20);
        mMattressOnlineView.setShow(mattressChartColorList, mattressChartRateList, true, true);
        mMattressOnlineView.setShowTextList(showTextList);
    }

    /**
     * 6、报警数据
     */
    @Override
    public void getAlertData(WarningEntity bean) {

//        // 添加的颜色
//        List<Integer> alertChartcolorList = new ArrayList<>();
//        //未处理
//        alertChartcolorList.add(R.color.color_2b84b9);
//        alertChartcolorList.add(R.color.color_f1b133);
//
//        //  添加的是百分比
//        List<Float> alertChartRateList = new ArrayList<>();
//        List<String> showTextList = new ArrayList<>();
//
//        float handledRate = (float) (bean.getNumY() * 1.0 / bean.getNumA() * 100);
//        String formatHandledRate = StringUtil.getFormatPercentRate(handledRate);//format 返回的是字符串
//
//        float unHandledRate = (100 - Float.parseFloat(formatHandledRate));
//        String formatUnHandledRate = StringUtil.getFormatPercentRate(unHandledRate);
//        //添加百分比
//        alertChartRateList.add(Float.parseFloat(formatHandledRate));
//        alertChartRateList.add(Float.parseFloat(formatUnHandledRate));
//        //设置显示的text
//        showTextList.add(MyApplication.getContext().getString(R.string.string_handled) + "\r\n" + Float.parseFloat(formatHandledRate) + MyApplication.getContext().getString(R.string.string_percent_symbol));
//        showTextList.add(MyApplication.getContext().getString(R.string.string_unhandled) + "\r\n" + Float.parseFloat(formatUnHandledRate) + MyApplication.getContext().getString(R.string.string_percent_symbol));
//        mChartviewDataAlertView.setShow(alertChartcolorList, alertChartRateList, true, true);
//        mChartviewDataAlertView.setShowTextList(showTextList);
    }

    @Override
    public void getUserHealthDataNum(UserHealthDataNum data) {

        String[] arrText = new String[]{"0","30","60","90","120","150"};
        List<Integer> dataList = new ArrayList<>();

        if(null == data){
            return;
        }

        dataList.add(data.getSbp());
        dataList.add(data.getSpo2());
        dataList.add(data.getPr2());
        dataList.add(data.getHr());
        dataList.add(data.getResp_rr());
        dataList.add(data.getGlu());
        dataList.add(data.getTemp());
        dataList.add(data.getFlipidsChol());
        dataList.add(data.getFlipidsTrig());
        dataList.add(data.getFlipidsHdl());
        dataList.add(data.getFlipidsLDL());
        dataList.add(data.getAssxhdb());
        dataList.add(data.getHtc());

//        int sum = data.getSbp() + data.getDbp() + data.getSpo2() + data.getPr2() + data.getHr()
//                + data.getResp_rr() + data.getGlu() + data.getTemp() + data.getFlipidsChol()
//                + data.getFlipidsTrig() + data.getFlipidsHdl() + data.getFlipidsLDL() + data.getAssxhdb()
//                + data.getHtc();
        int sum = 100;

        bodyExamChartView.setShowTextList(Arrays.asList(arrText));
        bodyExamChartView.setDataList(dataList);
        if(sum != 0){
            noDataView.setVisibility(View.GONE);
            bodyExamChartView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 8.分院地址
     *
     * @param dataList
     */
    @Override
    public void getBranchAddress(List<BranchAddressEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        //暂时地图上只标记上海地区
        for (int i = 0; i < dataList.size(); i++) {
            BranchAddressEntity bean = dataList.get(i);
            if (bean.getCityName().equals("上海")) {
                areaList.add(bean);
                //重复的areaName,只显示一个。
                areaPointMap.put(bean.getAreaId() + "", bean.getAreaName());
            }
        }
        //最多显示五条数据
        if (areaList.size() > 4) {
            mBranchAdapter.update(areaList.subList(0, 4), true);
        } else {
            mBranchAdapter.update(areaList, true);
        }
        initRedPoint();
    }

    /**
     * 处理总院账号登录时上海地区养老院地图上的红点显示
     */
    private void initRedPoint() {
        List<String> areaPointList = new ArrayList<>();
        Set<String> keys = areaPointMap.keySet();
        for (String key : keys) {
            areaPointList.add(areaPointMap.get(key));
        }
        for (int i = 0; i < areaPointList.size(); i++) {
            View point = LayoutInflater.from(mContext).inflate(R.layout.redpoint_postion, null);
            TextView tvAreaName = (TextView) point.findViewById(R.id.tv_areaName);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(30, 30);
            params.leftMargin = leftMarginArray[i];
            params.topMargin = topMarginArray[i];
            point.setLayoutParams(params);
            tvAreaName.setText(areaPointList.get(i));
            redPoint.addView(point);
        }

    }

    /**
     * 护理总项目
     * @param dataList
     */
    @Override
    public void getNurseProgressList(List<JobItemEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        //Merge the same type data
        Map<String, Integer> mergeDataMap = new HashMap<>();
        List<JobItemEntity> mergeDataList = new ArrayList<>();
        for(int i = 0; i < dataList.size(); ++i) {
            JobItemEntity entity = dataList.get(i);
            String currentName = entity.getItemName();

            if(mergeDataMap.size() > 0){
                Set<String> nameSet = mergeDataMap.keySet();
                if(nameSet.contains(currentName)) {
                    JobItemEntity exitItem = mergeDataList.get(mergeDataMap.get(currentName));
                    exitItem.setNumA(exitItem.getNumA() + entity.getNumA());
                    exitItem.setNumD(exitItem.getNumD() + entity.getNumD());
                    mergeDataMap.put(exitItem.getItemName(), mergeDataMap.get(currentName));
                    mergeDataList.set(mergeDataMap.get(currentName), exitItem);
                }
                else {
                    mergeDataList.add(entity);
                    mergeDataMap.put(entity.getItemName(), mergeDataList.size() - 1);
                }
            }
            else {
                mergeDataMap.put(entity.getItemName(), 0);
                mergeDataList.add(entity);
            }
        }
        mAdapterNurseProgress.update(mergeDataList, true);
        listViewNurseProgress.setAdapter(mAdapterNurseProgress);
    }

    @Override
    public void getParty(List<LatestDynamicEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        latestDynamicList = dataList;
        mAdapterLatestDynamic.updateList(dataList);
        mAdapterLatestDynamic.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra("party_id", latestDynamicList.get(position).getId());
                intent.putExtra("party_name", latestDynamicList.get(position).getName());
                intent.putExtra("party_time", latestDynamicList.get(position).getPartyStart());
                readyGo(ACT_HomeDynamic.class, intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        homeDynamicListView.setAdapter(mAdapterLatestDynamic);
        homeDynamicListView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, true, false));
    }

    /**
     * 老人警报
     * @param dataList
     */
    @Override
    public void getUserWarning6(List<LatestWarnEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        mAdapterLatestWarn.updateList(dataList);
        elderlyWarnListView.setAdapter(mAdapterLatestWarn);
        elderlyWarnListView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, true, false));
        elderlyWarnListView.setVisibility(View.VISIBLE);
        noWarnDataView.setVisibility(View.GONE);
    }

    @Override
    public void getAfficheNew(List<LatestDynamicEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
           return;
        }
        mAdapterLatestDynamic.updateList(dataList);
        homeDynamicListView.setAdapter(mAdapterLatestDynamic);
        homeDynamicListView.setLayoutManager(getScrollableLayoutManager(LinearLayoutManager.VERTICAL, false, false));
    }

    @Override
    public void getAbilityNum(List<AbilityNumEntity> result) {

        if(null == result || result.size() < 1){
            return;
        }
        // 添加颜色
        List<Integer> abilityChartColorList = new ArrayList<>();
        abilityChartColorList.add(R.color.type_color_1);
        abilityChartColorList.add(R.color.type_color_2);
        abilityChartColorList.add(R.color.type_color_3);
        abilityChartColorList.add(R.color.type_color_4);
        abilityChartColorList.add(R.color.type_color_5);
        abilityChartColorList.add(R.color.type_color_6);
        abilityChartColorList.add(R.color.type_color_7);
        abilityChartColorList.add(R.color.type_color_8);
        abilityChartColorList.add(R.color.type_color_9);
        abilityChartColorList.add(R.color.type_color_10);
        abilityChartColorList.add(R.color.type_color_11);
        abilityChartColorList.add(R.color.type_color_12);
        abilityChartColorList.add(R.color.type_color_13);
        abilityChartColorList.add(R.color.type_color_0);

        //  添加百分比
        List<Float> abilityChartRateList = new ArrayList<>();
        List<String> showTextList = new ArrayList<>();
//        List<String> indicatorList = new ArrayList<>();

        int totalNum = 0;
        for(int i = 0; i < result.size(); ++i) {
//        for(int i = 0; i < 4; ++i) {
            totalNum += result.get(i).getNum();
        }

        if(totalNum == 0) return;

        for(int i = 0; i < result.size(); ++i) {
//        for(int i = 0; i < 4; ++i) {
            AbilityNumEntity bean = result.get(i);
            float beanRate = (float) (bean.getNum() * 1.0 / totalNum * 100);
            String formatBeanRate = StringUtil.getFormatPercentRate(beanRate);
            abilityChartRateList.add(Float.parseFloat(formatBeanRate));
            if(bean.getAbilityId().equals("0")){
                showTextList.add("其他");
            }
            else {
                showTextList.add(bean.getAbilityName());
            }
        }

        updateAbilityIndicator(showTextList);
        mChartViewDataAblityView.setShow(abilityChartColorList, abilityChartRateList, true, true);
        mChartViewDataAblityView.setShowTextList(showTextList);
    }
}
