package com.yonyou.hhtpos.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.base.ACT_BaseSimple;
import com.yonyou.hhtpos.bean.NavigationNewEntity;
import com.yonyou.hhtpos.dialog.DIA_Navigation;
import com.yonyou.hhtpos.presenter.INavigationPresenter;
import com.yonyou.hhtpos.presenter.Impl.NavigationPresenterImpl;
import com.yonyou.hhtpos.view.INavigationView;

import java.util.List;

import butterknife.Bind;

/**
 * 首页
 * 作者：liushuofei on 2017/6/29 11:48
 */
public class ACT_Home extends ACT_BaseSimple implements View.OnClickListener, INavigationView{

    @Bind(R.id.iv_menu)
    ImageView mMenuImg;

    private INavigationPresenter mNavigationPresenter;
    private List<NavigationNewEntity> dataList;

    @Override
    protected void initView() {
        mMenuImg.setOnClickListener(this);

        mNavigationPresenter = new NavigationPresenterImpl(mContext, this);
        mNavigationPresenter.requestNavigationList("001");
    }

    @Override
    protected Fragment getContentFragment() {
        return new FRA_Home();
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

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
        switch (v.getId()){
            case R.id.iv_menu:
                DIA_Navigation dia_navigation = new DIA_Navigation(mContext, dataList);
                dia_navigation.getDialog().show();
                break;

            default:
                break;
        }
    }

    @Override
    public void requestNavigationList(List<NavigationNewEntity> dataList) {
        this.dataList = dataList;

        MyApplication.dataList = dataList;
    }
}
