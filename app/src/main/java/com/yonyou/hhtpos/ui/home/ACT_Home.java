package com.yonyou.hhtpos.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseSimple;
import com.yonyou.hhtpos.dialog.DIA_Navigation;
import com.yonyou.hhtpos.dialog.DIA_QRCode;
import com.yonyou.hhtpos.ui.dinner.wm.ACT_TakeOut;

import butterknife.Bind;

/**
 * 首页
 * 作者：liushuofei on 2017/6/29 11:48
 */
public class ACT_Home extends ACT_BaseSimple implements View.OnClickListener{

    @Bind(R.id.iv_menu)
    ImageView mMenuImg;

    @Override
    protected void initView() {
        mMenuImg.setOnClickListener(this);
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
//                readyGoThenKill(ACT_BookPreview.class);
//                readyGoThenKill(ACT_Packing.class);
//                readyGoThenKill(ACT_TakeOut.class);

                DIA_Navigation dia_navigation = new DIA_Navigation(mContext);
                dia_navigation.getDialog().show();

//                DIA_QRCode dia_qrCode = new DIA_QRCode(mContext);
//                dia_qrCode.getDialog().show();
                break;

            default:
                break;
        }
    }
}
