package com.smart.tvpos.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.R;
import com.smart.tvpos.widgets.CommonPopupWindow;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by JoJo on 2018/6/21.
 * wechat：18510829974
 * description：主页：数据监控
 */
public class ACT_Home extends BaseActivity {
    @Bind(R.id.tv_rest_home_num)
    TextView tvRestHomeNum;
    @Bind(R.id.tv_user_num)
    TextView tvUserNum;
    @Bind(R.id.tv_bedroom_num)
    TextView tvBedroomNum;
    @Bind(R.id.tv_live_rate)
    TextView tvLiveRate;
    private CommonPopupWindow mPopupWindow;
    private CommonPopupWindow.LayoutGravity mPopuplayoutGravity;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        mPopupWindow = new CommonPopupWindow(this, R.layout.popup_home_menu, 80, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
            }

            @Override
            protected void initEvent() {
            }
        };
        mPopuplayoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_ABOVE | CommonPopupWindow.LayoutGravity.TO_RIGHT);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_home;
    }

    @OnClick({R.id.iv_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:

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
}
