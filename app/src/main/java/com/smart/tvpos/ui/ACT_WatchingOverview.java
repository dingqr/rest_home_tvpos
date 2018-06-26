package com.smart.tvpos.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.WarningEntity;
import com.smart.tvpos.bean.WarningNewDataEntity;
import com.smart.tvpos.bean.WarningNewEntity;
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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_watching_overview;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        requestNet();
        initData();
    }

    private void requestNet() {
        requestWarningShow("warningShow");
        requestWarningNewList("warningNewList");
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
                if (bean != null) {
                    tvNumHelp.setText(bean.getNumHelp() + "");
                    tvNumMonitoring.setText(bean.getNumMonitoring() + "");
                    tvNumSleep.setText(bean.getNumSleep() + "");
                    tvNumHhealth.setText(bean.getNumHealth() + "");
                }
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
                List<WarningNewEntity> warningNewList = dataBean.getNum();
                Elog.e("TAG", "warningNewList=" + warningNewList.get(0).getTitle());
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

    private void initData() {
    }


    @Override
    protected View getLoadingTargetView() {
        return null;
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
