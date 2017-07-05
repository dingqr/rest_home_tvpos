package com.yonyou.hhtpos.ui.reserve;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseSimple;

import butterknife.Bind;

/**
 * Created by ybing on 2017/6/29.
 * 桌台预定查询
 */
public class ACT_ReserveTableQuery extends ACT_BaseSimple {
    @Bind(R.id.tv_title)
    protected TextView title;

    @Override
    protected void initView() {
        title.setText(this.getResources().getString(R.string.table_reserve_query));
    }

    @Override
    protected Fragment getContentFragment() {
        return  new FRA_ReserveTableQuery();
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
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }
}
