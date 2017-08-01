package com.yonyou.hhtpos.ui.dinner.wd;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.CommonUtils;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_OrderDishesDetail;
import com.yonyou.hhtpos.adapter.ADA_WDDetailPayType;
import com.yonyou.hhtpos.bean.wd.DishDetaiListlEntity;
import com.yonyou.hhtpos.bean.wd.WDOrderDetailEntity;
import com.yonyou.hhtpos.global.DishConstants;
import com.yonyou.hhtpos.presenter.IOrderDetailPresenter;
import com.yonyou.hhtpos.presenter.Impl.OrderDetailPresenterImpl;
import com.yonyou.hhtpos.ui.dinner.check.ACT_CheckOut;
import com.yonyou.hhtpos.view.IWDOrderDetailView;
import com.yonyou.hhtpos.widgets.BanSlideListView;

import java.util.ArrayList;

import butterknife.Bind;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.FROM_WHERE;
import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.TABLE_BILL_ID;

/**
 * Created by zj on 2017/7/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单明细-马诗雨
 */
public class FRA_PackingDetail extends BaseFragment implements IWDOrderDetailView {
    @Bind(R.id.rl_root_view)
    RelativeLayout rlRootView;
    @Bind(R.id.lv_order_detail)
    ListView lvOrderDishes;
    //详情字段
    @Bind(R.id.tv_open_order_waiter)
    TextView tvOpenOrderWaiter;
    @Bind(R.id.tv_open_order_time)
    TextView tvOpenOrderTime;
    @Bind(R.id.tv_open_order_person_num)
    TextView tvOpenOrderPersonNum;
    @Bind(R.id.tv_pay_status)
    TextView tvPayStatus;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_reduce_money)
    TextView tvReduceMoney;
    @Bind(R.id.tv_discount_money)
    TextView tvDiscountMoney;
    @Bind(R.id.tv_ignore_money)
    TextView tvIgnoreMoney;
    @Bind(R.id.tv_real_receive_amount)
    TextView tvRealReceiveAmount;
    @Bind(R.id.tv_cashier)
    TextView tvCashier;
    @Bind(R.id.tv_not_pay)
    TextView tvNotPay;
    @Bind(R.id.tv_wait_receive_money)
    TextView tvWaitReceiveMoney;
    //支付方式
    @Bind(R.id.lv_pay_type)
    BanSlideListView mLvPayType;
    //支付信息
    @Bind(R.id.layout_pay_info)
    LinearLayout layoutPayInfo;
    @Bind(R.id.tv_button)
    TextView tvButton;
    private ADA_WDDetailPayType mPaytypeAdapter;
    private ADA_OrderDishesDetail mAdapter;
    private ArrayList<DishDetaiListlEntity> dataList = new ArrayList<>();

    //请求外带详情接口
    private IOrderDetailPresenter mPresenter;
    private WDOrderDetailEntity mDataBean;
    private int payStatus;
    private String tableBillId = "";

    /**
     * 左侧外带订单列表是否为空
     *
     * @param dataList
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onWDLeftOrderList(ArrayList<com.yonyou.hhtpos.bean.wd.OrderListEntity> dataList) {
        if (dataList == null || dataList.size() == 0) {
            showEmpty(R.drawable.default_no_order_detail, mContext.getResources().getString(R.string.empty_msg), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222), mContext.getResources().getString(R.string.empty_msg_other));
        }
    }


    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return rlRootView;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter = new OrderDetailPresenterImpl(getActivity(), this);

        //有数据页面
        mAdapter = new ADA_OrderDishesDetail(mContext);
        lvOrderDishes.setAdapter(mAdapter);
        //设置支付方式
        mPaytypeAdapter = new ADA_WDDetailPayType(mContext);
        mLvPayType.setAdapter(mPaytypeAdapter);

        // 无数据页面
//        showEmpty(R.drawable.default_no_order_detail, mContext.getResources().getString(R.string.empty_msg), ContextCompat.getColor(mContext, R.color.color_e9e9e9), ContextCompat.getColor(mContext, R.color.color_222222),mContext.getResources().getString(R.string.empty_msg_other));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fra_packing_detail;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    public void requestPackingDetail(String tableBillId) {
        this.tableBillId = tableBillId;
        mPresenter.requestWDOrderDetail(tableBillId);
    }

    @OnClick({R.id.tv_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_button:
//                1-部分支付，2-支付完成，3-已退款，4-未支付
                switch (payStatus) {
                    case 1:
                    case 4:
                        tvButton.setText(mContext.getResources().getString(R.string.string_settle_account));
                        Bundle bundle = new Bundle();
                        bundle.putInt(FROM_WHERE, DishConstants.TYPE_WD);
                        bundle.putString(TABLE_BILL_ID, tableBillId);
                        readyGo(ACT_CheckOut.class);
                        break;
                    case 2:
                        tvButton.setText(mContext.getResources().getString(R.string.string_dozen_bill));
                        CommonUtils.makeEventToast(mContext,mContext.getResources().getString(R.string.string_dozen_bill),false);
                        break;

                }
                break;
        }
    }

    /**
     * 外带订单详情
     *
     * @param orderDetailEntity
     */
    @Override
    public void requestWDOrderDetail(WDOrderDetailEntity orderDetailEntity) {
        if (orderDetailEntity != null) {
            if (orderDetailEntity.payStatus != null) {
                payStatus = Integer.parseInt(orderDetailEntity.payStatus);
            }

            //开单服务员
            tvOpenOrderWaiter.setText(orderDetailEntity.waiterName);
            //开单时间
            tvOpenOrderTime.setText(String.valueOf(AppDateUtil.getTimeStamp(orderDetailEntity.openTime, AppDateUtil.YYYY_MM_DD_HH_MM_SS)));
            //开单服务员
            tvOpenOrderPersonNum.setText(StringUtil.getString(orderDetailEntity.personNum));
            //支付状态
            handlePayStatus();

            //会员手机号
            if (orderDetailEntity.memberPhone.length() == 11) {
                String maskNumber = orderDetailEntity.memberPhone.substring(0, 3) + "****" + orderDetailEntity.memberPhone.substring(7, orderDetailEntity.memberPhone.length());
                tvPhoneNumber.setText(maskNumber);
            }
            //优惠金额
            tvReduceMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getReduceMoney());
            //折扣
            tvDiscountMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getDiscountMoney());
            //抹零
            tvIgnoreMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getIgnoreMoney());
            //实际支付
            tvRealReceiveAmount.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getRealReceiveAmount());

            //支付方式-可能组合
            if (orderDetailEntity.payTypeList != null && orderDetailEntity.payTypeList.size() > 0) {
                mPaytypeAdapter.update(orderDetailEntity.payTypeList, true);
            }
            //收银员名称
            tvCashier.setText(orderDetailEntity.waiterName);
            //点菜明细列表
            ArrayList<DishDetaiListlEntity> dishListDetail = orderDetailEntity.dishListDetail;
            if (dishListDetail != null && dishListDetail.size() > 0) {
                mAdapter.update(dishListDetail, true);
            }
        }
    }

    /**
     * // 1-部分支付，2-支付完成，3-已退款，4-未支付
     */
    private void handlePayStatus() {
        switch (payStatus) {
            //部分支付
            case 1:
                tvPayStatus.setText(mContext.getResources().getString(R.string.string_paying));
                tvNotPay.setVisibility(View.VISIBLE);
                tvWaitReceiveMoney.setVisibility(View.VISIBLE);
                layoutPayInfo.setVisibility(View.VISIBLE);
                break;
            //支付完成
            case 2:
                tvPayStatus.setText(mContext.getResources().getString(R.string.string_pay_successful));
                tvNotPay.setVisibility(View.GONE);
                tvWaitReceiveMoney.setVisibility(View.GONE);
                layoutPayInfo.setVisibility(View.VISIBLE);
                break;
            //已退款-外带无退款状态
            case 3:

                break;
            //未支付
            case 4:
                tvPayStatus.setText(mContext.getResources().getString(R.string.string_wait_pay));
                tvNotPay.setVisibility(View.VISIBLE);
                tvWaitReceiveMoney.setVisibility(View.VISIBLE);
                layoutPayInfo.setVisibility(View.GONE);
                break;
        }
    }
}

