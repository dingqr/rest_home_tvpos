package com.yonyou.hhtpos.ui.dinner.wd;

/**
 * Created by zj on 2017/7/17.
 * 邮箱：zjuan@yonyou.com
 * 描述：
 */

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseFragment;
import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.AppDateUtil;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.framework.library.eventbus.EventCenter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_OrderDishesDetail;
import com.yonyou.hhtpos.adapter.ADA_WDDetailPayType;
import com.yonyou.hhtpos.bean.wd.OrderListEntity;
import com.yonyou.hhtpos.bean.wd.WDDishDetaiListlEntity;
import com.yonyou.hhtpos.bean.wd.WDOrderDetailEntity;
import com.yonyou.hhtpos.global.DishConstants;
import com.yonyou.hhtpos.presenter.IOrderDetailPresenter;
import com.yonyou.hhtpos.presenter.IWDPrintOrderPresenter;
import com.yonyou.hhtpos.presenter.Impl.OrderDetailPresenterImpl;
import com.yonyou.hhtpos.presenter.Impl.WDPrintOrderPresenterImpl;
import com.yonyou.hhtpos.ui.dinner.check.ACT_CheckOut;
import com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes;
import com.yonyou.hhtpos.util.Constants;
import com.yonyou.hhtpos.view.IWDOrderDetailView;
import com.yonyou.hhtpos.view.IWDPrintOrderView;
import com.yonyou.hhtpos.widgets.BanSlideListView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static com.yonyou.hhtpos.R.id.tv_wait_receive_money;
import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.FROM_WHERE;
import static com.yonyou.hhtpos.ui.dinner.dishes.ACT_OrderDishes.TABLE_BILL_ID;

/**
 * Created by zj on 2017/7/4.
 * 邮箱：zjuan@yonyou.com
 * 描述：外带订单明细-马诗雨
 */
public class FRA_PackingDetail extends BaseFragment implements IWDOrderDetailView, IWDPrintOrderView {
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
    @Bind(tv_wait_receive_money)
    TextView tvWaitReceiveMoney;
    @Bind(R.id.layout_wait_receive)
    LinearLayout layoutWaitReceive;//待收
    //支付方式
    @Bind(R.id.lv_pay_type)
    BanSlideListView mLvPayType;
    //支付信息
    @Bind(R.id.layout_pay_info)
    LinearLayout layoutPayInfo;
    @Bind(R.id.tv_button)
    TextView tvButton;
    //会员信息
    @Bind(R.id.layout_member_info)
    LinearLayout layoutMemberInfo;
    @Bind(R.id.layout_discount)
    LinearLayout layoutDiscount;
    @Bind(R.id.layout_ignore)
    LinearLayout layoutIgnore;
    private ADA_WDDetailPayType mPaytypeAdapter;
    private ADA_OrderDishesDetail mAdapter;
    private ArrayList<WDDishDetaiListlEntity> dataList = new ArrayList<>();

    //请求外带详情接口
    private IOrderDetailPresenter mPresenter;
    private WDOrderDetailEntity mDataBean;
    private int payStatus;
    private String tableBillId = "";
    //处理外带订单明细列表分组
    private String mCurrentTime;
    private HashMap<String, String> map = new HashMap<String, String>();
    private boolean isUnOrdered;
    //外带订单实体类
    private OrderListEntity mPackingOrderBean;
    //补打账单
    private IWDPrintOrderPresenter mPrintPresenter;

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
        mPresenter.requestWDOrderDetail(tableBillId);
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
        mPrintPresenter = new WDPrintOrderPresenterImpl(mContext, this);
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

    public void requestPackingDetail(OrderListEntity orderListEntity) {
        if (orderListEntity != null) {
            this.mPackingOrderBean = orderListEntity;
            if (!TextUtils.isEmpty(orderListEntity.id)) {
                tableBillId = orderListEntity.id;
                mPresenter.requestWDOrderDetail(tableBillId);
            }
        }
    }

    @OnClick({R.id.tv_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_button:
                handleClickPayStatus();
                break;
        }
    }

    /**
     * 根据账单支付状态处理跳转逻辑
     */
    private void handleClickPayStatus() {
        //                1-未支付，2-支付完成，3-已退款，4-部分支付
        switch (payStatus) {
            //部分支付-去结账-结账页面屏蔽去菜单按钮
            case 4:
                Bundle bundle = new Bundle();
                bundle.putInt(FROM_WHERE, DishConstants.TYPE_WD);
                bundle.putString(TABLE_BILL_ID, tableBillId);
                readyGo(ACT_CheckOut.class, bundle);
                break;
            //未支付状态-去点菜
            case 1:
                Bundle bundles = new Bundle();
                bundles.putString(ACT_OrderDishes.TABLE_BILL_ID, tableBillId);
                bundles.putInt(ACT_OrderDishes.FROM_WHERE, DishConstants.TYPE_WD);
                //传给ACT_OrderDishes订单编号
                if (!TextUtils.isEmpty(mPackingOrderBean.getBillNo()) && mPackingOrderBean.getBillNo().length() > 5) {
                    bundles.putString(ACT_OrderDishes.TITLE_TEXT, mPackingOrderBean.getBillNo().substring(mPackingOrderBean.getBillNo().length() - 5, mPackingOrderBean.getBillNo().length()));
                }
                readyGo(ACT_OrderDishes.class, bundles);
                break;
            //补打账单
            case 2:
                mPrintPresenter.requestPrintOrder(tableBillId, Constants.SHOP_ID);
                break;
        }
    }
//
//    /**
//     * 按照下单时间对点菜的明细订单列表进行分组处理
//     *
//     * @param dataList
//     */
//    private void setCount(ArrayList<WDDishDetaiListlEntity> dataList) {
//        int limit = 0;
//        int j = 0;
//        for (; limit < dataList.size(); limit++) {
//            if (dataList.get(limit).orderTime == null) {
//                return;
//            }
//            mCurrentTime = StringUtil.getString(dataList.get(limit).orderTime);
//            for (; j < dataList.size(); j++) {
//                WDDishDetaiListlEntity dishDetaiListlEntity = dataList.get(j);
//                if (dishDetaiListlEntity.orderTime == null) {
//                    return;
//                }
//                String orderTime = StringUtil.getString(dishDetaiListlEntity.orderTime);
//                if (!orderTime.equals(mCurrentTime)) {
//                    //key：time value:在该时间下单的菜品数量
//                    map.put(mCurrentTime, StringUtil.getString(j - limit));
//                    limit = j - 1;
//                    break;
//                }
//                if (j == dataList.size() - 1) {
//                    map.put(mCurrentTime, StringUtil.getString(j - limit + 1));
//                    limit = j;
//                    break;
//                }
//            }
//        }
//        for (int k = 0; k < dataList.size(); k++) {
//            String count = map.get(StringUtil.getString(dataList.get(k).orderTime));
//            dataList.get(k).totalCount = count;
//        }
//    }


    /**
     * // 1-未支付，2-支付完成，3-已退款，4-部分支付
     */
    private void handlePayStatus() {
        switch (payStatus) {
            //部分支付
            case 4:
                tvPayStatus.setText(mContext.getResources().getString(R.string.string_paying));
                tvButton.setText(mContext.getResources().getString(R.string.string_settle_account));
                tvNotPay.setVisibility(View.VISIBLE);
                tvWaitReceiveMoney.setVisibility(View.VISIBLE);
                layoutPayInfo.setVisibility(View.VISIBLE);
                break;
            //支付完成
            case 2:
                tvPayStatus.setText(mContext.getResources().getString(R.string.string_pay_successful));
                tvButton.setText(mContext.getResources().getString(R.string.string_dozen_bill));
                tvNotPay.setVisibility(View.GONE);
                tvWaitReceiveMoney.setVisibility(View.GONE);
                layoutPayInfo.setVisibility(View.VISIBLE);
                break;
            //已退款-外带无退款状态
            case 3:

                break;
            //未支付
            case 1:
                tvPayStatus.setText(mContext.getResources().getString(R.string.string_wait_pay));
                tvButton.setText(mContext.getResources().getString(R.string.string_order_dishes));
                tvNotPay.setVisibility(View.VISIBLE);
                tvWaitReceiveMoney.setVisibility(View.VISIBLE);
                layoutPayInfo.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 判断列表中是否有未下单的菜品
     *
     * @param orderDetailEntity
     */
    private void isExsitUnOrderedDish(WDOrderDetailEntity orderDetailEntity) {
        if (orderDetailEntity.dishListDetail != null) {
            ArrayList<WDDishDetaiListlEntity> dishListDetail = orderDetailEntity.dishListDetail;
            isUnOrdered = false;
            for (int i = 0; i < dishListDetail.size(); i++) {
                WDDishDetaiListlEntity wdDishDetaiListlEntity = dishListDetail.get(i);
                if (wdDishDetaiListlEntity.orderTime != null && wdDishDetaiListlEntity.orderTime == 0) {
                    isUnOrdered = true;
                    break;
                }
            }
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
            if (orderDetailEntity.payStatus != null && !orderDetailEntity.payStatus.equals("Y") && !orderDetailEntity.payStatus.equals("N")) {
                payStatus = Integer.parseInt(orderDetailEntity.payStatus);
            }

            //开单服务员
            tvOpenOrderWaiter.setText(orderDetailEntity.waiterName);
            //开单时间
            tvOpenOrderTime.setText(String.valueOf(AppDateUtil.getTimeStamp(orderDetailEntity.openTime, AppDateUtil.YYYY_MM_DD_HH_MM_SS)));
            //开单服务员
            tvOpenOrderPersonNum.setText(StringUtil.getString(orderDetailEntity.personNum));

            //会员手机号
            if (!TextUtils.isEmpty(orderDetailEntity.memberPhone) && orderDetailEntity.memberPhone.length() == 11) {
                layoutMemberInfo.setVisibility(View.VISIBLE);
                String maskNumber = orderDetailEntity.memberPhone.substring(0, 3) + "****" + orderDetailEntity.memberPhone.substring(7, orderDetailEntity.memberPhone.length());
                tvPhoneNumber.setText(maskNumber);
            } else {
                layoutMemberInfo.setVisibility(View.GONE);
            }
            //优惠金额
            if (!TextUtils.isEmpty(orderDetailEntity.getReduceMoney())) {
                tvReduceMoney.setVisibility(View.VISIBLE);
                tvReduceMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getReduceMoney());
            } else {
                tvReduceMoney.setVisibility(View.GONE);
            }

            //折扣
            if (!TextUtils.isEmpty(orderDetailEntity.getDiscountMoney())) {
                layoutDiscount.setVisibility(View.VISIBLE);
                tvDiscountMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getDiscountMoney());
            } else {
                layoutDiscount.setVisibility(View.GONE);
            }
            //抹零
            if (!TextUtils.isEmpty(orderDetailEntity.getIgnoreMoney())) {
                layoutIgnore.setVisibility(View.VISIBLE);
                tvIgnoreMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getIgnoreMoney());
            } else {
                layoutIgnore.setVisibility(View.GONE);
            }
            //实际支付
            if (!TextUtils.isEmpty(orderDetailEntity.getIgnoreMoney())) {
                tvRealReceiveAmount.setVisibility(View.VISIBLE);
                tvRealReceiveAmount.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getRealReceiveAmount());
            } else {
                tvRealReceiveAmount.setVisibility(View.GONE);
            }
            //支付方式-可能组合
            if (orderDetailEntity.payTypeList != null && orderDetailEntity.payTypeList.size() > 0) {
                mLvPayType.setVisibility(View.VISIBLE);
                mPaytypeAdapter.update(orderDetailEntity.payTypeList, true);
            } else {
                mLvPayType.setVisibility(View.GONE);
            }
            //收银员名称
            tvCashier.setText(orderDetailEntity.waiterName);
            //支付状态
            handlePayStatus();
            //点菜明细列表
            ArrayList<WDDishDetaiListlEntity> dishListDetail = orderDetailEntity.dishListDetail;
            if (dishListDetail != null && dishListDetail.size() > 0) {
//                setCount(orderDetailEntity.dishListDetail);
                mAdapter.update(dishListDetail, true);
            }
            //待收金额
            if (!TextUtils.isEmpty(orderDetailEntity.getReceiveAmount())) {
                tvWaitReceiveMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + orderDetailEntity.getReceiveAmount());
            }
            //是否存在未下单的菜品
            isExsitUnOrderedDish(orderDetailEntity);
            //是部分支付，并且全部都是未下单的情况，待支付显示￥0.00
            if (isUnOrdered && payStatus == 4) {
                //待收
                tvWaitReceiveMoney.setText(mContext.getResources().getString(R.string.RMB_symbol) + "0.00");
            }
        }
    }

    /**
     * 补打账单
     *
     * @param result
     */
    @Override
    public void requestPrintOrder(String result) {

    }
}

