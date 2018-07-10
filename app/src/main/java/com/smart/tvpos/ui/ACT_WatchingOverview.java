package com.smart.tvpos.ui;

import android.graphics.Rect;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.ReceiveConstants;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.AppDateUtil;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.adapter.ADA_BuildingList;
import com.smart.tvpos.adapter.ADA_FloorList;
import com.smart.tvpos.adapter.ADA_NewWarningList;
import com.smart.tvpos.bean.BuildingEntity;
import com.smart.tvpos.bean.FloorEntity;
import com.smart.tvpos.bean.NewWarningEntity;
import com.smart.tvpos.bean.WarningEntity;
import com.smart.tvpos.bean.WarningNewDataEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.Constants;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JoJo on 2018/6/22.
 * wechat：18510829974
 * description：监控概览页面
 */
public class ACT_WatchingOverview extends BaseActivity {
    @Bind(R.id.tv_num_help)
    TextView tvNumHelp;//当日一键求助报警数据
    @Bind(R.id.tv_num_monitoring)
    TextView tvNumMonitoring;//当日离线/离开/进入范围报警数据
    @Bind(R.id.tv_num_sleep)
    TextView tvNumSleep;//离床报警数据
    @Bind(R.id.tv_num_health)
    TextView tvNumHhealth;//生命体征异常报警数据
    @Bind(R.id.tv_current_time)
    TextView tvCurrentTime;//系统当前时间
    @Bind(R.id.recyclerview)
    LRecyclerView mRecyclerView;
    @Bind(R.id.tv_sub_title)
    TextView tvSubTitle;
    @Bind(R.id.buildingList)
    ListView listviewbuildingList;
    @Bind(R.id.floorList)
    ListView listviewfloorList;
    @Bind(R.id.tv_building)
    TextView tvBuilding;
    @Bind(R.id.tv_floor)
    TextView tvFloor;
    @Bind(R.id.tv_floor_name)
    TextView tvFloorName;
    @Bind(R.id.tv_active_num)
    TextView tvActiveNum;
    @Bind(R.id.tv_offline_num)
    TextView tvOfflineNum;
    @Bind(R.id.webview)
    WebView webview;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ADA_NewWarningList mAdapter;
    private ADA_BuildingList mBuildingAdapter;
    private ADA_FloorList mFloorAdapter;
    //当前选中的楼层
    private FloorEntity mSelectFloorEntity;
    //当前选中的楼宇
    private BuildingEntity mSelectBuildingEntity;

    /**
     * 接收刷新频率的广播
     *
     * @param intent
     * @param bundle
     */
    @Override
    protected void onReceiveBroadcast(int intent, Bundle bundle) {
        if (intent == ReceiveConstants.REFRESH_CURRENT_PAGE) {
            requestNet(false);
        }
    }

    @Override
    protected long getRefreshTime() {
        return 0;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_watching_overview;
    }

    @OnClick({R.id.tv_sub_title, R.id.tv_building, R.id.tv_floor, R.id.tv_watching_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sub_title:
                readyGo(ACT_NursingProgress.class);
                break;
            case R.id.tv_building:
                if (listviewbuildingList.getVisibility() == View.INVISIBLE) {
                    showBuildingList();
                } else {
                    hideBuildingList();
                }
                break;
            case R.id.tv_floor:
                if (listviewfloorList.getVisibility() == View.INVISIBLE) {
                    showFloorList();
                } else {
                    hideFloorList();
                }
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        //title（养老院简称）+监控概览
        if (Constants.TYPE.equals("总院")) {
            setHeaderTitle(MyApplication.getContext().getString(R.string.string_title_watching_overview_zong));
        } else if (Constants.TYPE.equals("分院")) {
            setHeaderTitle(Constants.BRANCH_NAME + "监控概览");
        }
        tvSubTitle.setText("护理进度");
        //显示系统当前时间
        String timeStamp = AppDateUtil.getTimeStamp(System.currentTimeMillis(), AppDateUtil.YYYY_MM_DD_POINT);
        String week = AppDateUtil.getWeek(AppDateUtil.getTimeStamp(System.currentTimeMillis(), AppDateUtil.YYYY_MM_DD));
        tvCurrentTime.setText(timeStamp + " " + week);

        initRecyclerView();
        initListView();
        initwebview();

        requestNet(true);
        initListener();
    }

    private void initwebview() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());

        // 设置WebView可触摸放大缩小
//        webview.getSettings().setBuiltInZoomControls(true); //显示放大缩小 controler
//        webview.getSettings().setSupportZoom(true); //可以缩放
//        webview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);//默认缩放模式
////        设置以上无效,使用
//        webview.setInitialScale(100);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            // (1)解决android 6.0 webview加载https出现空白页问题
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受所有网站的证书
            }
        });
        /**
         *  (1)解决android 6.0 webview加载https出现空白页问题
         *  webview在安卓5.0之前默认允许其加载混合网络协议内容
         *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.loadUrl(API.URL_H5 +Constants.USER_ID);
    }


    private void initListView() {
        mBuildingAdapter = new ADA_BuildingList(mContext);
        listviewbuildingList.setAdapter(mBuildingAdapter);

        mFloorAdapter = new ADA_FloorList(mContext);
        listviewfloorList.setAdapter(mFloorAdapter);
    }

    private void initListener() {
        listviewbuildingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectBuildingEntity = mBuildingAdapter.getDataList().get(i);
                mBuildingAdapter.setCheckCurrentItem(i);
                tvBuilding.setText(mSelectBuildingEntity.getBuildingName());
                mFloorAdapter.update(mSelectBuildingEntity.getList(), true);
                //切换时默认显示列表的第一个
                if (mSelectBuildingEntity.getList().size() > 0) {
                    tvFloor.setText(mSelectBuildingEntity.getList().get(0).getFloorName());
                }
                if (listviewfloorList.getVisibility() == View.INVISIBLE) {
                    showFloorList();
                }
            }
        });
        //楼层切换
        listviewfloorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectFloorEntity = mFloorAdapter.getDataList().get(i);
                mFloorAdapter.setCheckCurrentItem(i);
                tvFloor.setText(mSelectFloorEntity.getFloorName());
                tvFloorName.setText(mSelectBuildingEntity.getBuildingName() + mSelectFloorEntity.getFloorName());
                hideFloorList();
                if (listviewbuildingList.getVisibility() == View.VISIBLE) {
                    hideBuildingList();
                }
                //选中的楼和楼层
//                CommonUtils.makeEventToast(MyApplication.getContext(), mSelectBuildingEntity.getBuildingName() + mSelectFloorEntity.getFloorName(), false);
                //切换楼层时,刷新当前楼层用户床垫在线情况
                requestBuildingUserList("buildingUser", mSelectFloorEntity.getFloorId());
            }
        });
    }

    /**
     * 弹出楼宇列表
     */
    private void showBuildingList() {
        listviewbuildingList.setVisibility(View.VISIBLE);
        listviewbuildingList.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    /*
     * 隐藏楼宇列表
     */
    private void hideBuildingList() {
        listviewbuildingList.setVisibility(View.INVISIBLE);
        listviewbuildingList.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
    }

    /**
     * 弹出楼层列表
     */
    private void showFloorList() {
        listviewfloorList.setVisibility(View.VISIBLE);
        listviewfloorList.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    /**
     * 隐藏楼层列表
     */
    private void hideFloorList() {
        listviewfloorList.setVisibility(View.INVISIBLE);
        listviewfloorList.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
    }

    /**
     * 初始化配置RecyclerView
     */
    private void initRecyclerView() {
        mAdapter = new ADA_NewWarningList(mContext);
//        mAdapter.setHasStableIds(true);
        //配置列表样式
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        //设置外层列表Adapter
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        //设置item之间的间距
        mRecyclerView.addItemDecoration(new LuRecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {

                //header占了一个位置，故从位置1开始显示实际的item
                if (itemPosition == 1) {
                    outRect.top = 44;//设计图recyclerview距离上方控件为44px
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new

                LinearLayoutManager(mContext));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadMoreEnabled(false);
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
//        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {


            }
        });
        //设置头部文字颜色
        mRecyclerView.setHeaderViewColor(R.color.color_2e84ba, R.color.color_2e84ba, R.color.color_FFFFFF);
        //设置底部加载颜色-loading动画颜色,文字颜色,footer的背景颜色
        mRecyclerView.setFooterViewColor(R.color.color_2e84ba, R.color.color_2e84ba, R.color.color_FFFFFF);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint(MyApplication.getContext().

                getString(R.string.list_footer_loading), MyApplication.

                getContext().

                getString(R.string.list_footer_end), MyApplication.

                getContext().

                getString(R.string.list_footer_network_error));
    }

    /**
     * 请求数据
     *
     * @param isShowLoading
     */

    private void requestNet(boolean isShowLoading) {
        requestWarningShow("warningShow");
        requestWarningNewList("warningNewList");
        requestBuildingList("building");
//        requestBuildingUserList("buildingUser");
        if (isShowLoading) {
            showLoading(MyApplication.getContext().getString(R.string.common_loading_message));
        }
    }

    /**
     * 报警数据(页面头部显示数据)
     *
     * @param requestType warningShow
     */
    private void requestWarningShow(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<WarningEntity>() {

            @Override
            public void onReqSuccess(WarningEntity bean) {
                hideLoading();
                if (bean != null) {
                    tvNumHelp.setText(bean.getNumHelp() + "");
                    tvNumMonitoring.setText(bean.getNumMonitoring() + "");
                    tvNumSleep.setText(bean.getNumSleep() + "");
                    tvNumHhealth.setText(bean.getNumHealth() + "");
                }
            }

            @Override
            public void onFailure(String result) {
                hideLoading();
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                hideLoading();
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 最新报警消息列表（右边）
     *
     * @param requestType warningNewList
     */
    private void requestWarningNewList(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<WarningNewDataEntity>() {

            @Override
            public void onReqSuccess(WarningNewDataEntity dataBean) {
                if (dataBean == null || dataBean.getNum() == null || dataBean.getNum().size() == 0) {
                    return;
                }
                List<NewWarningEntity> warningNewList = dataBean.getNum();
                mAdapter.update(warningNewList, true);
                Elog.e("TAG", "warningNewList=" + warningNewList.get(0).getTitle());

                setStaffName(warningNewList);
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 3.	当前分院的楼宇,楼层
     *
     * @param requestType building
     */
    private void requestBuildingList(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<List<BuildingEntity>>() {

            @Override
            public void onReqSuccess(List<BuildingEntity> dataList) {
                if (dataList == null || dataList.size() == 0) {
                    return;
                }
                mSelectBuildingEntity = dataList.get(0);
                if (mSelectBuildingEntity == null) {
                    return;
                }
                Elog.e("TAG", "building=" + mSelectBuildingEntity.getBuildingName());

                tvBuilding.setText(mSelectBuildingEntity.getBuildingName());
                mBuildingAdapter.update(dataList, true);

                if (mSelectBuildingEntity.getList().size() > 0) {
                    tvFloor.setText(mSelectBuildingEntity.getList().get(0).getFloorName());
                    tvFloorName.setText(mSelectBuildingEntity.getBuildingName() + mSelectBuildingEntity.getList().get(0).getFloorName());
                    //floorId-请求床垫在线离线人数
                    requestBuildingUserList("buildingUser", mSelectBuildingEntity.getList().get(0).getFloorId());
                }
                mFloorAdapter.update(mSelectBuildingEntity.getList(), true);
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 4.	当前楼层用户床垫在线情况
     *
     * @param requestType buildingUser
     * @param floorId
     */
    private void requestBuildingUserList(String requestType, int floorId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        params.put("floorId", floorId + "");
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<WarningEntity>() {

            @Override
            public void onReqSuccess(WarningEntity bean) {
                hideLoading();
                if (bean != null) {
                    tvActiveNum.setText("活跃：" + bean.getIn());
                    tvOfflineNum.setText("离线：" + bean.getOut());
                }
            }

            @Override
            public void onFailure(String result) {
                hideLoading();
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                hideLoading();
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }
        });
    }

    /**
     * 处理护工的显示
     *
     * @param warningNewList
     */
    private void setStaffName(List<NewWarningEntity> warningNewList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < warningNewList.size(); i++) {
            NewWarningEntity bean = warningNewList.get(i);
            if (bean == null) {
                continue;
            }
            if (bean.getStaff() == null) {
                continue;
            }
            if (bean.getStaff().size() > 0) {
                //只有一个护工
                if (bean.getStaff().size() == 1 && bean.getStaff().get(0) != null) {
                    bean.setAllStaff(bean.getStaff().get(0).getName() == null ? "" : bean.getStaff().get(0).getName());
                    continue;
                }
                //多个护工
                for (int j = 0; j < bean.getStaff().size(); j++) {
                    NewWarningEntity.StaffBean staffBean = bean.getStaff().get(j);
                    stringBuffer.append(staffBean.getName());
                    if (!(j == bean.getStaff().size() - 1)) {
                        stringBuffer.append("、");
                    }
                }
            }
        }
    }


    @Override
    protected View getLoadingTargetView() {
        return mRecyclerView;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
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
}
