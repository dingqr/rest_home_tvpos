package com.smart.tvpos.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.adapter.ADA_NewWarningList;
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
    @Bind(R.id.recyclerview)
    LRecyclerView mRecyclerView;
    @Bind(R.id.tv_sub_title)
    TextView tvSubTitle;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private ADA_NewWarningList mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_watching_overview;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        tvSubTitle.setText("护理进度");
        initRecyclerView();
        requestNet();
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
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
        mRecyclerView.setFooterViewHint(MyApplication.getContext().getString(R.string.list_footer_loading), MyApplication.getContext().getString(R.string.list_footer_end), MyApplication.getContext().getString(R.string.list_footer_network_error));
    }

    private void requestNet() {
        requestWarningShow("warningShow");
        requestWarningNewList("warningNewList");
        showLoading(MyApplication.getContext().getString(R.string.common_loading_message));
    }

    /**
     * 报警数据(页面头部显示数据)
     *
     * @param requestType warningShow
     */
    private void requestWarningShow(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
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
        params.put("name", "hafuadmin");
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
