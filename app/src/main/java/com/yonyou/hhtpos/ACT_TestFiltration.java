package com.yonyou.hhtpos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.dialog.DIA_ReserveFiltration;

import java.util.ArrayList;

import butterknife.Bind;

import static com.yonyou.hhtpos.util.FiltrationUtil.getFakeData;

//测试筛选布局
public class ACT_TestFiltration extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private ArrayList<FilterItemEntity> filterItemList;
    DIA_ReserveFiltration dia_reserveFiltration;


    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_test_filtration;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        btnConfirm.setOnClickListener(this);
        filterItemList = getFakeData();
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

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                dia_reserveFiltration = new DIA_ReserveFiltration(mContext,filterItemList);
                dia_reserveFiltration.getDialog().show();
                break;

            default:
                break;
        }
    }
}
