package com.smart.tvpos.ui;

import android.os.Bundle;
import android.view.View;

import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.bean.UserNurseDataEntity;
import com.smart.tvpos.bean.UserNurseListEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.Constants;

import java.util.HashMap;

/**
 * Created by JoJo on 2018/6/23.
 * wechat：18510829974
 * description：护理进度页面
 */
public class ACT_NursingProgress extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_nursing_progress;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        requestNet();
    }

    private void requestNet() {
        requestWarningShow("userNurse");
    }

    /**
     * 12.	用户护理进度
     *
     * @param requestType userNurse
     */
    private void requestWarningShow(String requestType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("a", requestType);
        params.put("name", "hafuadmin");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<UserNurseDataEntity>() {

            @Override
            public void onReqSuccess(UserNurseDataEntity bean) {
                if (bean == null || bean.getUser() == null || bean.getUser().size() == 0) {
                    return;
                }
                UserNurseListEntity userNurseBean = bean.getUser().get(0);
                Elog.e("TAG" + "userNurse=" + userNurseBean.getBuildingName());
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
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
