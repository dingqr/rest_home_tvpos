package com.smart.tvpos.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.ReceiveConstants;
import com.smart.framework.library.common.utils.AppDateUtil;
import com.smart.framework.library.common.utils.StringUtil;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.adapter.ADA_BranchList;
import com.smart.tvpos.adapter.ADA_CircleNurseProgress;
import com.smart.tvpos.adapter.ADA_HomeMenu;
import com.smart.tvpos.adapter.ADA_NurseLevel;
import com.smart.tvpos.bean.AdmitLivingEntity;
import com.smart.tvpos.bean.BranchAddressEntity;
import com.smart.tvpos.bean.ChartCommonEntity;
import com.smart.tvpos.bean.HomeHeadEntity;
import com.smart.tvpos.bean.HomeMenuEntity;
import com.smart.tvpos.bean.JobItemEntity;
import com.smart.tvpos.bean.NurseLevelEntity;
import com.smart.tvpos.bean.StaffEntity;
import com.smart.tvpos.bean.TrendDataEntity;
import com.smart.tvpos.bean.WarningEntity;
import com.smart.tvpos.mvp.HomePresenter;
import com.smart.tvpos.mvp.IHomeView;
import com.smart.tvpos.util.Constants;
import com.smart.tvpos.widgets.BanSlideGridView;
import com.smart.tvpos.widgets.BanSlideListView;
import com.smart.tvpos.widgets.CommonPopupWindow;
import com.smart.tvpos.widgets.DownCurveChartView;
import com.smart.tvpos.widgets.PannelChartView;
import com.smart.tvpos.widgets.RingChartView;
import com.smart.tvpos.widgets.UpCurveChartView;

import java.util.ArrayList;
import java.util.Collections;
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
    @Bind(R.id.pannelChartView)
    PannelChartView mPannelChartView;
    @Bind(R.id.alertchartview)
    RingChartView mChartviewDataAlertView;
    @Bind(R.id.userlivingchartview)
    RingChartView mChartviewUserLivingView;
    @Bind(R.id.nurselevelchartview)
    RingChartView chartviewNurselevel;
    @Bind(R.id.employeechartview)
    RingChartView mChartviewEmployeeView;
    @Bind(R.id.curveViewUp)
    UpCurveChartView mUpCurveView;
    @Bind(R.id.curveViewDown)
    DownCurveChartView mDowncurveView;
    @Bind(R.id.gridview)
    BanSlideGridView gridviewNurseProgress;
    private CommonPopupWindow mPopupWindow;
    private CommonPopupWindow.LayoutGravity mPopuplayoutGravity;
    private List<HomeMenuEntity> menuList = new ArrayList();
    //        private String[] memuTitle = {"数据监控", "概览", "护理进度"};
    private String[] memuTitle = {"数据监控", "护理进度"};
    //        private int[] memuIcons = {R.drawable.ic_data_watching, R.drawable.ic_genneral_view, R.drawable.ic_nurse_progress};
    private int[] memuIcons = {R.drawable.ic_data_watching, R.drawable.ic_nurse_progress};
    private HomePresenter mPresenter;
    private ADA_CircleNurseProgress mAdapterNurseProgress;
    private int[] upYAxisData;
    private int[] downYAxisData;
    //暂时处理地图展示
    @Bind(R.id.red_point)
    RelativeLayout redPoint;
    @Bind(R.id.listview)
    BanSlideListView listview;
    @Bind(R.id.gridv)
    BanSlideGridView gridv;
    private int[] leftMarginArray = new int[]{125, 200 + 5 * 1, (140 + 5 * 1 + 40), 30, 160, 10 + 5 * 1, 48 + 5 * 1, 105 + 5 * 2, 115 + 5 * 1, 66 + 5 * 3, 150 + 5 * 1, 58, 0 + 5 * 2, 38 + 5 * 2, 66 + 5 * 2, 76 + 5 * 1, 140 + 5 * 2, (140 + 5 * 2 + 40), (140 + 5 * 2 + 40 * 2), 5 + 5 * 3, 38 + 5 * 3, 86, 105 + 5 * 3, 100 + 5 * 3};
    private int[] topMarginArray = new int[]{16, 2 + 40 * 1, (2 + 50 * 1 + 40), 20, 16, 16 + 50 * 1, 0 + 50 * 1, 14 + 50 * 1, 16 + 50 * 2, 14 + 50 * 3, 2 + 50 * 1, 0, 16 + 50 * 2, 0 + 50 * 2, 14 + 50 * 2, 16 + 50 * 1, 2 + 50 * 2, (2 + 50 * 2 + 50), (2 + 50 * 2 + 50), 50 * 3, 0 + 50 * 3, 14, 16 + 50 * 3, 2 + 50 * 2};
    //上海地区所有的养老院
    private List<BranchAddressEntity> areaList = new ArrayList<>();
    private Map<String, String> areaPointMap = new HashMap<>();
    private ADA_BranchList mBranchAdapter;
    private ADA_NurseLevel mNurseLevelAdapter;
    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.REFRESH_CURRENT_PAGE) {
            requestNet();
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

        initChartView();

        //联网获取数据
        requestNet();

        mAdapterNurseProgress = new ADA_CircleNurseProgress(mContext);
        gridviewNurseProgress.setAdapter(mAdapterNurseProgress);

        mBranchAdapter = new ADA_BranchList(mContext);
        listview.setAdapter(mBranchAdapter);
        //护理级别Adapter
        mNurseLevelAdapter = new ADA_NurseLevel(mContext);
        gridv.setAdapter(mNurseLevelAdapter);
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
            HomeMenuEntity itemMenu = new HomeMenuEntity();
            itemMenu.menuIcon = memuIcons[i];
            itemMenu.menuTxt = memuTitle[i];
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
        mPopuplayoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_ABOVE | CommonPopupWindow.LayoutGravity.TO_RIGHT);
    }

    /**
     * 初始化图表
     */
    private void initChartView() {
        //接待入住
//        int[] valueDatas = {50, 10, 80};
//        mPannelChartView.setValueData(valueDatas);

        //入住用户
        // 添加的颜色
//        List<Integer> userLivingChartcolorList = new ArrayList<>();
//        //未处理
//        userLivingChartcolorList.add(R.color.color_34617e);
//        userLivingChartcolorList.add(R.color.color_f1b133);
//        userLivingChartcolorList.add(R.color.color_2e84ba);
//        userLivingChartcolorList.add(R.color.color_55c7f2);
//        userLivingChartcolorList.add(R.color.color_5ffefd);
//        userLivingChartcolorList.add(R.color.color_e36853);
//        userLivingChartcolorList.add(R.color.color_7c80fe);
//
//        //  添加的是百分比
//        List<Float> userLivingChartRateList = new ArrayList<>();
//        userLivingChartRateList.add(5f);
//        userLivingChartRateList.add(10f);
//        userLivingChartRateList.add(20f);
//        userLivingChartRateList.add(15f);
//        userLivingChartRateList.add(30f);
//        userLivingChartRateList.add(10f);
//        userLivingChartRateList.add(10f);
//        chartviewUserLiving.setShow(userLivingChartcolorList, userLivingChartRateList, true, true);

        //报警数据
//        添加的颜色
//        List<Integer> alertChartcolorList = new ArrayList<>();
//        //未处理
//        alertChartcolorList.add(R.color.color_f1b133);
//        alertChartcolorList.add(R.color.color_2b84b9);
//
//        //  添加的是百分比
//        List<Float> alertChartRateList = new ArrayList<>();
//        alertChartRateList.add(32f);
//        alertChartRateList.add(68f);
//        chartviewDataAlert.setShow(alertChartcolorList, alertChartRateList, true, true);

        // 护理级别
        // 添加的颜色
//        List<Integer> nurseLevelChartcolorList = new ArrayList<>();
//        nurseLevelChartcolorList.add(R.color.color_55c7f2);
//        nurseLevelChartcolorList.add(R.color.color_5ffefd);
//        nurseLevelChartcolorList.add(R.color.color_e36853);
//        nurseLevelChartcolorList.add(R.color.color_7c80fe);
//
//        //  添加的是百分比
//        List<Float> nurseLevelChartRateList = new ArrayList<>();
//        nurseLevelChartRateList.add(40f);
//        nurseLevelChartRateList.add(10f);
//        nurseLevelChartRateList.add(5f);
//        nurseLevelChartRateList.add(45f);
//        chartviewNurselevel.setShow(nurseLevelChartcolorList, nurseLevelChartRateList, true, true);
        //员工统计
        // 添加的颜色
//        List<Integer> employeeChartcolorList = new ArrayList<>();
//        employeeChartcolorList.add(R.color.color_55c7f2);
//        employeeChartcolorList.add(R.color.color_5ffefd);
//        employeeChartcolorList.add(R.color.color_34617e);
//        employeeChartcolorList.add(R.color.color_7c80fe);
//        employeeChartcolorList.add(R.color.color_f1b133);
//        //  添加的是百分比
//        List<Float> employeeChartRateList = new ArrayList<>();
//        employeeChartRateList.add(40f);
//        employeeChartRateList.add(20f);
//        employeeChartRateList.add(5f);
//        employeeChartRateList.add(20f);
//        employeeChartRateList.add(15f);
//        chartviewEmployee.setShow(employeeChartcolorList, employeeChartRateList, true, true);

    }

    /**
     * 联网获取数据
     */
    private void requestNet() {
        mPresenter = new HomePresenter(this);
        //1. 入住养老院数等
        mPresenter.getHeaderData("branchNum");
        //2. 接待入住
        mPresenter.getAdmitLivingData("admitInOut");
        //3. 入住用户
        mPresenter.getLivingUserData("user");
        //4. 近三个月报警数据
        mPresenter.getAlertData("warning");
        //5. 入住养老入住\用户整体趋势
        mPresenter.getLivingTrendData("branchUserTrend");
        //6. 护理级别
        mPresenter.getUserNurseData("userByNurse");
        //7. 员工统计
        mPresenter.getStaffData("staff");
        //8.分院地址
        mPresenter.getBranchAddress("branchList");
        //9.分院护理进度
        mPresenter.getNurseProgressList("jobItem");
    }


    @OnClick({R.id.iv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                mPopupWindow.showBashOfAnchor(ivMenu, mPopuplayoutGravity, 0, 0);
                break;
        }
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
            //入住养老院
            tvRestHomeNum.setText(bean.getNumBranch() + "");
            //入住用户数
            tvUserNum.setText(bean.getNumUser() + "");
            //床位数
            tvBedroomNum.setText(bean.getNumBed() + "");
            //入住率
            tvLiveRate.setText(StringUtil.getFormatPercentRate(bean.getNumIn() * 1f / bean.getNumBed() * 100));

        }
    }

    /**
     * 2、接待入住
     *
     * @param bean
     */
    @Override
    public void getAdmitLivingData(AdmitLivingEntity bean) {
        int[] valueDatas = {bean.getAdminNum(), bean.getInNum(), bean.getOutNum()};
        ArrayList<Integer> yAxisData = new ArrayList<>();
        for (int i = 0; i < valueDatas.length; i++) {
            yAxisData.add(valueDatas[i]);
        }
        //新增用户数据的最大值
        Integer maxX = Collections.max(yAxisData);
        if (maxX % 2 == 0) {
            maxX = maxX + 2;
        } else {
            maxX = maxX + 1;
        }
        int space = maxX / 5;
        if (maxX % 5 != 0) {
            space = space + 2;
        }
        int[] yAxisDataArray = new int[5 + 1];
        yAxisDataArray[0] = 0;
        for (int i = 1; i < 5 + 1; i++) {
            yAxisDataArray[i] = yAxisDataArray[i - 1] + space;
        }
        mPannelChartView.setValueData(valueDatas, yAxisDataArray);
    }

    /**
     * 3、入住用户
     *
     * @param dataList
     */
    @Override
    public void getLivingUserData(List<ChartCommonEntity> dataList) {
        //处理集合数据——由于fastjson解析数据，有默认排序，与预期的不符合
        if (dataList != null && dataList.size() > 0) {
            //{"100岁以上":0,"60-69岁":5,"60以下":4,"70-79岁":18,"80-89岁":6,"90-99岁":0,"其他":4}
            dataList.add(dataList.size() - 1, dataList.get(0));
            dataList.remove(0);
            Collections.swap(dataList, 0, 1);
        }
//        for (int i = 0; i < dataList.size(); i++) {
//            Elog.e("TAG", "item=" + dataList.get(i).keyName + "-----item-value=" + dataList.get(i).value);
//        }
        //添加色块
        List<Integer> userLivingChartcolorList = new ArrayList<>();
        int[] colors = {R.color.color_34617e, R.color.color_f1b133, R.color.color_2e84ba, R.color.color_55c7f2, R.color.color_5ffefd, R.color.color_e36853, R.color.color_7c80fe};
        for (int i = 0; i < colors.length; i++) {
            userLivingChartcolorList.add(colors[i]);
        }

        //  添加百分比
        List<Float> userLivingChartRateList = new ArrayList<>();
        //设置显示的文字
        List<String> showTextList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            ChartCommonEntity bean = dataList.get(i);
            userLivingChartRateList.add(bean.value * 1f / mPresenter.livingTotal * 100);
            showTextList.add(bean.keyName);
        }
        mChartviewUserLivingView.setShow(userLivingChartcolorList, userLivingChartRateList, true, true);
        mChartviewUserLivingView.setShowTextList(showTextList);

    }

    /**
     * 4、报警数据
     */
    @Override
    public void getAlertData(WarningEntity bean) {

        // 添加的颜色
        List<Integer> alertChartcolorList = new ArrayList<>();
        //未处理
        alertChartcolorList.add(R.color.color_2b84b9);
        alertChartcolorList.add(R.color.color_f1b133);

        //  添加的是百分比
        List<Float> alertChartRateList = new ArrayList<>();
        List<String> showTextList = new ArrayList<>();

        float handledRate = (float) (bean.getNumY() * 1.0 / bean.getNumA() * 100);
        String formatHandledRate = StringUtil.getFormatPercentRate(handledRate);//format 返回的是字符串

        float unHandledRate = (100 - Float.parseFloat(formatHandledRate));
        String formatUnHandledRate = StringUtil.getFormatPercentRate(unHandledRate);
        //添加百分比
        alertChartRateList.add(Float.parseFloat(formatHandledRate));
        alertChartRateList.add(Float.parseFloat(formatUnHandledRate));
        //设置显示的text
        showTextList.add(MyApplication.getContext().getString(R.string.string_handled) + Float.parseFloat(formatHandledRate) + MyApplication.getContext().getString(R.string.string_percent_symbol));
        showTextList.add(MyApplication.getContext().getString(R.string.string_unhandled) + Float.parseFloat(formatUnHandledRate) + MyApplication.getContext().getString(R.string.string_percent_symbol));
        mChartviewDataAlertView.setShow(alertChartcolorList, alertChartRateList, true, true);
        mChartviewDataAlertView.setShowTextList(showTextList);
    }

    /**
     * 5.	入住养老入住\用户整体趋势
     *
     * @param dataList branchUserTrend
     */
    @Override
    public void getLivingTrendData(List<TrendDataEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        ArrayList<String> xAxisData = new ArrayList<>();
        //新增养老院
        ArrayList<Integer> branchNewAddList = new ArrayList<>();
        //新增用户
        ArrayList<Integer> userNewAddList = new ArrayList<>();
        //横坐标最多显示5个
        if (dataList.size() > 5) {
            for (int i = 0; i < 5; i++) {
                TrendDataEntity bean = dataList.get(i);
                xAxisData.add(bean.getKeyName() + MyApplication.getContext().getString(R.string.string_unit_month));
                userNewAddList.add(bean.getUserNewN());
                branchNewAddList.add(bean.getBranchNewN());
            }
        } else {
            for (int i = 0; i < dataList.size(); i++) {
                TrendDataEntity bean = dataList.get(i);
                xAxisData.add(bean.getKeyName() + MyApplication.getContext().getString(R.string.string_unit_month));
                userNewAddList.add(bean.getUserNewN());
                branchNewAddList.add(bean.getBranchNewN());
            }
        }
        //根据最大值生成对应的Y轴坐标范围
        gennerateMaxAxisDataList(branchNewAddList, userNewAddList);
        mUpCurveView.setData(userNewAddList, xAxisData, upYAxisData);
        mDowncurveView.setData(branchNewAddList, downYAxisData);
    }

    /**
     * 根据最大值生成上下曲线图对应的Y轴坐标范围(范围刻度最大为4个)
     *
     * @param branchNewAddList
     * @param userNewAddList
     */
    //范围刻度最大为4个
    private int maxKeduValue = 4;

    private void gennerateMaxAxisDataList(ArrayList<Integer> branchNewAddList, ArrayList<Integer> userNewAddList) {
        //新增用户数据的最大值
        Integer maxUserX = Collections.max(userNewAddList);
        if (maxUserX % 2 == 0) {
            maxUserX = maxUserX + 2;
        } else {
            maxUserX = maxUserX + 1;
        }
        //新增养老院数据的最大值
        Integer maxBranchX = Collections.max(branchNewAddList);
        if (maxBranchX % 2 == 0) {
            maxBranchX = maxUserX + 2;
        } else {
            maxBranchX = maxBranchX + 1;
        }

        int userSpace = maxUserX / maxKeduValue == 0 ? 1 : (maxUserX / maxKeduValue);
        int branchSpace = maxBranchX / maxKeduValue == 0 ? 1 : (maxBranchX / maxKeduValue);
        //刻度集合为maxKeduValue+1(0刻度占一个)
        upYAxisData = new int[maxKeduValue + 1];
        downYAxisData = new int[maxKeduValue + 1];
        upYAxisData[0] = 0;
        downYAxisData[0] = 0;
        for (int i = 1; i < 5; i++) {
            upYAxisData[i] = upYAxisData[i - 1] + userSpace;
            downYAxisData[i] = downYAxisData[i - 1] + branchSpace;
        }
    }

    /**
     * 6、护理级别
     *
     * @param dataList
     */
    @Override
    public void getUserNurseData(List<NurseLevelEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        List<Integer> nurseLevelChartcolorList = new ArrayList<>();
        //专户，一级，二级，三级
        int[] colors = {R.color.color_55c7f2, R.color.color_5ffefd, R.color.color_e36853, R.color.color_7c80fe, R.color.color_f1b133, R.color.color_34617e, R.color.color_2e84ba};
        // 百分比
        List<Float> nurseLevelChartRateList = new ArrayList<>();
        List<String> showTextList = new ArrayList<>();
        //计算总数
        int totalCount = 0;
        for (int i = 0; i < dataList.size(); i++) {
            totalCount += dataList.get(i).getNum();
        }
        //根据是否有以下类别,动态添加颜色值,设置显示的文字
        if (dataList.size() > 7) {
            //根据是否有以下类别,动态添加颜色值,设置显示的文字
            for (int i = 0; i < 7; i++) {
                NurseLevelEntity bean = dataList.get(i);
                String formatPercentRate = StringUtil.getFormatPercentRate(bean.getNum() * 1f / totalCount * 100);
                //添加百分比
                nurseLevelChartRateList.add(Float.parseFloat(StringUtil.getFormatPercentRate(bean.getNum() * 1f / totalCount * 100)));

                nurseLevelChartcolorList.add(colors[i]);
                showTextList.add(bean.getName() + " " + formatPercentRate + MyApplication.getContext().getString(R.string.string_percent_symbol));
            }
        } else {
            //根据是否有以下类别,动态添加颜色值,设置显示的文字
            for (int i = 0; i < dataList.size(); i++) {
                NurseLevelEntity bean = dataList.get(i);
                String formatPercentRate = StringUtil.getFormatPercentRate(bean.getNum() * 1f / totalCount * 100);
                //添加百分比
                nurseLevelChartRateList.add(Float.parseFloat(StringUtil.getFormatPercentRate(bean.getNum() * 1f / totalCount * 100)));

                nurseLevelChartcolorList.add(colors[i]);
                showTextList.add(bean.getName() + " " + formatPercentRate + MyApplication.getContext().getString(R.string.string_percent_symbol));
            }
        }
        mNurseLevelAdapter.update(dataList, true);

        mNurseLevelAdapter.update(dataList, true);
        chartviewNurselevel.setShow(nurseLevelChartcolorList, nurseLevelChartRateList, true, true);
        chartviewNurselevel.setShowTextList(showTextList);
    }

    /**
     * 7、员工统计
     *
     * @param dataList
     */
    @Override
    public void getStaffData(List<StaffEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        List<Integer> employeeChartcolorList = new ArrayList<>();
        int[] colors = {R.color.color_55c7f2, R.color.color_5ffefd, R.color.color_34617e, R.color.color_7c80fe, R.color.color_f1b133};
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

    @Override
    public void getNurseProgressList(List<JobItemEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        if (dataList.size() > 6) {
            mAdapterNurseProgress.update(dataList.subList(0, 6), true);
        } else {
            mAdapterNurseProgress.update(dataList, true);
        }
    }

}
