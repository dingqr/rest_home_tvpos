package com.yonyou.hhtpos.ui.dinner.dishes;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.yonyou.framework.library.base.BaseActivity;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.framework.library.netstatus.NetUtils;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.wd.OpenOrderEntity;
import com.yonyou.hhtpos.global.SalesModeConstants;
import com.yonyou.hhtpos.presenter.IWDCloseOrderPresenter;
import com.yonyou.hhtpos.presenter.IWDOpenOrderPresenter;
import com.yonyou.hhtpos.presenter.Impl.WDCloseOrderPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.WDOpenOrderPresenterImpl;
import com.yonyou.hhtpos.view.IWDCloseOrderView;
import com.yonyou.hhtpos.view.IWDOpenOrderView;

import de.greenrobot.event.EventBus;

/**
 * 点菜页面
 * 作者：liushuofei on 2017/7/11 10:31
 */
public class ACT_OrderDishes extends BaseActivity implements IWDOpenOrderView, IWDCloseOrderView{

    /**传递参数 */
    public static final String FROM_WD = "from.wd";
    public static final String TABLE_BILL_ID = "table.bill.id";
    private boolean fromWd;
    private String tableBillId;

    private FRA_DishesList mDishesLeft;

    /**中间者 */
    private IWDOpenOrderPresenter mWDOpenOrderPresenter;
    private IWDCloseOrderPresenter mWDCloseOrderPresenter;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        fromWd = extras.getBoolean(FROM_WD, false);
        tableBillId = extras.getString(TABLE_BILL_ID, "");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_order_dishes;
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
        mDishesLeft = new FRA_DishesList();

        // 替换left fragment
        FragmentTransaction leftTrans = getSupportFragmentManager().beginTransaction();
        leftTrans.add(R.id.fl_left, mDishesLeft);
        leftTrans.commitAllowingStateLoss();

        // 替换right fragment
        FragmentTransaction rightTrans = getSupportFragmentManager().beginTransaction();
        rightTrans.add(R.id.fl_right, new FRA_OrderDishes());
        rightTrans.commitAllowingStateLoss();

        mWDOpenOrderPresenter = new WDOpenOrderPresenterImpl(this, this);
        mWDCloseOrderPresenter = new WDCloseOrderPresenterImpl(this, this);

        if (fromWd){
            OpenOrderEntity bean = new OpenOrderEntity();
            bean.setShopId("hht");
            bean.setWaiterId("0001");
            bean.setWaiterName("王五");
            bean.setSalesMode(SalesModeConstants.SALES_MODE_WD);
            mWDOpenOrderPresenter.openOrder(bean);
        }

        //新起一个子线程发送tableBillId
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(tableBillId)){
                    EventBus.getDefault().post(tableBillId);
                }
            }
        }, 0);
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
    public void openOrder(String tableBillId) {
        this.tableBillId = tableBillId;
        //发送到右侧列表
        if (!TextUtils.isEmpty(tableBillId)){
            EventBus.getDefault().post(tableBillId);
        }
    }

    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //System.out.println("按下了back键   onBackPressed()");

        mWDCloseOrderPresenter.closeOrder(tableBillId);
    }

    @Override
    public void closeOrder() {
        finish();
    }
}
