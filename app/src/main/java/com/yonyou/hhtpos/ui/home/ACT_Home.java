package com.yonyou.hhtpos.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.log.Elog;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.base.ACT_BaseSimple;
import com.yonyou.hhtpos.bean.NavigationNewEntity;
import com.yonyou.hhtpos.presenter.INavigationPresenter;
import com.yonyou.hhtpos.presenter.IPrintPresenter;
import com.yonyou.hhtpos.presenter.Impl.NavigationPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.PrintPresenterImpl;
import com.yonyou.hhtpos.util.AidlUtil;
import com.yonyou.hhtpos.util.BytesUtil;
import com.yonyou.hhtpos.view.INavigationView;
import com.yonyou.hhtpos.view.IPrintView;

import java.util.List;

/**
 * 首页
 * 作者：liushuofei on 2017/6/29 11:48
 */
public class ACT_Home extends ACT_BaseSimple implements INavigationView, IPrintView{

    private INavigationPresenter mNavigationPresenter;
    private List<NavigationNewEntity> dataList;

    /**打印中间者 */
    private IPrintPresenter mPrintPresenter;

    @Override
    protected void initView() {
        mNavigationPresenter = new NavigationPresenterImpl(mContext, this);
        mNavigationPresenter.requestNavigationList("001");

//        mPrintPresenter = new PrintPresenterImpl(this, this);
//        mPrintPresenter.requestPrintOrder();
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
    public void requestNavigationList(List<NavigationNewEntity> dataList) {
        this.dataList = dataList;
        MyApplication.dataList = dataList;
    }

    @Override
    public void requestPrintOrder(String[] strings) {
        byte[] bytes = new byte[strings.length];
        for (int i = 0; i < strings.length; i++){
            bytes[i] = Byte.parseByte(strings[i]);
        }

        // 打印文字
//        AidlUtil.getInstance().printText(AidlUtil.PRINT_TEXT, 20, false, false);
        try {
            AidlUtil.getInstance().printText(new String(bytes, "UTF-8"), 20, false, false);
//            AidlUtil.getInstance().sendRawData(BytesUtil.getBaiduTestBytes());

            Elog.e("printText", String.valueOf(bytes));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
