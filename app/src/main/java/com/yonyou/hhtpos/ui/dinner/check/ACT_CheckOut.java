package com.yonyou.hhtpos.ui.dinner.check;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.base.ACT_BaseMultiple;
import com.yonyou.hhtpos.bean.check.RequestPayEntity;
import com.yonyou.hhtpos.bean.check.SettleAccountDataEntity;
import com.yonyou.hhtpos.presenter.IQueryBillInfoPresenter;
import com.yonyou.hhtpos.presenter.Impl.QueryBillInfoPresenterImpl;
import com.yonyou.hhtpos.view.IQueryBillInfoView;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 结账页面
 * 作者：liushuofei on 2017/7/15 10:14
 */
public class ACT_CheckOut extends ACT_BaseMultiple implements IQueryBillInfoView {


    @Bind(R.id.inc_title)
    RelativeLayout mTitleLay;
    private IQueryBillInfoPresenter mPresenter;
    private String tableBillId;
    private String compId = "DIE49JkEU29JHD819HRh19hGDAY1";
    private String shopId = "C13352966C000000A60000000016E000";
    //传递参数
    public static final String TABLE_BILL_ID = "table.bill.id";
    @Override
    protected void initView() {
        mTitleLay.setVisibility(View.GONE);
        mPresenter = new QueryBillInfoPresenterImpl(mContext, this);
        RequestPayEntity requestPayEntity = new RequestPayEntity();
        mPresenter.queryBillInfo(compId, shopId, tableBillId,false,requestPayEntity);
    }

    @Override
    protected float getLeftWeight() {
        return 0.47f;
    }

    @Override
    protected Fragment getLeftContent() {
        return new FRA_CheckOutLeft();
    }

    @Override
    protected Fragment getRightContent() {
        return new FRA_CheckOutRight();
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        tableBillId = extras.getString(TABLE_BILL_ID, "");
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return getLayoutRoot();
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
    public void queryBillInfo(SettleAccountDataEntity settleAccountDataEntity) {
        if (settleAccountDataEntity != null) {
            EventBus.getDefault().post(settleAccountDataEntity);
        }
    }

    public String getTableBillId() {
        return tableBillId;
    }
}
